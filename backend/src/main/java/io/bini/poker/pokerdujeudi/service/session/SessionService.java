package io.bini.poker.pokerdujeudi.service.session;

import io.bini.poker.pokerdujeudi.model.Season;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.ranking.RankingService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.season.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final PlayerResultService playerResultService;
    private final RankingService rankingService;
    private final SeasonRepository seasonRepository;

    public SessionService(SessionRepository sessionRepository, SeasonRepository seasonRepository, PlayerResultService playerResultService, RankingService rankingService) {
        this.sessionRepository = sessionRepository;
        this.playerResultService = playerResultService;
        this.rankingService = rankingService;
        this.seasonRepository = seasonRepository;
    }

    public List<Session> list() {
        List<Session> sessions = sessionRepository.findAll();
        sessions.sort(Comparator.comparing(Session::getDate));
        return sessions;
    }
    public Session get(long sessionId) {
        Optional<Session> maybeSession = sessionRepository.findById(sessionId);
        if (maybeSession.isPresent()) {
            Session session = maybeSession.get();
            populateNextAndPreviousSessions(session);
        }
        return maybeSession.orElse(null);
    }

    public Session save(Session session) {
        session = this.sessionRepository.save(session);
        this.rankingService.computeRankingsForSession(session);
        return session;
    }

    public void delete(Long id) {
        Session session = sessionRepository.getOne(id);
        Session previousSession = session.getPreviousSession();
        session.getPlayerResults().forEach(playerResultService::delete);
        this.rankingService.getSessionRankings(id).forEach(r -> this.rankingService.deleteRanking(r.getRankingKey()));
        sessionRepository.deleteById(id);
        if (previousSession != null) {
            rankingService.computeRankingsForSession(previousSession);
        }
    }

    public Session getLastSession() {
        Session session = this.sessionRepository.getLastSession();
        populateNextAndPreviousSessions(session);
        return session;
    }

    public Session getLastSession(String season) {
        Session session = this.sessionRepository.getLastSession(season);
        populateNextAndPreviousSessions(session);
        return session;
    }

    private void populateNextAndPreviousSessions(Session session) {
        Session nextSession = this.getNextSession(session.getSessionId());
        if (nextSession != null) {
            session.setNextSession(nextSession);
        }
        Session previousSession = this.getPreviousSession(session.getSessionId());
        if (previousSession != null) {
            session.setPreviousSession(previousSession);
        }
    }

    public Session getPreviousSession(long sessionId) {
        return this.sessionRepository.getSessionBefore(sessionId);
    }

    public Session getNextSession(long sessionId) {
        return this.sessionRepository.getSessionAfter(sessionId);
    }

}
