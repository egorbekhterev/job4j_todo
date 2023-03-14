package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.Collection;

/**
 * @author: Egor Bekhterev
 * @date: 14.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private PriorityStore priorityStore;

    @Override
    public Collection<Priority> findAll() {
        return priorityStore.findAll();
    }
}
