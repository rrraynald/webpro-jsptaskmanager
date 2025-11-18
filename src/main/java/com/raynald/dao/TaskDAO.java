package com.raynald.dao;

import com.raynald.model.Task;
import com.raynald.util.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    /**
     * Feature: Read (List) all Tasks for a specific project.
     * Fetches all tasks from the database that belong to one project.
     * @param projectId The ID of the project whose tasks we want.
     * @return A List of Task objects.
     */
    public List<Task> getTasksByProjectId(int projectId) {
        
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, projectId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String status = rs.getString("status");
                    String priority = rs.getString("priority");
                    Date dueDate = rs.getDate("due_date");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    
                    taskList.add(new Task(id, projectId, title, status, priority, dueDate, createdAt));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return taskList;
    }

    /**
     * Feature: Create a new Task
     * Inserts a new task into the database.
     * @param task A Task object containing all the data from the form.
     */
    public void createTask(Task task) {
        
        String sql = "INSERT INTO tasks (project_id, title, status, priority, due_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, task.getProjectId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getStatus());
            stmt.setString(4, task.getPriority());
            stmt.setDate(5, task.getDueDate());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Feature: Delete a Task
     * Deletes a task from the database based on its ID.
     * @param id The ID of the task to delete.
     */
    public void deleteTask(int id) {
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Feature: Read (Single) Task
     * Fetches a single task from the database by its ID.
     * @param id The ID of the task to fetch.
     * @return A Task object, or null if not found.
     */
    public Task getTaskById(int id) {
        Task task = null;
        String sql = "SELECT * FROM tasks WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int projectId = rs.getInt("project_id");
                    String title = rs.getString("title");
                    String status = rs.getString("status");
                    String priority = rs.getString("priority");
                    Date dueDate = rs.getDate("due_date");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    
                    task = new Task(id, projectId, title, status, priority, dueDate, createdAt);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return task;
    }

    /**
     * Feature: Update a Task
     * Updates an existing task in the database.
     * @param task A Task object containing the new data.
     */
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, status = ?, priority = ?, due_date = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getStatus());
            stmt.setString(3, task.getPriority());
            stmt.setDate(4, task.getDueDate());
            stmt.setInt(5, task.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}