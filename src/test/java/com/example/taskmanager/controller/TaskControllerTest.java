package com.example.taskmanager.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.taskmanager.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanup() throws Exception {
        String json = mockMvc.perform(get("/tasks"))
                .andReturn().getResponse().getContentAsString();

        Task[] tasks = objectMapper.readValue(json, Task[].class);
        for (Task task : tasks) {
            mockMvc.perform(delete("/tasks/" + task.getId()).with(csrf()))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @WithMockUser
    void shouldAddAndGetTask() throws Exception {
        Task task = new Task(null, "Apprendre Jenkins", false);

        mockMvc.perform(post("/tasks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Apprendre Jenkins"));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void shouldDeleteTask() throws Exception {
        Task task = new Task(null, "Tâche à supprimer", false);

        String json = objectMapper.writeValueAsString(task);
        String response = mockMvc.perform(post("/tasks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn().getResponse().getContentAsString();

        Task createdTask = objectMapper.readValue(response, Task.class);

        mockMvc.perform(delete("/tasks/" + createdTask.getId())
                .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks"))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}

