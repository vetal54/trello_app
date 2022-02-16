package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Workspace;
import spd.trello.service.WorkspaceService;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController extends AbstractController<Workspace, WorkspaceService> {

    public WorkspaceController(WorkspaceService service) {
        super(service);
    }
}
