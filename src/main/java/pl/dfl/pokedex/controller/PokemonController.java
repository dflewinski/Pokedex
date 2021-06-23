package pl.dfl.pokedex.controller;

import org.springframework.web.bind.annotation.*;
import pl.dfl.pokedex.exception.PokemonNotFoundByIdException;
import pl.dfl.pokedex.exception.PokemonNotFoundByNameException;
import pl.dfl.pokedex.model.Pokemon;
import pl.dfl.pokedex.repository.PokemonRepository;

import java.util.List;

@RestController
public class PokemonController {

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping("/pokemons")
    List<Pokemon> all() {
        return pokemonRepository.findAll();
    }

    @GetMapping("/pokemons/{id}")
    Pokemon getOne(@PathVariable int id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundByIdException(id));
    }

    @GetMapping("/pokemons/")
    List<Pokemon> getByName(@RequestParam(name = "name") String name) {
        List<Pokemon> result = pokemonRepository.findByName(name);
        if(result.isEmpty()) throw new PokemonNotFoundByNameException(name);
        return result;
    }

    @PostMapping("/pokemons")
    Pokemon newPokemon(@RequestBody Pokemon newPokemon) {
        return pokemonRepository.save(newPokemon);
    }

    @PutMapping("/pokemons/{id}")
    Pokemon replacePokemon(@RequestBody Pokemon newPokemon, @PathVariable int id) {
        return pokemonRepository.findById(id)
                .map(pokemon -> {
                    pokemon.setName(newPokemon.getName());
                    pokemon.setId(id);
                    return pokemonRepository.save(pokemon);
                }).orElseGet(() -> {
                    newPokemon.setId(id);
                    return pokemonRepository.save(newPokemon);
                });
    }

    @DeleteMapping("/pokemons/{id}")
    void deletePokemon(@PathVariable int id) {
        pokemonRepository.findById(id).ifPresent(pokemon -> pokemonRepository.deleteById(id));
    }
}
