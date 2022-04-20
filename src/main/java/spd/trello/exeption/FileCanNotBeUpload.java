package spd.trello.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="File can`t be upload")
public class FileCanNotBeUpload extends RuntimeException {
}
