package spd.trello.service;

import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReminderService extends AbstractService<Reminder> {

    public ReminderService(ReminderRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public Reminder create() {
        Reminder reminder = new Reminder();
        print(reminder);
        repository.create(reminder);
        return reminder;
    }

    @Override
    public void print(Reminder reminder) {
        System.out.println(reminder);
    }

    @Override
    public void update(Reminder reminder) {
        repository.update(reminder);
    }

    @Override
    public Reminder findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Reminder> findAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
