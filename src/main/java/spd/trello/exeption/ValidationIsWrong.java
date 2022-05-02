package spd.trello.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationIsWrong extends RuntimeException {

    public ValidationIsWrong(String message) {
        super(message);
    }
}
