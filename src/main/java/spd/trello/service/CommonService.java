package spd.trello.service;

import spd.trello.domian.common.Domain;

import java.util.List;
import java.util.UUID;

public interface CommonService<E extends Domain> {
    E save(E e);
    E update(E e);
    E findById(UUID id);
    List<E> findAll();
    void delete(UUID id);
}
