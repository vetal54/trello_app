package spd.trello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spd.trello.domian.CardList;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.CardListService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardListController.class)
class CardListControllerTest {

    @Autowired
    @MockBean
    private CardListService service;

    @Autowired
    private MockMvc mockMvc;
    private CardList cardList;

    private final String json = """
            {
              "archived": true,
              "boardId": "1c3edb28-5d9e-4dde-9f67-1fb37a706f8f",
              "createBy": "string",
              "name": "string"
            }""";

    private final String updateJson = """
            {
                "id": "132208b7-6a06-42ea-aedc-a68371545476",
                "createBy": "string",
                "updateBy": "vitaliy",
                "createDate": "2022-02-18T13:31:28.200+00:00",
                "updateDate": null,
                "name": "spd",
                "archived": false,
                "boardId": "1c3edb28-5d9e-4dde-9f67-1fb37a706f8f",
                "cards": []
            }""";

    @BeforeEach
    void init() {
        cardList = new CardList();
        cardList.setCreateBy("string");
        cardList.setName("string");
    }

    @Test
    void postCardListFailed() throws Exception {
        mockMvc.perform(post("/card-list"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardListPost() throws Exception {
        mockMvc.perform(
                        post("/card-list")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(201));
    }

    @Test
    void cardListNotFoundBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(get("/card-list/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardListNotFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/card-list/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cardListFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenReturn(cardList);

        mockMvc.perform(get("/card-list/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("string"))
                .andExpect(jsonPath("$.createBy").value("string"))
                .andReturn();
    }

    @Test
    void cardListFindAllEmptyList() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/card-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(service, times(1)).findAll();
    }

    @Test
    void cardListFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(cardList));

        mockMvc.perform(get("/card-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cardList.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("string"))
                .andExpect(jsonPath("$[0].createBy").value("string"));

        verify(service, times(1)).findAll();
    }

    @Test
    void cardListNotDeletedBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(delete("/card-list/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardListDeleted() throws Exception {
        mockMvc.perform(delete("/card-list/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk());

        verify(service).delete(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"));
    }

    @Test
    void cardListUpdateFailedForIncorrectId() throws Exception {
        mockMvc.perform(
                put("/card-list/id")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void cardListUpdatedById() throws Exception {
        mockMvc.perform(
                put("/card-list/132208b7-6a06-42ea-aedc-a68371545476")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().is(201));
    }
}