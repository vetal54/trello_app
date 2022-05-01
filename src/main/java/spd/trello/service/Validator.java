package spd.trello.service;

import spd.trello.domian.common.Domain;

public interface Validator<E extends Domain> {
    void validateReference(E e);
}
