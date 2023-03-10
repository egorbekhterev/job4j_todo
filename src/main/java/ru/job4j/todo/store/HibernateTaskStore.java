package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class HibernateTaskStore implements TaskStore {

    private final SessionFactory sf;

    @Override
    public Optional<Task> save(Task task) {
        var session = sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            rsl = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<Task> findById(int id) {
        var session = sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
             rsl = session.createQuery("SELECT i FROM Task i WHERE i.id = :fId", Task.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findAll() {
        var session = sf.openSession();
        List<Task> list = new ArrayList<>();
        try {
            session.beginTransaction();
            var query = session.createQuery("SELECT i FROM Task i ORDER BY i.id ASC", Task.class);
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public boolean deleteById(int id) {
        var session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            var query = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            if (query > 0) {
                rsl = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(Task task) {
        var session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            session.merge(task);
            rsl = true;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findCompleted(boolean isDone) {
        var session = sf.openSession();
        List<Task> list = new ArrayList<>();
        try {
            session.beginTransaction();
            var query = session.createQuery("SELECT i FROM Task i WHERE i.done = :fDone", Task.class)
                    .setParameter("fDone", isDone);
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public boolean updateDoneField(Task task) {
        var session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            var query = session.createQuery("UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            if (query > 0) {
                rsl = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}
