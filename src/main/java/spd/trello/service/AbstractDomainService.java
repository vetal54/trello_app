package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spd.trello.domian.common.Domain;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractDomainService <E extends Domain, R extends AbstractRepository<E>> implements CommonService<E>{

    protected R repository;
    protected ObjectMapper mapper = new ObjectMapper();

    AbstractDomainService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) throws JsonProcessingException {
        return repository.save(e);
    }

    @Override
    public E update(E e) throws JsonProcessingException {
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
