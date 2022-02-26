package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.User;
import spd.trello.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractDomainController<User, UserService> {

    public UserController(UserService service) {
        super(service);
    }
}
