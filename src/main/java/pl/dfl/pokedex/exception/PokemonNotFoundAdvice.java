package pl.dfl.pokedex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class PokemonNotFoundAdvice {

    @ExceptionHandler({PokemonNotFoundByIdException.class,
            PokemonNotFoundByNameException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorResponse> pokemonNotFoundByIdHandler(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
//        errors.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        errors.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
