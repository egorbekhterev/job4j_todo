package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;
}
