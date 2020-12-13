package io.bini.poker.pokerdujeudi.service.ranking;

import io.bini.poker.pokerdujeudi.model.Ranking;
import io.bini.poker.pokerdujeudi.model.RankingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, RankingKey> {
    List<Ranking> findByRankingKey_SessionId(long sessionId);
}
