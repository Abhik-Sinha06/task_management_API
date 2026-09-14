package com.abhik.task_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull
    private boolean completionStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(){
    }

    public Task(String name, boolean completionStatus) {
        this.name = name;
        this.completionStatus = completionStatus;
    }

    public Task(int id, String name, boolean completionStatus) {
        this.id = id;
        this.name = name;
        this.completionStatus = completionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
