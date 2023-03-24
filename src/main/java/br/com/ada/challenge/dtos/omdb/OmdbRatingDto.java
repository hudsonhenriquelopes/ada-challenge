package br.com.ada.challenge.dtos.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record OmdbRatingDto(
        @JsonProperty("Source") String source,
        @JsonProperty("Value") String Value
) implements Serializable {
}