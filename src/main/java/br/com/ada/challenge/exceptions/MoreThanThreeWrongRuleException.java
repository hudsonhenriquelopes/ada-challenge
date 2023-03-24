package br.com.ada.challenge.exceptions;

import static br.com.ada.challenge.exceptions.GameException.GameRuleMessages.MSG_MORE_THAN_THREE_WRONG;

public class MoreThanThreeWrongRuleException extends GameException {

    public MoreThanThreeWrongRuleException(int points) {
        super(MSG_MORE_THAN_THREE_WRONG.getMessage(points));
    }
}
