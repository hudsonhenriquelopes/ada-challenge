package br.com.ada.challenge.controllers;

import br.com.ada.challenge.dtos.MoviePairDto;
import br.com.ada.challenge.security.CurrentUser;
import br.com.ada.challenge.security.CustomUser;
import br.com.ada.challenge.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    private final MatchService matchService;

    @Autowired
    public GameController(MatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping("/start")
    public ModelAndView startMatch(@CurrentUser final CustomUser customUser) {
        customUser.setMatch(matchService.createMatch());
        return new ModelAndView("redirect:/game");
    }

    @GetMapping(value = "/game")
    public ModelAndView startRound(@CurrentUser final CustomUser customUser) {
        return nextRound(customUser, null);
    }

    @PostMapping(value = "/game", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView nextRound(
            @CurrentUser final CustomUser customUser,
            @RequestParam("answer") String answer
    ) {
        MoviePairDto round =
                matchService.createRound(
                        customUser.getMatch(),
                        customUser.getRound(),
                        customUser.getUsername(),
                        answer
                );
        customUser.setRound(round);

        return new ModelAndView("game")
                .addObject("match", customUser.getMatch())
                .addObject("movies", List.of(customUser.getRound().movie01(), customUser.getRound().movie02()))
                .addObject("url_end", "/end")
                .addObject("url_ranking", "/ranking")
                .addObject("url_logout", "/end?logout");
    }

    @GetMapping("/end")
    public ModelAndView endMatch(
            @CurrentUser final CustomUser customUser,
            @RequestParam(name = "logout") Optional<String> logout) {
        saveMatchAndLeave(customUser);
        if (logout.isPresent()) {
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("redirect:/start");
    }

    @GetMapping("/ranking")
    public ModelAndView showRanking() {
        return new ModelAndView("ranking")
                .addObject("matches", matchService.findAll())
                .addObject("url_end", "/end")
                .addObject("url_logout", "/end?logout");
    }

    private void saveMatchAndLeave(final CustomUser customUser) {
        if (customUser.getMatch() != null) {
            if (!customUser.getMatch().getRounds().isEmpty()) {
                matchService.saveMatch(customUser.getMatch(), customUser.getUsername());
            }
            customUser.resetMatch();
        }
    }
}
