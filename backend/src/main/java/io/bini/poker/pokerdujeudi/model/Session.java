package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity(name="session")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Session {
    @Override
    public String toString() {
        return String.valueOf(this.sessionId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="seasonId")
    private Season season;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="placeId")
    private Place place;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "session")
    @JsonIgnoreProperties(value = {"session", "playerResultKey"})
    private List<PlayerResult> playerResults;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "rankingKey.sessionId")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties(value = {"session"})
    private List<Ranking> rankings;

    private Date date;

    @Transient
    private Session previousSession;

    @Transient
    private Session nextSession;
}
