// src/main/java/com/example/taskmanager/controller/TaskController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        Long id = counter.incrementAndGet();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasks.remove(id);
    }
}
