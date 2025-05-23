package com.example.taskmanager.integration;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TaskIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldPersistTaskToDatabase() {
        Task task = new Task(null, "Intégration test", false);

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Intégration test");

        List<Task> allTasks = taskRepository.findAll();
        assertThat(allTasks).hasSize(1);
    }

    @Test
    void shouldDeleteTaskFromDatabase() {
        Task task = new Task(null, "To be deleted", false);
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        assertThat(taskRepository.findById(savedTask.getId())).isEmpty();
    }
}
