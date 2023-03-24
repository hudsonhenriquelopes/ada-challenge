package br.com.ada.challenge.dtos.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record OmdbMovieMinimalDto(
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year,
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("Type") String type,
        @JsonProperty("Poster") String poster
) implements Serializable {
}
