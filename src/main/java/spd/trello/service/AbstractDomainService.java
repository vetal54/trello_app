package spd.trello.service;

import spd.trello.domian.common.Domain;
import spd.trello.domian.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractDomainService <E extends Domain, R extends AbstractRepository<E>> implements CommonService<E>{

    protected R repository;

    AbstractDomainService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) {
        return repository.save(e);
    }

    @Override
    public E update(E e) {
        return repository.save(e);
    }

    @Override
    public E findById(UUID id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
