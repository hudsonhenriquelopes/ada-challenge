package br.com.ada.challenge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity(name = "MATCH")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @MapKeyJoinColumn(name = Player.FK_COLUMN, table = Player.TABLE, referencedColumnName = "id")
    private Player player;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date startedIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @LastModifiedDate
    private Date endedIn;

    @Column
    @Setter
    private int rounds;

    @Column
    @Setter
    private int points;

    @Column
    @Setter
    private int errors;

    public Match(Player player, int rounds, int points, int errors) {
        this.player = player;
        this.rounds = rounds;
        this.points = points;
        this.errors = errors;
    }
}
