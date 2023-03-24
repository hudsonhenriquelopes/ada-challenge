package br.com.ada.challenge.services;

import br.com.ada.challenge.dtos.MovieDto;
import br.com.ada.challenge.entities.Movie;
import br.com.ada.challenge.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> findAll() {
        List<MovieDto> list = new ArrayList<>();
        for (Movie movie : movieRepository.findAll()) {
            MovieDto movieDto = new MovieDto(movie.getId(), movie.getTitle(), movie.getPoster(), movie.getRanking(), movie.getPlot(), movie.getImdbID());
            list.add(movieDto);
        }
        return list;
    }
}
