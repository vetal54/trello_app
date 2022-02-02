package spd.trello.repository;

import spd.trello.domain.common.Resource;

import java.util.List;
import java.util.UUID;

public interface AbstractRepository<E extends Resource> {
    E save(E e);

    E update(E e);

    E getById(UUID id);

    List<E> getAll();

    boolean delete(UUID id);
}
