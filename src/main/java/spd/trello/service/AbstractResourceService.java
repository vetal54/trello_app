package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spd.trello.domian.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Slf4j
public abstract class AbstractResourceService<E extends Resource, R extends AbstractRepository<E>> implements CommonService<E> {

    protected R repository;
    protected final ObjectMapper mapper = new ObjectMapper();

    AbstractResourceService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) throws JsonProcessingException {
        log.info("Resource save");
        return repository.save(e);
    }

    @Override
    public E update(E e) throws JsonProcessingException {
        log.info("Resource update");
        return repository.save(e);
    }

    @Override
    public E findById(UUID id) {
        log.info("Resource findById");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not such resource"));
    }

    @Override
    public List<E> findAll() {
        log.info("Resource found all");
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        log.info("Resource delete");
        repository.deleteById(id);
    }
}
