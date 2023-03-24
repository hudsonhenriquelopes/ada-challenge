package br.com.ada.challenge.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MatchDto implements Serializable {

    private Set<MoviePairDto> rounds;
    private int points;
    private int errors;

    public MatchDto() {
        this.rounds = new HashSet<>();
        this.points = 0;
        this.errors = 0;
    }

    public void markPoint() {
        this.points++;
    }

    public void markError() {
        this.errors++;
    }

    public Set<MoviePairDto> getRounds() {
        return rounds;
    }

    public int getPoints() {
        return points;
    }

    public int getErrors() {
        return errors;
    }
}
