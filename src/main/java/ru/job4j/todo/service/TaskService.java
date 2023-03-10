package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
public interface TaskService {

    Optional<Task> save(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    boolean deleteById(int id);

    boolean update(Task task);

    Collection<Task> findCompleted(boolean isDone);

    boolean updateDoneField(Task task);
}
