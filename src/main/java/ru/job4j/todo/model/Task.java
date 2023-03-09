package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;
    private LocalDateTime created;
    private boolean done;
}
