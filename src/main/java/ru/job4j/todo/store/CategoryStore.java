package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * @author: Egor Bekhterev
 * @date: 13.03.2023
 * @project: job4j_todo
 */
public interface CategoryStore {
    List<Category> findAll();

    List<Category> findByIDs(List<Integer> categoryIDs);
}
