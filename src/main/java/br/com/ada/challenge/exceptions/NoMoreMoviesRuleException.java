package br.com.ada.challenge.exceptions;

import static br.com.ada.challenge.exceptions.GameException.GameRuleMessages.MSG_NO_MORE_MOVIES;

public class NoMoreMoviesRuleException extends GameException {

    public NoMoreMoviesRuleException() {
        super(MSG_NO_MORE_MOVIES.getMessage());
    }
}
