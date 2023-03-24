package br.com.ada.challenge.dtos.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public record OmdbMovieDto(
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year,
        @JsonProperty("Rated") String rated,
        @JsonProperty("Released") String released,
        @JsonProperty("Runtime") String runtime,
        @JsonProperty("Genre") String genre,
        @JsonProperty("Director") String director,
        @JsonProperty("Writer") String writer,
        @JsonProperty("Actors") String actors,
        @JsonProperty("Plot") String plot,
        @JsonProperty("Language") String language,
        @JsonProperty("Country") String country,
        @JsonProperty("Awards") String awards,
        @JsonProperty("Poster") String poster,
        @JsonProperty("Ratings") List<OmdbRatingDto> ratings,
        @JsonProperty("Metascore") String metascore,
        @JsonProperty Double imdbRating,
        @JsonProperty @JsonDeserialize(using = AmericanLongDeserializer.class) Long imdbVotes,
        @JsonProperty String imdbID,
        @JsonProperty("Type") String type,
        @JsonProperty("DVD") String dvd,
        @JsonProperty("BoxOffice") String boxOffice,
        @JsonProperty("Production") String production,
        @JsonProperty("Website") String website,
        @JsonProperty("Response") String response
) implements Serializable {

    static class AmericanLongDeserializer extends JsonDeserializer<Long> {

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            try {
                return NumberFormat.getInstance(Locale.US).parse(jsonParser.getText()).longValue();
            } catch (ParseException | IOException e) {
                return 0L;
            }
        }
    }
}
