package com.raynald.dao;


import com.raynald.util.DBUtil;

import com.raynald.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    /**
     * Feature: Read (List) all Projects
     * Fetches all projects from the database.
     * @return A List of Project objects.
     */
    public List<Project> getAllProjects() {
        
        List<Project> projectList = new ArrayList<>();
        String sql = "SELECT * FROM projects ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Timestamp createdAt = rs.getTimestamp("created_at");
                
                projectList.add(new Project(id, name, createdAt));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return projectList;
    }

    /**
     * Feature: Create a new Project
     * Inserts a new project into the database.
     * @param projectName The name of the new project from the form.
     */
    public void createProject(String projectName) {
        
        // The SQL query to insert a new project.
        // We use a '?' as a placeholder to prevent SQL injection.
        String sql = "INSERT INTO projects (name) VALUES (?)";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, projectName);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Feature: Delete a Project
     * Deletes a project from the database based on its ID.
     * @param id The ID of the project to delete.
     */
    public void deleteProject(int id) {
        
        // The SQL query to delete a project.
        String sql = "DELETE FROM projects WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the ID for the '?' placeholder
            stmt.setInt(1, id);
            
            // Execute the query
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    /**
     * Feature: Read (Single) Project
     * Fetches a single project from the database by its ID.
     * @param id The ID of the project to fetch.
     * @return A Project object, or null if not found.
     */
    public Project getProjectById(int id) {
        Project project = null;
        String sql = "SELECT * FROM projects WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // If we found a result, create the project object
                if (rs.next()) {
                    String name = rs.getString("name");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    
                    project = new Project(id, name, createdAt);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return project;
    }

    /**
     * Feature: Update a Project
     * Updates an existing project's name in the database.
     * @param id The ID of the project to update.
     * @param name The new name for the project.
     */
    public void updateProject(int id, String name) {
        String sql = "UPDATE projects SET name = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the new name (parameter 1)
            stmt.setString(1, name);
            // Set the ID (parameter 2)
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}