package com.reactivce.task.task.controllers;

import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.model.dto.TaskDTO;
import com.reactivce.task.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) throws InterruptedException{
        return taskService.getTaskById(id);
    }

    @GetMapping
    public Flux<Task> getAllUsers() {
        return taskService.getAllTask();
    }

    @PostMapping
    public Mono<Task> createUser(@RequestBody TaskDTO task) throws InterruptedException{
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Mono<Task> updateUser(@RequestBody Task task) throws InterruptedException{
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) throws InterruptedException{
        return taskService.deleteTask(id).then(Mono.just(ResponseEntity.ok("Eliminado correctamente")));
    }

    @GetMapping("/user/{userEmail}")
    public Flux<Task> getTaskByUserEmail(@PathVariable String userEmail) throws InterruptedException{
        return taskService.findByUserEmail(userEmail).delayElements(Duration.ofSeconds(1));
    }
}
