package spd.trello.service;

import spd.trello.domain.Resource;
import spd.trello.repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractService<T extends Resource> {

    protected IRepository<T> repository;

    abstract T create();

    abstract void print(T t);

    abstract void update(T e);

    abstract Optional<T> findById(UUID id);

    abstract List<T> findAll();

    abstract boolean delete(UUID id);

    public AbstractService(IRepository<T> repository) {
        this.repository = repository;
    }
}
