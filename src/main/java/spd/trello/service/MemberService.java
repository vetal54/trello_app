package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Member;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.MemberRepository;
import spd.trello.repository.UserRepository;

@Service
@Slf4j
public class MemberService extends AbstractDomainService<Member, MemberRepository> implements Validator<Member> {

    private final UserRepository userRepository;

    public MemberService(MemberRepository repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    @Override
    public Member save(Member member) throws JsonProcessingException {
        log.info("Try saving member");
        validateReference(member);
        log.info("Member created successfully {}", mapper.writeValueAsString(member));
        return repository.save(member);
    }

    @Override
    public Member update(Member member) throws JsonProcessingException {
        log.info("Try updating member");
        validateReference(member);
        log.info("Member updated successfully {}", mapper.writeValueAsString(member));
        return repository.save(member);
    }

    public void validateReference(Member member) {
        log.info("Try checked foreign key");
        userRepository.findById(member.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("User reference not valid. Id not corrected: " + member.getUserId()));
    }
}
