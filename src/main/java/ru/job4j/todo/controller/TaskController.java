package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Egor Bekhterev
 * @date: 09.03.2023
 * @project: job4j_todo
 */
@Controller
@ThreadSafe
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model, HttpServletRequest request) {
        var session = request.getSession();
        var user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "No user with the given ID is found.");
            return "errors/404";
        }
        task.setUser(user);
        taskService.save(task);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "No task with the given ID is found.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("update/{id}")
    public String getByIdUpdate(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "No task with the given ID is found.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/update/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
            var isUpdated = taskService.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "No task with the given ID is found.");
                return "errors/404";
            }
            return "redirect:/index";
    }

    @GetMapping("delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "No task with the given ID is found.");
            return "errors/404";
        }
        return "redirect:/index";
    }

    @GetMapping("complete/{id}")
    public String complete(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "No task with the given ID is found.");
            return "errors/404";
        }
        taskService.updateDoneField(taskOptional.get());
        return "redirect:/index";
    }
}
