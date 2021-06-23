package pl.dfl.pokedex.exception;

public class PokemonNotFoundByNameException extends RuntimeException {

    public PokemonNotFoundByNameException(String name) {
        super("Couldn't find Pokemon with name: " + name);
    }
}
