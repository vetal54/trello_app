package spd.trello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spd.trello.domian.Card;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.CardService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    @MockBean
    private CardService service;

    @Autowired
    private MockMvc mockMvc;
    private Card card;

    private final String json = """
            {
              "archived": true,
              "cardListId": "132208b7-6a06-42ea-aedc-a68371545476",
              "checkList": {
              },
              "createBy": "string",
              "description": "string",
              "name": "string",
              "reminder": {
                "active": true,
                "end": "2023-02-18T12:15:45.886+00:00",
                "remindOn": "2022-02-18T12:15:45.886+00:00",
                "start": "2021-02-18T12:15:45.886+00:00"
              }
            }""";

    private final String updateJson = """
            {
                "id": "5a50f8d0-03c0-4dc5-9fd5-4f2a1a0f6307",
                "createBy": "string",
                "updateBy": "vitaliy",
                "createDate": "2022-02-18T13:21:56.454+00:00",
                "updateDate": null,
                "name": "string",
                "description": "string",
                "archived": true,
                "cardListId": "132208b7-6a06-42ea-aedc-a68371545476",
                "reminder": {
                    "id": "691ac830-0fd0-40c5-933b-998f2df701e9",
                    "start": "2021-02-18T12:15:45.886+00:00",
                    "end": "2023-02-18T12:15:45.886+00:00",
                    "remindOn": "2022-02-18T12:15:45.886+00:00",
                    "active": false
                },
                "checkList": {
                    "id": "c0428b6c-c17b-42f9-8810-a7aade404760",
                    "name": "string",
                    "items": [
                        {
                            "id": "18f4f822-60da-4cac-8eee-211274f6ca3a",
                            "name": "item",
                            "checked": false
                        }
                    ]
                }
            }""";

    @BeforeEach
    void init() {
        card = new Card();
        card.setCreateBy("string");
        card.setName("string");
    }

    @Test
    void postCardFailed() throws Exception {
        mockMvc.perform(post("/card"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardPost() throws Exception {
        mockMvc.perform(
                        post("/card")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(201));
    }

    @Test
    void cardNotFoundBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(get("/card/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardNotFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/card/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cardFound() throws Exception {
        when(service.findById(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2")))
                .thenReturn(card);

        mockMvc.perform(get("/card/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("string"))
                .andExpect(jsonPath("$.createBy").value("string"))
                .andReturn();
    }

    @Test
    void cardFindAllEmptyList() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(service, times(1)).findAll();
    }

    @Test
    void cardFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(card));

        mockMvc.perform(get("/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(card.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("string"))
                .andExpect(jsonPath("$[0].createBy").value("string"));

        verify(service, times(1)).findAll();
    }

    @Test
    void cardNotDeletedBecauseOfIncorrectId() throws Exception {
        mockMvc.perform(delete("/card/id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cardDeleted() throws Exception {
        mockMvc.perform(delete("/card/b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"))
                .andExpect(status().isOk());

        verify(service).delete(UUID.fromString("b8f5e12d-20b2-4d38-9fea-9b8d9d5e2ba2"));
    }

    @Test
    void cardUpdateFailedForIncorrectId() throws Exception {
        mockMvc.perform(
                put("/card/id")
                        .contentType(MediaType.APPLICATION_JSON).content(updateJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void cardUpdatedById() throws Exception {
        when(service.findById(card.getId()))
                .thenReturn(card);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                put("/card/" + card.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(card))
        ).andExpect(status().isOk());
    }
}