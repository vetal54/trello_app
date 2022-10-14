package spd.trello.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Object can`t be update")
public class UpdateImpossible extends RuntimeException {
}
