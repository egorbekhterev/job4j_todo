package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author: Egor Bekhterev
 * @date: 11.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class CrudRepository {

    private final SessionFactory sf;

    public <T> T tx(Function<Session, T> command) {
        var session = sf.openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
                throw e;
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public boolean run(Function<Session, Boolean> command) {
        return tx(command);
    }

    public boolean run(String query, Map<String, Object> args) {
        Function<Session, Boolean> command = session -> {
            var sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate() > 0;
        };
        return run(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }
}
