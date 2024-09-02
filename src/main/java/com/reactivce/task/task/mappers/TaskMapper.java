package com.reactivce.task.task.mappers;

import com.reactivce.task.task.model.Task;
import com.reactivce.task.task.model.dto.TaskDTO;

public class TaskMapper {
    public static Task toTask(TaskDTO taskDtoToTask){
        return Task.builder().title(taskDtoToTask.getTitle()).description(taskDtoToTask.getDescription())
                .status(taskDtoToTask.getStatus()).userEmail(taskDtoToTask.getUserEmail()).build();
    }
}
