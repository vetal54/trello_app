package spd.trello.service;

import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CheckListService extends AbstractService<CheckList> {

    public CheckListService(CheckListRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public CheckList create() {
        CheckList checkList = new CheckList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter check list name");
        checkList.setName(scanner.nextLine());
        print(checkList);
        repository.create(checkList);
        return checkList;
    }

    @Override
    public void print(CheckList checkList) {
        System.out.println(checkList);
    }

    @Override
    public void update(CheckList checkList) {
        repository.update(checkList);
    }

    @Override
    public CheckList findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<CheckList> findAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
