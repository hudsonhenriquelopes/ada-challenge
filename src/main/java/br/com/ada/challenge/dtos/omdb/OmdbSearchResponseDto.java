package br.com.ada.challenge.dtos.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record OmdbSearchResponseDto(
        @JsonProperty("Search") List<OmdbMovieMinimalDto> results,
        @JsonProperty("totalResults") int totalResults,
        @JsonProperty("Response") boolean valid
) implements Serializable {
}
