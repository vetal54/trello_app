package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Member;
import spd.trello.domian.type.Role;
import spd.trello.repository.MemberRepository;

import java.util.UUID;

@Service
public class MemberService extends AbstractDomainService<Member, MemberRepository> {

    public MemberService(MemberRepository repository) {
        super(repository);
    }

    public Member create(Role role, UUID id) {
        Member member = new Member();
        member.setRole(role);
        member.setUserId(id);
        return repository.save(member);
    }
}
