package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 10.03.2023
 * @project: job4j_todo
 */
public interface UserStore {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
