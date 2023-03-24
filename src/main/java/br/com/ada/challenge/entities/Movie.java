package br.com.ada.challenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity(name = "MOVIE")
@Data
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column
    private String title;

    @Column
    private String poster;

    @Column
    private Double ranking;

    @Column
    private String plot;

    @Column
    private String imdbID;
}
