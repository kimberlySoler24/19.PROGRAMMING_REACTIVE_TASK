package com.reactivce.task.task;

import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
public class TaskRepositoryGetByIdTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void shouldFindTaskById() {
        Task task = Task.builder().title("test").description("esta es una prueba").status("PENDIENTE")
                .userEmail("kimberly@gmail.com").build();
        taskRepository.save(task).block();

        taskRepository.findById(task.getId())
                .as(StepVerifier::create)
                .expectNextMatches(foundTask -> "test".equals(foundTask.getTitle()))
                .verifyComplete();
    }
}
