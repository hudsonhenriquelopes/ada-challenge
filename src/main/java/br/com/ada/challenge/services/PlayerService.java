package br.com.ada.challenge.services;

import br.com.ada.challenge.entities.Player;
import br.com.ada.challenge.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> findByUsername(String username) {
        return Optional.ofNullable(playerRepository.findByUsername(username));
    }
}
