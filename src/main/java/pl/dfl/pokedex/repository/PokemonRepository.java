package pl.dfl.pokedex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import pl.dfl.pokedex.model.Pokemon;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon,Integer>, QueryByExampleExecutor<Pokemon> {

    List<Pokemon> findByName(String name);
}
