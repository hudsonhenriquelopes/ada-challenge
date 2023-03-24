package br.com.ada.challenge.services;

import br.com.ada.challenge.dtos.*;
import br.com.ada.challenge.entities.Match;
import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.exceptions.MoreThanThreeWrongRuleException;
import br.com.ada.challenge.exceptions.NoMoreMoviesRuleException;
import br.com.ada.challenge.repositories.MatchRepository;
import br.com.ada.challenge.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private PlayerRepository playerRepository;
    private MoviePairStorage moviePairStorage;
    @InjectMocks
    private MatchService matchService;

    private final MovieDto movie01 = new MovieDto(UUID.randomUUID(), "title1", "poster", 1d, "plot", "imdbID");
    private final MovieDto movie02 = new MovieDto(UUID.randomUUID(), "title2", "poster", 0d, "plot", "imdbID");

    @BeforeEach
    void init() {
        List<MoviePairDto> moviePairs = new ArrayList<>() {
        };
        moviePairs.add(new MoviePairDto(movie01, movie02));
        this.moviePairStorage = new MoviePairStorage(moviePairs);
        this.matchService = new MatchService(matchRepository, playerRepository, moviePairStorage);
    }

    @Test
    void testCreateMatchSuccess() {
        // GIVEN

        // WHEN
        var result = matchService.createMatch();

        // THEN
        assertNotNull(result);
    }

    @Test
    void testCreateRoundWhenMarkPointSuccess() {
        // GIVEN
        String username = "";
        String answer = movie01.id().toString();
        MatchDto match = new MatchDto();
        MoviePairDto lastRound = new MoviePairDto(movie01, movie02);
        match.getRounds().add(lastRound);

        // WHEN
        var result = matchService.createRound(match, lastRound, username, answer);

        // THEN
        assertNotNull(result);
        assertEquals(movie01, result.movie01());
        assertEquals(movie02, result.movie02());
        assertTrue(match.getRounds().contains(lastRound));
        assertEquals(1, match.getPoints());
    }

    @Test
    void testCreateRoundWhenMarkErrorSuccess() {
        // GIVEN
        String username = "";
        String answer = "0";
        MatchDto match = new MatchDto();
        MoviePairDto lastRound = new MoviePairDto(movie01, movie02);
        match.getRounds().add(lastRound);

        // WHEN
        var result = matchService.createRound(match, lastRound, username, answer);

        // THEN
        assertNotNull(result);
        assertEquals(movie01, result.movie01());
        assertEquals(movie02, result.movie02());
        assertTrue(match.getRounds().contains(lastRound));
        assertEquals(1, match.getErrors());
    }

    @Test
    void testCreateRoundWhenThreeWrongAnswersFailure() {
        // GIVEN
        String username = "";
        String answer = "";
        MatchDto match = new MatchDto();
        match.markError();
        match.markError();
        match.markError();
        match.markPoint();
        MoviePairDto lastRound = new MoviePairDto(movie01, movie02);
        Player player = mock(Player.class);

        // WHEN
        when(playerRepository.findByUsername(username)).thenReturn(player);
        var exception = assertThrows(MoreThanThreeWrongRuleException.class,
                () -> matchService.createRound(match, lastRound, username, answer)
        );

        // THEN
        String message = String.format("Você totalizou 3 erros. Seu total de acertos foi %s!", match.getPoints());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testCreateRoundWhenNoMoviePairAvailableFailure() {
        // GIVEN
        String username = "";
        String answer = "0";
        MatchDto match = new MatchDto();
        MoviePairDto lastRound = new MoviePairDto(movie01, movie02);

        // WHEN
        moviePairStorage.pairs().clear();
        var exception = assertThrows(NoMoreMoviesRuleException.class,
                () -> matchService.createRound(match, lastRound, username, answer)
        );

        // THEN
        String message = "Fim de jogo! Não há mais filmes disponíveis para continuação do jogo.";
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSaveMatchSuccess() {
        // GIVEN
        String username = "";
        MatchDto dto = new MatchDto();
        dto.markError();
        dto.markPoint();
        MoviePairDto lastRound = new MoviePairDto(movie01, movie02);
        Player player = mock(Player.class);
        Match record = mock(Match.class);

        // WHEN
        when(playerRepository.findByUsername(username)).thenReturn(player);
        when(matchRepository.save(any(Match.class))).thenReturn(record);

        var result = matchService.saveMatch(dto, username);

        // THEN
        assertEquals(record, result);
    }

    @Test
    void testFindAllSuccess() {
        // GIVEN
        List<Match> list = new ArrayList<>();
        list.add(new Match(new Player(), 0,999,0));
        list.add(new Match(new Player(), 0,888,0));

        // WHEN
        when(matchRepository.findAll()).thenReturn(list);

        var result = matchService.findAll();

        // THEN
        assertEquals(2, result.size());
        assertEquals(999, result.get(0).points());
        assertEquals(888, result.get(1).points());
    }
}