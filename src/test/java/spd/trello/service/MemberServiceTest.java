package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Member;
import spd.trello.domian.type.Role;
import spd.trello.exeption.ResourceNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM member")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private Helper helper;

    @Test
    void memberWasSaved() {
        Member savedMember = helper.createMember();
        Member memberSave = memberService.findById(savedMember.getId());
        assertThat(memberSave).isEqualTo(savedMember);
    }

    @Test
    void memberNotSavedEmptyName() {
        Member member = new Member();
        member.setRole(null);
        member.setUserId(helper.createUser().getId());

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        assertFalse(violations.isEmpty());
    }

    @Test
    void emptyListOfMembersIsReturned() {
        List<Member> members = memberService.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    void notEmptyListOfMembersIsReturned() {
        Member savedUser = helper.createMember();

        List<Member> members = memberService.findAll();

        assertThat(members).isNotEmpty();
    }

    @Test
    void memberWasNotFoundById() {
        assertThatCode(() -> memberService.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void memberWasFoundById() {
        Member savedMember = helper.createMember();

        Member memberFindById = memberService.findById(savedMember.getId());

        assertThat(memberFindById).isEqualTo(savedMember);
    }

    @Test
    void memberWasDeleted() {
        Member savedMember = helper.createMember();

        memberService.delete(savedMember.getId());

        assertThatCode(() -> memberService.findById(savedMember.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void memberWasUpdated() {
        Member savedMember = helper.createMember();
        savedMember.setRole(Role.GUEST);

        Member updatedMember = memberService.update(savedMember);

        assertThat(updatedMember.getRole()).isEqualTo(Role.GUEST);
    }
}
