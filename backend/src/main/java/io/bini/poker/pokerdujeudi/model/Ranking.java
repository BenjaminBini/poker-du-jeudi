package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Objects;

@Data
@Entity(name="ranking")
@NoArgsConstructor
public class Ranking {
    @EmbeddedId
    private RankingKey rankingKey = new RankingKey();

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "playerId")
    @JsonIgnoreProperties("playerResults")
    private Player player;


    public Ranking(long playerId, long sessionId, boolean general, int total, long sessionsCount) {
        this.setPlayerId(playerId);
        this.setSessionId(sessionId);
        this.setGeneral(general);
        this.total = total;
        this.sessionsCount = sessionsCount;
    }

    public void setPlayerId(long playerId) {
        this.rankingKey.setPlayerId(playerId);
    }
    public void setSessionId(long sessionId) {
        this.rankingKey.setSessionId(sessionId);
    }
    public void setGeneral(boolean general) {
        this.rankingKey.setGeneral(general);
    }

    private int total = 0;
    private long sessionsCount = 0;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ranking)) return false;
        Ranking r = (Ranking) o;
        return getRankingKey() != null && Objects.equals(getRankingKey(), r.getRankingKey());
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
