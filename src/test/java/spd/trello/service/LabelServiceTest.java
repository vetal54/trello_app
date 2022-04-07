package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Label;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM label")
class LabelServiceTest {

    @Autowired
    private LabelService service;
    @Autowired
    private Helper helper;

    @Test
    void labelWasSaved() {
        Label label = helper.createLabel();
        Label labelSave = service.findById(label.getId());
        assertThat(labelSave).isEqualTo(label);
    }

    @Test
    void emptyListOfLabelsIsReturned() {
        List<Label> labels = service.findAll();

        assertThat(labels).isEmpty();
    }

    @Test
    void notEmptyListOfLabelsIsReturned() {
        Label label = helper.createLabel();

        List<Label> labels = service.findAll();

        assertThat(labels).isNotEmpty();
    }

    @Test
    void labelWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void labelWasFoundById() {
        Label label = helper.createLabel();

        Label labelFindById = service.findById(label.getId());

        assertThat(labelFindById).isEqualTo(label);
    }

    @Test
    void labelWasDeleted() {
        Label label = helper.createLabel();

        service.delete(label.getId());

        assertThatCode(() -> service.findById(label.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void labelWasUpdated() {
        Label label = helper.createLabel();
        label.setName("new Name");

        Label updatedLabel = service.update(label);

        assertThat(updatedLabel.getName()).isEqualTo("new Name");
    }
}
