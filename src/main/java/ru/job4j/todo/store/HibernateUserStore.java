package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 10.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        var session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            rsl = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        var session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            rsl = session.createQuery(
                    "SELECT i FROM User i WHERE i.login = :fLogin AND i.password = :fPassword", User.class)
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}
