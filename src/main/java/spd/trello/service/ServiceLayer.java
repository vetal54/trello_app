package spd.trello.service;

import spd.trello.domain.Resource;
import spd.trello.repository.IRepository;

import java.util.List;
import java.util.UUID;

public class ServiceLayer<T extends Resource> {

    protected IRepository<T> repository;

    public ServiceLayer(IRepository<T> repository) {
        this.repository = repository;
    }

    public void print(T t) {
        System.out.println(t);
    }

    public void update(T e) {
        repository.update(e);
    }

    public T findById(UUID id) {
        return repository.getById(id);
    }

    public List<T> findAll() {
        return repository.getAll();
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
