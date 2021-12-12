package spd.trello.service;

import spd.trello.domain.Resource;

public abstract class AbstractService<T extends Resource> {
    abstract T create();

    abstract void print(T t);
}
