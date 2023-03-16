package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.TaskTimezoneSetter;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 * Класс-контроллер, собирает представления для всех задач TO DO списка, выполненных и невыполненных задач.
 * Учитывает при сборке временную зону, указанную в профиле текущего пользователя.
 */
@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final TaskService taskService;

    @GetMapping({"/", "/index"})
    public String getTasks(Model model, @SessionAttribute("user") User user) {
        var tasks = taskService.findAll();
        tasks.forEach(task -> TaskTimezoneSetter.setTimezone(task, user));
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model, @SessionAttribute("user") User user) {
        var tasks = taskService.findCompleted(true);
        tasks.forEach(task -> TaskTimezoneSetter.setTimezone(task, user));
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model, @SessionAttribute("user") User user) {
        var tasks = taskService.findCompleted(false);
        tasks.forEach(task -> TaskTimezoneSetter.setTimezone(task, user));
        model.addAttribute("tasks", tasks);
        return "index";
    }
}
