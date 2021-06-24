package pl.dfl.pokedex.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dfl.pokedex.exception.PokemonNotFoundByIdException;
import pl.dfl.pokedex.exception.PokemonNotFoundByNameException;
import pl.dfl.pokedex.model.Pokemon;
import pl.dfl.pokedex.model.PokemonModelAssembler;
import pl.dfl.pokedex.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class PokemonController {

    private final PokemonRepository pokemonRepository;
    private final PokemonModelAssembler pokemonModelAssembler;

    public PokemonController(PokemonRepository pokemonRepository, PokemonModelAssembler pokemonModelAssembler) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonModelAssembler = pokemonModelAssembler;
    }

    @GetMapping("/pokemons")
    public CollectionModel<EntityModel<Pokemon>> all() {
        List<EntityModel<Pokemon>> pokemons = pokemonRepository.findAll().stream() //
                .map(pokemonModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pokemons, linkTo(methodOn(PokemonController.class).all()).withSelfRel());
    }

    @GetMapping("/pokemons/{id}")
    public EntityModel<Pokemon> getOne(@PathVariable int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundByIdException(id));
        return pokemonModelAssembler.toModel(pokemon);
    }

    @GetMapping("/pokemons/")
    CollectionModel<EntityModel<Pokemon>> getByName(@RequestParam(name = "name") String name) {
        List<Pokemon> pokemons = pokemonRepository.findByName(name);
        if (pokemons.isEmpty()) throw new PokemonNotFoundByNameException(name);

        List<EntityModel<Pokemon>> result = pokemons.stream()
                .map(pokemonModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(result, linkTo(methodOn(PokemonController.class).all()).withSelfRel());
    }

    @PostMapping("/pokemons")
    public ResponseEntity<?>  newPokemon(@RequestBody Pokemon newPokemon) {
        EntityModel<Pokemon> entityModel = pokemonModelAssembler.toModel(pokemonRepository.save(newPokemon));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/pokemons/{id}")
    ResponseEntity<?> replacePokemon(@RequestBody Pokemon newPokemon, @PathVariable int id) {
        Pokemon updatePokemon = pokemonRepository.findById(id)
                .map(poke -> {
                    poke.setName(newPokemon.getName());
                    poke.setId(id);
                    return pokemonRepository.save(poke);
                }).orElseGet(() -> {
                    newPokemon.setId(id);
                    return pokemonRepository.save(newPokemon);
                });

        EntityModel<Pokemon> entityModel = pokemonModelAssembler.toModel(updatePokemon);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/pokemons/{id}")
    ResponseEntity<?> deletePokemon(@PathVariable int id) {
        Pokemon deletePokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundByIdException(id));
        pokemonRepository.delete(deletePokemon);

        return ResponseEntity.noContent().build();
    }
}
