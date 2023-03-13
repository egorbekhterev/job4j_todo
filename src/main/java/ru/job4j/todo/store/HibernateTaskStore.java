package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.*;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class HibernateTaskStore implements TaskStore {

    private final CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTaskStore.class.getName());

    @Override
    public Optional<Task> save(Task task) {
        Optional<Task> rsl = Optional.empty();
        try {
            crudRepository.run(session -> {
                session.persist(task);
                return true;
            });
            rsl = Optional.of(task);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Task task) method.", e);
        }
        return rsl;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("SELECT i FROM Task i JOIN FETCH i.priority WHERE i.id = :fId", Task.class, Map.of("fId", id));
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("SELECT i FROM Task i JOIN FETCH i.priority ORDER BY i.id ASC", Task.class);
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.run("DELETE Task WHERE id = :fId", Map.of("fId", id));
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.run(session -> {
            session.merge(task);
            return true;
        });
    }

    @Override
    public Collection<Task> findCompleted(boolean isDone) {
        return crudRepository.query(
                "SELECT i FROM Task i JOIN FETCH i.priority WHERE i.done = :fDone", Task.class, Map.of("fDone", isDone));
    }

    @Override
    public boolean updateDoneField(Task task) {
        return crudRepository.run("UPDATE Task SET done = :fDone WHERE id = :fId", Map.of(
                "fDone", true,
                "fId", task.getId()));
    }
}
