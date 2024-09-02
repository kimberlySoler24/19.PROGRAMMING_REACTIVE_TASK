package com.reactivce.task.task.services;

import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.model.dto.TaskDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<Task> getTaskById(Long id) throws InterruptedException;
    Flux<Task> getAllTask();
    Mono<Task> createTask(TaskDTO createTask);
    Mono<Void> deleteTask(Long id);
    Mono<Task> updateTask(Task updateTask);
    Flux<Task> findByUserEmail(String userEmail);

}
