package com.abhik.task_management.dto;

public class TaskResponseDTO {
    private Integer id;
    private String name;
    private boolean completionStatus;

    public TaskResponseDTO(Integer id, String name, boolean completionStatus) {
        this.id = id;
        this.name = name;
        this.completionStatus = completionStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
