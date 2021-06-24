package pl.dfl.pokedex.model;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.dfl.pokedex.controller.PokemonController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PokemonModelAssembler implements RepresentationModelAssembler<Pokemon, EntityModel<Pokemon>> {
    @Override
    public EntityModel<Pokemon> toModel(Pokemon entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PokemonController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(PokemonController.class).all()).withRel("pokemons")
        );
    }
}
