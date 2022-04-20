package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Member;
import spd.trello.repository.MemberRepository;

@Service
public class MemberService extends AbstractDomainService<Member, MemberRepository> {

    public MemberService(MemberRepository repository) {
        super(repository);
    }
}
