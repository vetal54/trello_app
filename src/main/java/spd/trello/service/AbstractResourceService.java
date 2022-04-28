package spd.trello.service;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import spd.trello.domian.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Log4j2
public abstract class AbstractResourceService<E extends Resource, R extends AbstractRepository<E>> implements CommonService<E> {

    protected R repository;

    AbstractResourceService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) {
        log.info("Resource saved successfully");
        return repository.save(e);
    }

    @Override
    public E update(E e) {
        log.info("Resource updated successfully");
        return repository.save(e);
    }

    @Override
    public E findById(UUID id) {
        log.info("Resource found successfully");
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<E> findAll() {
        log.info("Resources found successfully");
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        log.info("Resource deleted successfully");
        findById(id);
        repository.deleteById(id);
    }
}
