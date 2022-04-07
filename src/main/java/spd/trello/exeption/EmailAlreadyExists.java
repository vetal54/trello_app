package spd.trello.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class EmailAlreadyExists extends RuntimeException {

    public EmailAlreadyExists(String massage) {
        super(massage);
    }
}
