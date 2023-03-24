package br.com.ada.challenge.repositories;

import br.com.ada.challenge.entities.Player;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ListCrudRepository<Player, Long> {

    Player findByUsername(String username);
}
