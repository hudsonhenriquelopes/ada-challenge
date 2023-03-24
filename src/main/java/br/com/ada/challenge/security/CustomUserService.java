package br.com.ada.challenge.security;

import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    private final PlayerService playerService;

    @Autowired
    public CustomUserService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não criado ainda."));
        return new CustomUser(player.getUsername(), player.getPassword());
    }
}
