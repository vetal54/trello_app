package spd.trello.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spd.trello.domian.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.CommonService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class AbstractResourceController<E extends Resource, S extends CommonService<E>> implements CommonController<E> {

    S service;

    public AbstractResourceController(S service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    @Override
    public ResponseEntity<E> create(@Valid @RequestBody E resource) throws IOException {
        E result = service.save(resource);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    @Override
    public ResponseEntity<E> update(@PathVariable UUID id, @Valid @RequestBody E resource) {
        E entity = service.findById(id);
        if (entity == null) throw new ResourceNotFoundException();
        resource.setId(id);
        resource.setCreateDate(entity.getCreateDate());
        E result = service.update(resource);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public HttpStatus delete(@PathVariable UUID id) {
        service.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<E> readById(@PathVariable UUID id) {
        E result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public List<E> readAll() {
        return service.findAll();
    }
}
