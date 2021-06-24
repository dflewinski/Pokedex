package pl.dfl.pokedex.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class NotFoundError implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<CustomErrorResponse> handleError(HttpServletRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setPath(request.getRequestURI());
        errors.setError("The URI couldn't be found.");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
