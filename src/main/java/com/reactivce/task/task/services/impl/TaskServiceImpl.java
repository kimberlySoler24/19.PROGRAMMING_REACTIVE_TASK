package com.reactivce.task.task.services.impl;

import com.reactivce.task.task.handlers.TaskNotFoundException;
import com.reactivce.task.task.handlers.ValidationException;
import com.reactivce.task.task.mappers.TaskMapper;
import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.model.dto.TaskDTO;
import com.reactivce.task.task.repositories.TaskRepository;
import com.reactivce.task.task.services.TaskService;
import com.reactivce.task.task.validations.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    private TaskValidator taskValidator;
    @Override
    public Mono<Task> getTaskById(Long id) throws InterruptedException {
        return taskRepository.findById(id).switchIfEmpty(Mono.error
                        (new TaskNotFoundException("Task not found with id: " + id)))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve task by id: {}", id))
                .doOnNext(users -> logger.info("got task by id: {}", id))
                .doOnError(error -> logger.error("Error retrieving task: {}", error.getMessage()));
    }
    @Override
    public Flux<Task> getAllTask() {
        return taskRepository.findAll()
                .switchIfEmpty(Mono.error
                        (new TaskNotFoundException("we don't find task on database")))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve all tasks"))
                .doOnNext(tasks -> logger.debug("Retrieved task: {}", tasks.getTitle()))
                .doOnError(error -> logger.error("Error retrieving tasks: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed retrieving all users"));
    }
    @Override
    public Mono<Task> createTask(TaskDTO createTask) {
        return Mono.just(TaskMapper.toTask(createTask))
                .doOnSubscribe(subscription -> logger.info("Request to create new task: {}", createTask))
                .flatMap(task -> {
                    Errors errors = new BeanPropertyBindingResult(task, "task");
                    taskValidator.validate(task, errors);

                    if (errors.hasErrors()) {
                        String errorMessage = errors.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", "));
                        logger.error("Validation error for task: {}", errorMessage);
                        return Mono.error(new ValidationException(errorMessage));
                    }

                    return taskRepository.save(task)
                            .doOnNext(savedTask -> logger.info("Created new task: {}", savedTask))
                            .doOnError(error -> logger.error("Error creating task: {}", error.getMessage()));
                })
                .doOnError(error -> {
                    if (!(error instanceof ValidationException)) {
                        logger.error("Unexpected error creating task: {}", error.getMessage());
                    }
                });
    }

    @Override
    public Mono<Void> deleteTask(Long id) {
        return taskRepository.findById(id)
                .flatMap(taskRepository::delete).switchIfEmpty(Mono.error
                        (new TaskNotFoundException("Task not found with id: " + id)))
                .doOnSubscribe(subscription -> logger.info("delete user"))
                .doOnNext(users -> logger.debug("deleted task with ID: {}", id))
                .doOnError(error -> logger.error("Error deleting task: {}", error.getMessage()));
    }

    @Override
    public Mono<Task> updateTask(Task updateTask) {
        return taskRepository.findById(updateTask.getId())
                .switchIfEmpty(Mono.error
                        (new TaskNotFoundException("Task not found with id: " + updateTask.getId())))
                .flatMap(existingTask -> {
                    if (updateTask.getTitle() != null) existingTask.setTitle(updateTask.getTitle());
                    if (updateTask.getDescription() != null) existingTask.setDescription(updateTask.getDescription());
                    if (updateTask.getStatus() != null) existingTask.setStatus(updateTask.getStatus());
                    if (updateTask.getUserEmail() != null) existingTask.setUserEmail(updateTask.getUserEmail());


                    return taskRepository.save(existingTask);
                }).doOnSubscribe(subscription -> logger.info("Request to updated task by id: {}", updateTask.getTitle()))
                .doOnNext(users -> logger.debug("updated task: {}", updateTask.getTitle()))
                .doOnError(error -> logger.error("Error update task: {}", error.getMessage()));
    }

    @Override
    public Flux<Task> findByUserEmail(String userEmail) {
        return taskRepository.findByUserEmail(userEmail);
    }
}
