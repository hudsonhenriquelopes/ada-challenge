package br.com.ada.challenge.apis;

import br.com.ada.challenge.dtos.omdb.OmdbMovieDto;
import br.com.ada.challenge.dtos.omdb.OmdbSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "omdb-search", url = "${omdb.api.search-url}")
public interface OmdbSearchApi {

    @GetMapping("?apikey=${omdb.api-key}&i={i}")
    OmdbMovieDto findByID(@PathVariable String i);

    @GetMapping("?apikey=${omdb.api-key}&s={s}&page={page}")
    OmdbSearchResponseDto searchByMovieTitle(@PathVariable String s, @PathVariable int page);
}
