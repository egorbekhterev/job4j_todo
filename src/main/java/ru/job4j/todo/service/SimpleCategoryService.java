package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;

/**
 * @author: Egor Bekhterev
 * @date: 13.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private CategoryStore categoryStore;

    @Override
    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    @Override
    public List<Category> findByIDs(List<Integer> categoryIDs) {
        return categoryStore.findByIDs(categoryIDs);
    }
}
