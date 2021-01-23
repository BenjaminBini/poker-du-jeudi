package io.bini.poker.pokerdujeudi.service.ranking;

import io.bini.poker.pokerdujeudi.model.*;
import io.bini.poker.pokerdujeudi.service.player.PlayerRepository;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultRepository;
import io.bini.poker.pokerdujeudi.service.session.SessionRepository;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RankingService {
    private final RankingRepository rankingRepository;
    private final SessionRepository sessionRepository;
    private final PlayerResultRepository playerResultRepository;
    private final PlayerRepository playerRepository;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public RankingService(RankingRepository rankingRepository, SessionRepository sessionRepository, PlayerResultRepository playerResultRepository, PlayerRepository playerRepository) {
        this.rankingRepository = rankingRepository;
        this.sessionRepository = sessionRepository;
        this.playerResultRepository = playerResultRepository;
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    private void computeRankings() {
        Optional<Session> session = this.sessionRepository.findAll().stream().min(Comparator.comparing(Session::getDate));
        session.ifPresent(this::computeRankingsForSession);

    }

    public void computeRankingsForSession(Session session) {
        logger.info("Computing rankings from session with id: " + session.getSessionId());
        List<PlayerResult> allResults = this.playerResultRepository.findAll();
        List<Session> allSessions = this.sessionRepository.findAll().stream().filter(s -> s.getDate().compareTo(session.getDate()) >= 0).collect(Collectors.toList());

        List<Player> players = this.playerRepository.findAll();

        List<Ranking> rankings = new ArrayList<>();
        for (Player player : players) {
            List<PlayerResult> playerResults = allResults.stream().filter(pr -> pr.getPlayer().getPlayerId() == player.getPlayerId())
                    .collect(Collectors.toList());
            for (Session currentSession : allSessions) {
                List<PlayerResult> resultsBeforeSession = playerResults.stream().filter(pr -> pr.getSession().getDate().compareTo(currentSession.getDate()) <= 0).collect(Collectors.toList());
                int generalTotal = resultsBeforeSession.stream().map(PlayerResult::getResult).reduce(Integer::sum).orElse(0);
                int seasonTotal = resultsBeforeSession.stream().filter(pr -> pr.getSession().getSeason().getSeasonId() == currentSession.getSeason().getSeasonId())
                        .map(PlayerResult::getResult).reduce(Integer::sum).orElse(0);
                long generalCount = resultsBeforeSession.size();
                long seasonCount = resultsBeforeSession.stream().filter(pr -> pr.getSession().getSeason().getSeasonId() == currentSession.getSeason().getSeasonId()).count();
                if (seasonCount > 0) {
                    Ranking seasonRanking = new Ranking(player.getPlayerId(), currentSession.getSessionId(), false, seasonTotal, seasonCount);
                    seasonRanking.setPlayer(player);
                    rankings.add(seasonRanking);
                }
                if (generalCount > 0) {
                    Ranking generalRanking = new Ranking(player.getPlayerId(), currentSession.getSessionId(), true, generalTotal, generalCount);
                    generalRanking.setPlayer(player);
                    rankings.add(generalRanking);
                }
            }

        }
        this.rankingRepository.saveAll(rankings);
        session.setRankings(rankings.stream().filter(r -> r.getRankingKey().getSessionId() == session.getSessionId()).collect(Collectors.toList()));
    }

    public void deleteRanking(RankingKey rankingKey) {
        this.rankingRepository.deleteById(rankingKey);
    }

    public List<Ranking> getSessionRankings(Long sessionId) {
        return this.rankingRepository.findByRankingKey_SessionId(sessionId);
    }

    public List<Ranking> getSessionRankings() {
        return this.rankingRepository.findAll();
    }
}
