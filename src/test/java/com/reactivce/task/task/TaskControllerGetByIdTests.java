package com.reactivce.task.task;

import com.reactivce.task.task.controllers.TaskController;
import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TaskControllerGetByIdTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    @MockBean
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        Task task = new Task(1L, "Sample Title", "Sample Description", "Sample Status", "sample@example.com");
        Mockito.when(taskRepository.findById(1L)).thenReturn(Mono.just(task));
    }

    @Test
    public void shouldReturnTaskById() {
        webTestClient.get().uri("/api/tasks/getById/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(task -> Assertions.assertEquals(1L, task.getId()));
    }

}
