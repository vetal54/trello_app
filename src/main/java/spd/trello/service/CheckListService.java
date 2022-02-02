package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepositoryImpl;

@Service
public class CheckListService extends AbstractService<CheckList, CheckListRepositoryImpl> {

    public CheckListService(CheckListRepositoryImpl repository) {
        super(repository);
    }

    public CheckList create(String name, String email) {
        CheckList checkList = new CheckList();
        checkList.setName(name);
        checkList.setCreateBy(email);
        repository.save(checkList);
        return repository.getById(checkList.getId());
    }
}
