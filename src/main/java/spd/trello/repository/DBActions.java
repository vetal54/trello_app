package spd.trello.repository;

import java.util.UUID;

public interface DBActions<E> {
    void create(E e);

    void update(E e);

    E get(UUID id);

    boolean delete(UUID id);
}
