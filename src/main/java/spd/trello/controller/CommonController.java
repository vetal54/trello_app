package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spd.trello.domian.common.Domain;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CommonController<E extends Domain> {
    ResponseEntity<E> create(E resource) throws IOException;
    ResponseEntity<E> update(UUID id, E resource);
    HttpStatus delete(UUID id);
    ResponseEntity<E> readById(UUID id);
    List<E> readAll();
}
