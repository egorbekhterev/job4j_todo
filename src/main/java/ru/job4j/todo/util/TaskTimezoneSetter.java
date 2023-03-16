package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * @author: Egor Bekhterev
 * @date: 16.03.2023
 * @project: job4j_todo
 */
public class TaskTimezoneSetter {

    /**
     * Устанавливает временную зону для задачи {@link Task} в соответствии со временной зоной пользователя {@link User},
     * связанного с этой задачей. Используются в контроллере для задач.
     * @param task - задача для которой нужно установить часовой пояс пользователя.
     * @param user - пользователь, чей часовой пояс используется при установке времени создания задачи.
     */
    public static void setTimezone(Task task, User user) {
        ZoneId userTimeZone;
        if (user == null) {
            userTimeZone = TimeZone.getDefault().toZoneId();
        } else {
            userTimeZone = user.getTimezone() != null ? ZoneId
                    .of(user.getTimezone()) : TimeZone.getDefault().toZoneId();
        }
        task.setCreated(task.getCreated().atZone(ZoneOffset.systemDefault())
                .withZoneSameInstant(userTimeZone).toLocalDateTime());
    }
}
