package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.CheckList;

@Service
public class CheckListService {

    public CheckList create(String name) {
        CheckList checkList = new CheckList();
        checkList.setName(name);
        return checkList;
    }
}
