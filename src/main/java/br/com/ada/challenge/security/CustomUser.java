package br.com.ada.challenge.security;

import br.com.ada.challenge.dtos.MatchDto;
import br.com.ada.challenge.dtos.MoviePairDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Setter
@Getter
public class CustomUser extends User {

    public CustomUser(String username, String password) {
        super(username, password, AuthorityUtils.createAuthorityList("ROLE_USER"));
        resetMatch();
    }

    private MatchDto match;

    private MoviePairDto round;

    public void resetMatch() {
        this.match = new MatchDto();
        this.round = null;
    }
}
