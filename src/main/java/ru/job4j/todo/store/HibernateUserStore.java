package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
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

    private final CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserStore.class.getName());

    @Override
    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.run(session -> {
                session.persist(user);
                return true;
            });
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Error in the save(User user) method.", e);
        }
        return rsl;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "SELECT i FROM User i WHERE i.login = :fLogin AND i.password = :fPassword", User.class,
                Map.of("fLogin", login,
                        "fPassword", password)
        );
    }

    /**
     * Записывает часовой пояс пользователя {@link User} в таблицу users по ID текущей сессии пользователя.
     * @param user - пользователь текущей сессии.
     * @return true - если изменение поля произведено, иначе false.
     */
    @Override
    public boolean updateTimezone(User user) {
            return crudRepository.run("UPDATE User SET timezone = :fTimezone WHERE id = :fId", Map.of(
                    "fTimezone", user.getTimezone(),
                    "fId", user.getId()));
    }
}
