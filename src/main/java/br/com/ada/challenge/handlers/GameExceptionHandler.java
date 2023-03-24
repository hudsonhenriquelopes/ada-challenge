package br.com.ada.challenge.handlers;

import br.com.ada.challenge.exceptions.GameException;
import br.com.ada.challenge.security.CurrentUser;
import br.com.ada.challenge.security.CustomUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(GameException.class)
    public ModelAndView handleGameRules(final GameException exception, @CurrentUser CustomUser customUser) {
        customUser.resetMatch();
        return new ModelAndView("endgame")
                .addObject("msg", exception.getMessage())
                .addObject("url_end", "/end")
                .addObject("url_ranking", "/ranking")
                .addObject("url_logout", "/end?logout");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotExist(final UsernameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
