package ru.job4j.todo.store;

import ru.job4j.todo.model.Priority;

import java.util.Collection;

/**
 * @author: Egor Bekhterev
 * @date: 14.03.2023
 * @project: job4j_todo
 */
public interface PriorityStore {
    Collection<Priority> findAll();
}
