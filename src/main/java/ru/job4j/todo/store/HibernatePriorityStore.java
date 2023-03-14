package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;

/**
 * @author: Egor Bekhterev
 * @date: 14.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query("SELECT i FROM Priority i ORDER BY id", Priority.class);
    }
}
