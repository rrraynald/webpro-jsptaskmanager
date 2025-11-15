package com.raynald.model;

import java.sql.Date;       // Use java.sql.Date for due_date
import java.sql.Timestamp; // Use java.sql.Timestamp for created_at

/**
 * This is the Model class (JavaBean) for a Task.
 * It holds data for a single task.
 */
public class Task {

    // --- Fields ---
    private int id;
    private int projectId;
    private String title;
    private String status;
    private String priority;
    private Date dueDate;
    private Timestamp createdAt;

    // --- Constructor ---
    // We'll create a full constructor for when we fetch data
    public Task(int id, int projectId, String title, String status, String priority, Date dueDate, Timestamp createdAt) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }
    
    // We'll also create a simpler constructor for when we create a new task
    public Task(int projectId, String title, String status, String priority, Date dueDate) {
        this.projectId = projectId;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }


    // --- Getters and Setters ---
    // Standard methods to access the private fields

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