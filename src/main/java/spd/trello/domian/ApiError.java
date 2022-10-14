package spd.trello.domian;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiError {

    String message;
    Map<String, String> details;

    public ApiError(String message, Map<String, String> details) {
        super();
        this.message = message;
        this.details = details;
    }
}
