package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Data
@Entity(name="playerResult")
public class PlayerResult {
    @EmbeddedId
    private PlayerResultKey playerResultKey = new PlayerResultKey();

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "playerId", columnDefinition = "BIGINT(20)")
    @JsonIgnoreProperties("playerResults")
    private Player player;

    @ManyToOne
    @MapsId("sessionId")
    @JoinColumn(name = "sessionId", columnDefinition = "BIGINT(20)")
    @JsonIgnoreProperties(value = {"playerResults", "rankings"})
    private Session session;

    @Transient
    private long rank;

    @Transient
    private boolean isLast;

    @Transient
    private int playersCount;

    @PostLoad
    private void onLoad() {
        Supplier<Stream<PlayerResult>> sortedResultsSupplier = () -> this.session.getPlayerResults().stream().sorted(Comparator.comparingInt(PlayerResult::getResult));
        // Calcul du classement
        long superiorResults = sortedResultsSupplier.get().filter(r -> r.getResult() > this.result).count();
        this.rank = superiorResults + 1;

        Optional<PlayerResult> lastResult = sortedResultsSupplier.get().findFirst();
        this.isLast = lastResult.isPresent() && lastResult.get().result == this.result;

        this.playersCount = this.session.getPlayerResults().size();
    }

    private int buyIns = 0;
    private int result = 0;
}
