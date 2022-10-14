package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Member;
import spd.trello.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController extends AbstractDomainController<Member, MemberService> {

    public MemberController(MemberService service) {
        super(service);
    }
}
