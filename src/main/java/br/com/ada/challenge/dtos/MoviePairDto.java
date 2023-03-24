package br.com.ada.challenge.dtos;

import java.io.Serializable;
import java.util.Objects;

public record MoviePairDto(
        MovieDto movie01,
        MovieDto movie02
) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviePairDto that = (MoviePairDto) o;
        return Objects.equals(movie01, that.movie01) && Objects.equals(movie02, that.movie02) ||
                Objects.equals(movie02, that.movie01) && Objects.equals(movie01, that.movie02);
    }

    @Override
    public int hashCode() {
        if (movie02.id().compareTo(movie01.id()) < 1) {
            return Objects.hash(movie02, movie01);
        }
        return Objects.hash(movie01, movie02);
    }
}
