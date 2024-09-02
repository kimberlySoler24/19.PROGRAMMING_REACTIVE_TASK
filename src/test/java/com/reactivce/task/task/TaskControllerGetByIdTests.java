package com.reactivce.task.task;

import com.reactivce.task.task.controllers.TaskController;
import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(TaskController.class)
public class TaskControllerGetByIdTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void shouldReturnTaskById() {
        webTestClient.get().uri("/tasks/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(task -> Assertions.assertEquals(1L, task.getId()));
    }

}
