package com.raynald.controller;

import com.raynald.dao.ProjectDAO;
import com.raynald.dao.TaskDAO;
import com.raynald.model.Project;
import com.raynald.model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;


@WebServlet("/") // handles all requests
public class ProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;

    public void init() {
        projectDAO = new ProjectDAO();
        taskDAO = new TaskDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getServletPath();

        try {
            switch (action) {
                // Project POSTs
                case "/newProject":
                    insertProject(request, response);
                    break;
                case "/editProject":
                    updateProject(request, response);
                    break;
                
                // Task POSTs
                case "/newTask":
                    insertTask(request, response);
                    break;
                case "/editTask":
                    updateTask(request, response);
                    break;
                    
                default:
                    doGet(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getServletPath();

        if (action.startsWith("/css/") || action.startsWith("/js/") || action.startsWith("/images/")) {
            getServletContext().getNamedDispatcher("default").forward(request, response);
            return; // Stop processing
        }

        try {
            switch (action) {
                case "/newProject":
                    showNewProjectForm(request, response);
                    break;
                case "/deleteProject":
                    deleteProject(request, response);
                    break;
                case "/editProject":
                    showEditProjectForm(request, response);
                    break;
                case "/listTasks":
                    listTasks(request, response);
                    break;
                
                case "/newTask":
                    showNewTaskForm(request, response);
                    break;
                case "/deleteTask":
                    deleteTask(request, response);
                    break;
                case "/editTask":
                    showEditTaskForm(request, response);
                    break;
                    
                default:
                    listProjects(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void listProjects(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Project> projectList = projectDAO.getAllProjects();
        request.setAttribute("projectList", projectList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewProjectForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("project-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertProject(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String name = request.getParameter("projectName");
        projectDAO.createProject(name);
        response.sendRedirect("./");
    }
    
    private void deleteProject(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        projectDAO.deleteProject(id);
        response.sendRedirect("./");
    }
    
    private void showEditProjectForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Project existingProject = projectDAO.getProjectById(id);
        request.setAttribute("project", existingProject);
        RequestDispatcher dispatcher = request.getRequestDispatcher("project-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("projectName");
        projectDAO.updateProject(id, name);
        response.sendRedirect("./");
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        Project project = projectDAO.getProjectById(projectId);
        List<Task> taskList = taskDAO.getTasksByProjectId(projectId);
        
        request.setAttribute("project", project);
        request.setAttribute("taskList", taskList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("task-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewTaskForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        request.setAttribute("projectId", projectId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTask(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String title = request.getParameter("title");
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");
        
        Date dueDate = null;
        String dueDateStr = request.getParameter("dueDate");
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            dueDate = Date.valueOf(dueDateStr);
        }

        Task newTask = new Task(projectId, title, status, priority, dueDate);
        taskDAO.createTask(newTask);
        
        response.sendRedirect("listTasks?projectId=" + projectId);
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        taskDAO.deleteTask(id);
        response.sendRedirect("listTasks?projectId=" + projectId);
    }
    
    private void showEditTaskForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Task existingTask = taskDAO.getTaskById(id);
        request.setAttribute("task", existingTask);
        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
        dispatcher.forward(request, response);
    }


    private void updateTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String title = request.getParameter("title");
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");
        
        Date dueDate = null;
        String dueDateStr = request.getParameter("dueDate");
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            dueDate = Date.valueOf(dueDateStr);
        }
        
        Task taskToUpdate = new Task(projectId, title, status, priority, dueDate);
        taskToUpdate.setId(id); 
        
        taskDAO.updateTask(taskToUpdate);
        
        response.sendRedirect("listTasks?projectId=" + projectId);
    }
}