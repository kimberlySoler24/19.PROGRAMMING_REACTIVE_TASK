package com.reactivce.task.task.repositories;

import com.reactivce.task.task.model.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    Flux<Task> findByUserEmail(String userEmail);
}
