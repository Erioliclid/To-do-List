package org.example.todolist.controller;

import org.example.todolist.model.Task;
import org.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Get
    @GetMapping("/all_tasks")
    public List<Task> showAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task showTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/done")
    public List<Task> showAllDoneTasks() {
        return taskService.getTaskByCompleted();
    }

    @GetMapping("/exist_by_name")
    public boolean existsByName(@RequestParam String name) {
        return taskService.existsTaskByName(name);
    }

    @GetMapping("/count_by_name")
    public int countByName(@RequestParam String name) {
        return taskService.countTaskByName(name);
    }

    @GetMapping("/debug/users")
    public String debugUsers() {
        return "InMemory users: user/1234, admin/12345";
    }

    //Post
    @PostMapping("/new_task")
    public Task createTask(@RequestBody Task task, Authentication authentication) {
        if (task.getName() == null || task.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        } else {
            return taskService.createTask(task, authentication.getName());
        }
    }

    //Put
    @PutMapping("/update_task")
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    //Delete
    @DeleteMapping("/delete_by_id")
    public void deleteTask(@RequestParam Long id) {
        taskService.deleteTaskById(id);
    }

    //Patch
    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    task.setName((String) value);
                    break;
                case "description":
                    task.setDescription((String) value);
                    break;
                case "completed":
                    if (value instanceof Boolean) {
                        task.setCompleted((Boolean) value);
                    } else if (value instanceof String) {
                        task.setCompleted(Boolean.parseBoolean((String) value));
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Field 'completed' must be boolean");
                    }
                    break;
            }
        });


        return taskService.updateTask(task);
    }
}
