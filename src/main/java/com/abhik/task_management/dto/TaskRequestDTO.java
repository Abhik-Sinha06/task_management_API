package com.abhik.task_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequestDTO {

    @NotBlank(message = "Task name cannot be empty")
    @Size(min = 5, max = 75, message = "Task name length isn't proper")
    private String name;

    private boolean completionStatus;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String name, boolean completionStatus) {
        this.name = name;
        this.completionStatus = completionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }
}
