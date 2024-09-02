package com.reactivce.task.task.validations;

import com.reactivce.task.task.model.Task;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class TaskValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Task user = (Task) target;
        if (user.getTitle() == null || user.getTitle().isEmpty()) {
            errors.rejectValue("title", "task.title.empty", "task title cannot be empty");
        }
        if (user.getDescription() == null || user.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "task.description.empty", "description cannot be empty");
        }
        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            errors.rejectValue("status", "task.status.empty", "status cannot be empty");
        }
        if (user.getUserEmail() == null || user.getUserEmail().trim().isEmpty()) {
            errors.rejectValue("user email", "task.User_email.empty", "id user cannot be empty");
        }
    }
}