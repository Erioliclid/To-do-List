package org.example.todolist.service;

import jakarta.transaction.Transactional;
import org.example.todolist.model.Task;
import org.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    //Get
    public List<Task> getAllTasks() {
       return taskRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    public List <Task> getTaskByCompleted() {
       return taskRepository.findByCompleted(true);
    }
    public boolean existsTaskByName(String name) {
        return taskRepository.existsByName(name);
    }
    public int countTaskByName(String name) {
        return taskRepository.countByName(name);
    }
    //Post
    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    //Put
    @Transactional
    public Task updateTask(Task task) {
        // Проверяем, что задача существует
        if (task.getId() == 0 || !taskRepository.existsById(task.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Task with id " + task.getId() + " not found");
        }
        return taskRepository.save(task);
    }
    //Delete
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
