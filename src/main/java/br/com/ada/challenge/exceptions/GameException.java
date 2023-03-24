package br.com.ada.challenge.exceptions;

public class GameException extends RuntimeException {

    public GameException(String message) {
        super(message);
    }

    protected enum GameRuleMessages {
        MSG_MORE_THAN_THREE_WRONG("Você totalizou 3 erros. Seu total de acertos foi %s!"),
        MSG_NO_MORE_MOVIES("Fim de jogo! Não há mais filmes disponíveis para continuação do jogo."),
        ;

        private String message;

        GameRuleMessages(String message) {
            this.message = message;
        }

        public String getMessage(Object... params) {
            return String.format(this.message, params);
        }
    }
}
