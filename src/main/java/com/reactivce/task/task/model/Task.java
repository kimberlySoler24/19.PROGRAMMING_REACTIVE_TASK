package com.reactivce.task.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task{
    @Id
    private Long id;
    private String title;
    private String description;
    private String status;
    @Column(value = "userEmail")
    private String userEmail;
}
