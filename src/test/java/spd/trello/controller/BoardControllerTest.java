package spd.trello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spd.trello.domian.Board;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.BoardService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    @MockBean
    private BoardService service;

    @Autowired
    private MockMvc mockMvc;
    private Board board;

    private final String json = """
            {
              "archived": true,
              "createBy": "string",
              "description": "string",
              "name": "string",
              "visibility": "PRIVATE",
              "workspaceId": "16aff29c-4be8-4abb-92ac-022b6f75cb82"
            }""";

    private final String updateJson = """
            {
                "id": "1c3edb28-5d9e-4dde-9f67-1fb37a706f8f",
                "createBy": "string",
                "updateBy": null,
                "createDate": "2022-02-18T12:15:45.886+00:00",
                "updateDate": null,
                "name": "string",
                "description": "string",
                "visibility": "PRIVATE",
                "archived": true,
                "workspaceId": "16aff29c-4be8-4abb-92ac-022b6f75cb82",
                "cardLists": []
            }""";

    @BeforeEach
    void init() {
        board = new Board();
        board.setCreateBy("string");
        board.setDescription("string");
        board.setName("string");
        board.setVisibility(BoardVisibility.PRIVATE);
    }

    @Test
    void postBoardFailed() throws Exception {
        mockMvc.perform(post("/board"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void boardPost() throws Exception {
        mockMvc.perform(
                        post("/board")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(201));
    }

    @Test
    void boardNotFoundBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(get("/board/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void boardNotFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/board/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void boardFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenReturn(board);

        mockMvc.perform(get("/board/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("string"))
                .andExpect(jsonPath("$.createBy").value("string"))
                .andReturn();
    }

    @Test
    void boardFindAllEmptyList() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(service, times(1)).findAll();
    }

    @Test
    void boardFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(board));

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(board.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("string"))
                .andExpect(jsonPath("$[0].createBy").value("string"));

        verify(service, times(1)).findAll();
    }

    @Test
    void boardNotDeletedBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(delete("/board/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void boardDeleted() throws Exception {
        mockMvc.perform(delete("/board/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk());

        verify(service).delete(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"));
    }

    @Test
    void boardUpdateFailedForIncorrectId() throws Exception {
        mockMvc.perform(
                put("/board/id")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void boardUpdatedById() throws Exception {
        mockMvc.perform(
                put("/board/1c3edb28-5d9e-4dde-9f67-1fb37a706f8f")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().is(201));
    }
}