package spd.trello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spd.trello.domian.common.Domain;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CommonController<E extends Domain> {
    ResponseEntity<E> create(@Valid E resource) throws IOException;
    ResponseEntity<E> update(UUID id, @Valid E resource) throws JsonProcessingException;
    HttpStatus delete(UUID id);
    ResponseEntity<E> readById(UUID id) throws JsonProcessingException;
    List<E> readAll() throws JsonProcessingException;
}
