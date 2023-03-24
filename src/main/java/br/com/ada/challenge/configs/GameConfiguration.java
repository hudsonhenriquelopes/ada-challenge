package br.com.ada.challenge.configs;

import br.com.ada.challenge.dtos.MovieDto;
import br.com.ada.challenge.dtos.MoviePairDto;
import br.com.ada.challenge.dtos.MoviePairStorage;
import br.com.ada.challenge.dtos.MovieStorage;
import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.repositories.PlayerRepository;
import br.com.ada.challenge.services.MovieService;
import br.com.ada.challenge.services.WebscrapingService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class GameConfiguration {

    private final MovieService movieService;
    private final WebscrapingService webscrapingService;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameConfiguration(MovieService movieService, WebscrapingService webscrapingService, PlayerRepository playerRepository) {
        this.movieService = movieService;
        this.webscrapingService = webscrapingService;
        this.playerRepository = playerRepository;
    }

    @Bean
    public MovieStorage movieStorage() {
        this.webscrapingService.init();
        return new MovieStorage(movieService.findAll());
    }

    @Bean
//    @SessionScope
    public MoviePairStorage moviePairStorage(MovieStorage movieStorage) {
        List<MovieDto> movies = movieStorage.movies();
        int totalMovies = movieStorage.movies().size();
        List<MoviePairDto> groupedMovies = new ArrayList<>();
        for (int i = 0; i < totalMovies; i++) {
            for (int j = i + 1; j < totalMovies; j++) {
                groupedMovies.add(new MoviePairDto(movies.get(i), movies.get(j)));
            }
        }
        return new MoviePairStorage(groupedMovies);
    }

    @PostConstruct
    void createUsers() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        playerRepository.save(new Player(UUID.randomUUID(), "user01", encoder.encode("password01")));
        playerRepository.save(new Player(UUID.randomUUID(), "user02", encoder.encode("password02")));
        playerRepository.save(new Player(UUID.randomUUID(), "user03", encoder.encode("password03")));
    }

}
