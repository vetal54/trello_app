package spd.trello.service;

import spd.trello.domain.common.Resource;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<E extends Resource, R extends AbstractRepository<E>> implements CommonService<E> {
    protected R repository;

    AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) {
       return repository.save(e);
    }

    @Override
    public E update(E e) {
       return repository.update(e);
    }

    @Override
    public E findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
