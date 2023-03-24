package br.com.ada.challenge.dtos;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record MovieDto(
        UUID id,
        String title,
        String poster,
        Double ranking,
        String plot,
        String imdbID
) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return Objects.equals(id, movieDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
