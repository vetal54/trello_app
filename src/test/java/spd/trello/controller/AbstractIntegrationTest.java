package spd.trello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spd.trello.domian.User;
import spd.trello.domian.common.Domain;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest<E extends Domain> {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public Object getValue(MvcResult mvcResult, String jsonPath) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), jsonPath);
    }

    public MvcResult register(String URL_TEMPLATE, User entity) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE, entity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andReturn();
    }

    public MvcResult create(String URL_TEMPLATE, E entity, String token) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE, entity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(entity)))
                .andReturn();
    }

    public MvcResult delete(String URL_TEMPLATE, UUID id, String token) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andReturn();
    }

    public MvcResult getById(String URL_TEMPLATE, UUID id, String token) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andReturn();
    }

    public MvcResult findAll(String URL_TEMPLATE, String token) throws Exception {
        return mockMvc.perform(get(URL_TEMPLATE)
                        .header("Authorization", token))
                .andReturn();
    }

    public MvcResult update(String URL_TEMPLATE, UUID id, E entity, String token) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(URL_TEMPLATE + "/{id}", id, entity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity))
                        .header("Authorization", token))
                .andReturn();
    }
}
