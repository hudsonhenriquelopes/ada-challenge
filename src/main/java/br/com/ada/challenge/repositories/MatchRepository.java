package br.com.ada.challenge.repositories;

import br.com.ada.challenge.entities.Match;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends ListCrudRepository<Match, Long> {
}
