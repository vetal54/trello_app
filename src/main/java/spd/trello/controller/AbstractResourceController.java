package spd.trello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spd.trello.domian.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.CommonService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Log4j2
public class AbstractResourceController<E extends Resource, S extends CommonService<E>> implements CommonController<E> {

    S service;
    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractResourceController(S service) {
        this.service = service;
    }

    @SneakyThrows
    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    @Override
    public ResponseEntity<E> create(@Valid @RequestBody E resource) {
        log.trace("Entering create() method");
        E result = service.save(resource);
        log.info("Resource created successfully {}", mapper.writeValueAsString(resource));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    @Override
    public ResponseEntity<E> update(@PathVariable UUID id, @Valid @RequestBody E resource) {
        log.trace("Entering update() method");
        E entity = service.findById(id);
        if (entity == null) throw new ResourceNotFoundException();
        resource.setId(id);
        resource.setCreateDate(entity.getCreateDate());
        log.info("Resource update successfully {}", mapper.writeValueAsString(resource));
        E result = service.update(resource);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public HttpStatus delete(@PathVariable UUID id) {
        log.trace("Entering delete() method");
        service.delete(id);
        log.info("Resource delete successfully by id {}", id);
        return HttpStatus.OK;
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<E> readById(@PathVariable UUID id) {
        log.trace("Entering readById() method");
        E result = service.findById(id);
        log.info("Resource find successfully {}", mapper.writeValueAsString(result));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping
    @Override
    public List<E> readAll() {
        log.trace("Entering readAll() method");
        List<E> result = service.findAll();
        log.info("All resources successfully found {}", mapper.writeValueAsString(result));
        return result;
    }
}
