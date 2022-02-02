package spd.trello.service;

import spd.trello.domain.common.Resource;

import java.util.List;
import java.util.UUID;

public interface CommonService<E extends Resource> {
    E save(E e);
    E update(E e);
    E findById(UUID id);
    List<E> findAll();
    boolean delete(UUID id);
}
