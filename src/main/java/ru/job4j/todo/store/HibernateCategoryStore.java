package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

/**
 * @author: Egor Bekhterev
 * @date: 13.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryStore {

    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("SELECT i FROM Category i ORDER BY id", Category.class);
    }

    @Override
    public List<Category> findByIDs(List<Integer> categoryIDs) {
        return crudRepository.query("SELECT i FROM Category i WHERE i.id IN :fId", Category.class,
                Map.of("fId", categoryIDs));
    }
}
