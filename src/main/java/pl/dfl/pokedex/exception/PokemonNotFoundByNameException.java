package pl.dfl.pokedex.exception;

public class PokemonNotFoundByNameException extends RuntimeException {

    public PokemonNotFoundByNameException(String name) {
        super("Couldn't found Pokemon with name: " + name);
    }
}
