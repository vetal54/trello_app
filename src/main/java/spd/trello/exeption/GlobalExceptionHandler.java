package spd.trello.exeption;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spd.trello.domian.ApiError;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = {NullPointerException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        log.trace("Entering handle() method");
        String bodyOfResponse = "Something went wrong...";
        log.error("An error was found: {}", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.I_AM_A_TEAPOT, request);
    }

    @SneakyThrows
    @SuppressWarnings("NullableProblems")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.trace("Entering validate() method");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiError error = new ApiError("Validation Failed", errors);
        log.error("The object is not valid: {}", mapper.writeValueAsString(error));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
