package br.com.ada.challenge.services;

import br.com.ada.challenge.dtos.MatchDto;
import br.com.ada.challenge.dtos.MatchMinimalDto;
import br.com.ada.challenge.dtos.MoviePairDto;
import br.com.ada.challenge.dtos.MoviePairStorage;
import br.com.ada.challenge.entities.Match;
import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.exceptions.MoreThanThreeWrongRuleException;
import br.com.ada.challenge.exceptions.NoMoreMoviesRuleException;
import br.com.ada.challenge.repositories.MatchRepository;
import br.com.ada.challenge.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final MoviePairStorage moviePairStorage;

    @Autowired
    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository, MoviePairStorage moviePairStorage) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.moviePairStorage = moviePairStorage;
    }

    public MatchDto createMatch() {
        return new MatchDto();
    }

    public MoviePairDto createRound(final MatchDto match, final MoviePairDto lastRound, String username, String answer) {
        if (match != null && lastRound != null) {
            if (!ObjectUtils.isEmpty(answer)) {
                UUID winnerId = lastRound.movie01().ranking() > lastRound.movie02().ranking() ?
                        lastRound.movie01().id() : lastRound.movie02().id();
                if (winnerId.toString().equals(answer)) {
                    match.markPoint();
                } else {
                    match.markError();
                }
                match.getRounds().add(lastRound);
            }

            validateWrongAnswers(match, username);
        }

        return selectMoviePair();
    }

    private void validateWrongAnswers(MatchDto match, String username) {
        if (match.getErrors() == 3) {
            saveMatch(match, username);
            throw new MoreThanThreeWrongRuleException(match.getPoints());
        }
    }

    private MoviePairDto selectMoviePair() {
        int totalPairs = moviePairStorage.pairs().size();

        if (totalPairs == 0) {
            throw new NoMoreMoviesRuleException();
        }

        SplittableRandom splittableRandom = new SplittableRandom();
        int index = splittableRandom.nextInt(0, totalPairs);

        return moviePairStorage.pairs().remove(index);
    }

    public Match saveMatch(MatchDto dto, String username) {
        Player player = playerRepository.findByUsername(username);
        Match record = new Match(player, dto.getRounds().size(), dto.getPoints(), dto.getErrors());
        return matchRepository.save(record);
    }

    public List<MatchMinimalDto> findAll() {
        List<MatchMinimalDto> matches = matchRepository.findAll().stream().map(match ->
                new MatchMinimalDto(match.getPlayer().getUsername(), match.getPoints(), match.getErrors())
        ).collect(Collectors.toList());
        matches.sort(Comparator.comparing(MatchMinimalDto::points).reversed());
        return matches;
    }
}
