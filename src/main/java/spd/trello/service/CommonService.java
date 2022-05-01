package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import spd.trello.domian.common.Domain;

import java.util.List;
import java.util.UUID;

public interface CommonService<E extends Domain> {
    E save(E e) throws JsonProcessingException;
    E update(E e) throws JsonProcessingException;
    E findById(UUID id);
    List<E> findAll();
    void delete(UUID id);
}
