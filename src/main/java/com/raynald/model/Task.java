package com.raynald.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {

    private int id;
    private int projectId;
    private String title;
    private String status;
    private String priority;
    private Date dueDate;
    private Timestamp createdAt;

    public Task(int id, int projectId, String title, String status, String priority, Date dueDate, Timestamp createdAt) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }
    
    public Task(int projectId, String title, String status, String priority, Date dueDate) {
        this.projectId = projectId;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}