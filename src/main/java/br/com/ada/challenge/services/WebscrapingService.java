package br.com.ada.challenge.services;

import br.com.ada.challenge.apis.OmdbSearchApi;
import br.com.ada.challenge.dtos.omdb.OmdbMovieDto;
import br.com.ada.challenge.dtos.omdb.OmdbSearchResponseDto;
import br.com.ada.challenge.entities.Movie;
import br.com.ada.challenge.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebscrapingService {

    private final OmdbSearchApi omdbSearchApi;
    private final MovieRepository movieRepository;
    private final String suggestedTitle;
    private final int limitPages;

    @Autowired
    public WebscrapingService(
            OmdbSearchApi omdbSearchApi,
            MovieRepository movieRepository,
            @Value("${omdb.api-search-title:Batman}") String suggestedTitle,
            @Value("${omdb.api-search-limit:5}") int limitPages
    ) {
        this.omdbSearchApi = omdbSearchApi;
        this.movieRepository = movieRepository;
        this.suggestedTitle = suggestedTitle;
        this.limitPages = limitPages;
    }

    public void init() {
        long countRecords = movieRepository.count();
        if (countRecords == 0) {
            int totalPages = limitPages > 100 ? 100 : limitPages;
            for (int page = 1; page <= totalPages; page++) {
                OmdbSearchResponseDto response = omdbSearchApi.searchByMovieTitle(suggestedTitle, page);

                List<Movie> movies =
                        response.results().stream().map(item -> {
                            OmdbMovieDto movieDetails = omdbSearchApi.findByID(item.imdbId());
                            Movie movie = new Movie();
                            movie.setTitle(movieDetails.title());
                            movie.setPoster(movieDetails.poster());
                            movie.setRanking(movieDetails.imdbVotes() * movieDetails.imdbRating());
                            movie.setPlot(movieDetails.plot());
                            return movie;
                        }).collect(Collectors.toList());

                movieRepository.saveAll(movies);
            }
        }
    }
}
