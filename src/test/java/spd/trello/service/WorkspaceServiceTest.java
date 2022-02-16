package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.domian.Workspace;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.WorkspaceRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository repository;
    private Workspace workspace;
    private WorkspaceService service;

    void create() {
        workspace = new Workspace();
        workspace.setName("vitaliy");
        workspace.setCreateBy("@gmail");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("hello");
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new WorkspaceService(repository);
        create();
    }

    @Test
    void workspaceSave() {
        when(repository.save(workspace)).thenReturn(workspace);

        Workspace savedWorkspace = repository.save(workspace);
        assertThat(savedWorkspace).isEqualTo(workspace);
    }

    @Test
    void emptyListOfWorkspaces() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Workspace> workspaces = service.findAll();

        assertThat(workspaces).isEmpty();
    }

    @Test
    void oneElementOfListWorkspaces() {
        when(repository.findAll()).thenReturn(
                List.of(
                        workspace
                )
        );

        List<Workspace> workspaces = service.findAll();

        assertThat(workspaces).isEqualTo(List.of(workspace));
    }

    @Test
    void workspaceWasNotFoundById() {
        when(repository.findById(workspace.getId()))
                .thenReturn(Optional.empty());

        assertThatCode(() -> service.findById(workspace.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void workspaceWasFoundById() {
        when(repository.findById(workspace.getId())).thenReturn(
                Optional.of(workspace)
        );

        Workspace workspaceFindById = service.findById(workspace.getId());

        assertThat(workspaceFindById).isEqualTo(workspace);
    }

    @Test
    void workspaceWasDeleted() {
        service.delete(workspace.getId());
        verify(repository).deleteById(workspace.getId());
    }

    @Test
    void workspaceWasUpdated() {
        when(repository.save(workspace))
                .thenReturn(workspace);

        workspace.setName("new Name");

        Workspace updatedWorkspace = service.save(workspace);

        assertThat(updatedWorkspace).isEqualTo(workspace);
    }
}
