package pl.dfl.pokedex.exception;

public class PokemonNotFoundByIdException extends RuntimeException {

    public PokemonNotFoundByIdException(int id) {
        super("Couldn't find Pokemon with id: " + id);
    }
}
