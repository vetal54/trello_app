package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.common.Resource;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.service.CommonService;

import java.util.List;
import java.util.UUID;

public class AbstractController<E extends Resource, S extends CommonService<E>> implements CommonController<E> {

    S service;

    public AbstractController(S service){
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<E> create(@RequestBody E resource) {
        E result = service.save(resource);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<E> update(@PathVariable UUID id, @RequestBody E resource) {
        E entity = service.findById(id);
        if (entity == null) throw new ResourceNotFoundException();
        resource.setId(id);
        resource.setCreateDate(entity.getCreateDate());
        E result = service.update(resource);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public HttpStatus delete(@PathVariable UUID id) {
        boolean status = service.delete(id);
        return status ?  HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<E> readById(@PathVariable UUID id) {
        E result = service.findById(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public List<E> readAll() {
        return service.findAll();
    }
}
