package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Comment;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.CardRepository;
import spd.trello.repository.CommentRepository;
import spd.trello.repository.MemberRepository;

@Service
@Slf4j
public class CommentService extends AbstractResourceService<Comment, CommentRepository> implements Validator<Comment> {

    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository repository, CardRepository cardRepository, MemberRepository memberRepository) {
        super(repository);
        this.cardRepository = cardRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Comment save(Comment comment) throws JsonProcessingException {
        log.info("Try saving comment");
        validateReference(comment);
        log.info("Comment created successfully {}", mapper.writeValueAsString(comment));
        return repository.save(comment);
    }

    @Override
    public Comment update(Comment comment) throws JsonProcessingException {
        log.info("Try updating comment");
        validateReference(comment);
        log.info("Comment updated successfully {}", mapper.writeValueAsString(comment));
        return repository.save(comment);
    }

    @Override
    public void validateReference(Comment comment) {
        log.info("Try checked foreign key");
        cardRepository.findById(comment.getCardId()).orElseThrow(() ->
                new ResourceNotFoundException("Card reference not valid. Id not corrected: " + comment.getCardId()));
        memberRepository.findById(comment.getMemberId()).orElseThrow(() ->
                new ResourceNotFoundException("Member reference not valid. Id not corrected: " + comment.getMemberId()));
    }
}
