package spd.trello.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<E> {
    void create(E e);

    void update(E e);

    E getById(UUID id);

    List<E> getAll();

    boolean delete(UUID id);
}
