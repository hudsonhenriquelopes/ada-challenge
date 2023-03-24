package br.com.ada.challenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.util.UUID;

import static br.com.ada.challenge.entities.Player.TABLE;

@Entity(name = TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Primary
public class Player {

    protected static final String TABLE = "PLAYER";
    protected static final String FK_COLUMN = "user_id";

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
