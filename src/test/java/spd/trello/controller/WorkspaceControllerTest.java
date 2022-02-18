package spd.trello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.WorkspaceService;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkspaceController.class)
class WorkspaceControllerTest {

    @Autowired
    @MockBean
    private WorkspaceService service;

    @Autowired
    private MockMvc mockMvc;
    private Workspace workspace;

   private final String json = """
           {
             "createBy": "string",
             "description": "string",
             "name": "string",
             "visibility": "PRIVATE"
           }""";

   private final String updateJson = """
           {
               "id": "6ca7d536-7a99-4936-b97c-7a9e1e6f01d1",
               "createBy": "string",
               "updateBy": "vitaliy",
               "createDate": "2022-02-18T13:35:02.528+00:00",
               "updateDate": null,
               "name": "spd",
               "description": "string",
               "visibility": "PRIVATE",
               "boards": []
           }""";

    @BeforeEach
    void init() {
        workspace = new Workspace();
        workspace.setCreateBy("string");
        workspace.setDescription("string");
        workspace.setName("string");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
    }

    @Test
    void postWorkspaceFailed() throws Exception {
        mockMvc.perform(post("/workspace"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void workspacePost() throws Exception {
        mockMvc.perform(
                        post("/workspace")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(201));
    }

    @Test
    void workspaceNotFoundBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(get("/workspace/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void workspaceNotFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/workspace/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void workspaceFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenReturn(workspace);

        mockMvc.perform(get("/workspace/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("string"))
                .andExpect(jsonPath("$.createBy").value("string"))
                .andReturn();
    }

    @Test
    void workspaceNotDeletedBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(delete("/workspace/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void workspaceDeleted() throws Exception {
        mockMvc.perform(delete("/workspace/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk());

        verify(service).delete(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"));
    }

    @Test
    void workspaceUpdateFailedForIncorrectId() throws Exception {
        mockMvc.perform(
                put("/workspace/id")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void workspaceUpdatedById() throws Exception {
        mockMvc.perform(
                put("/workspace/6ca7d536-7a99-4936-b97c-7a9e1e6f01d1")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().isOk());
    }
}