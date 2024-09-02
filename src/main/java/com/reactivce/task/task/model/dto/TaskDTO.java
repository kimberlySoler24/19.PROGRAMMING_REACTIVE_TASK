package com.reactivce.task.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String userEmail;
}
