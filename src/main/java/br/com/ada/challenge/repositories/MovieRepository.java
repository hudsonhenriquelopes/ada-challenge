package br.com.ada.challenge.repositories;

import br.com.ada.challenge.entities.Movie;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ListCrudRepository<Movie, Long> {
}
