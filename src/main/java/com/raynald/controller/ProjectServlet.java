package com.raynald.controller;

// DAO and Model imports
import com.raynald.dao.ProjectDAO;
import com.raynald.dao.TaskDAO;
import com.raynald.model.Project;
import com.raynald.model.Task;

// Servlet imports
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Utility imports
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

/**
 * Servlet implementation class ProjectServlet
 * This Controller handles all requests for Projects AND Tasks.
 */
@WebServlet("/") // This servlet handles all requests
public class ProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;

    public void init() {
        projectDAO = new ProjectDAO();
        taskDAO = new TaskDAO();
    }

    /**
     * This method acts as our "router" for POST requests.
     */
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

    /**
     * This method acts as our "router" for GET requests.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getServletPath();

        // --- FIX FOR CSS/STATIC FILES ---
        // If the request is for a static file, let Tomcat handle it
        if (action.startsWith("/css/") || action.startsWith("/js/") || action.startsWith("/images/")) {
            getServletContext().getNamedDispatcher("default").forward(request, response);
            return; // Stop processing
        }
        // --- END OF FIX ---

        try {
            switch (action) {
                // Project routes
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
                
                // Task routes
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
                    // By default (URL "/"), list all projects
                    listProjects(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    
    // --- PROJECT HELPER METHODS ---

    /**
     * Gets the list of projects and sends them to index.jsp
     */
    private void listProjects(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Project> projectList = projectDAO.getAllProjects();
        request.setAttribute("projectList", projectList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Shows the new project form (project-form.jsp)
     */
    private void showNewProjectForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("project-form.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Handles the form submission for a new project
     */
    private void insertProject(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String name = request.getParameter("projectName");
        projectDAO.createProject(name);
        response.sendRedirect("./");
    }
    
    /**
     * Handles the delete request for a project
     */
    private void deleteProject(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        projectDAO.deleteProject(id);
        response.sendRedirect("./");
    }
    
    /**
     * Shows the edit project form (project-form.jsp) pre-filled with data
     */
    private void showEditProjectForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Project existingProject = projectDAO.getProjectById(id);
        request.setAttribute("project", existingProject);
        RequestDispatcher dispatcher = request.getRequestDispatcher("project-form.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the form submission for an updated project
     */
    private void updateProject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("projectName");
        projectDAO.updateProject(id, name);
        response.sendRedirect("./");
    }

    // --- TASK HELPER METHODS ---

    /**
     * Gets the project AND its list of tasks and sends them to task-list.jsp
     */
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

    /**
     * Shows the new task form (task-form.jsp)
     */
    private void showNewTaskForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        request.setAttribute("projectId", projectId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the form submission for a new task
     */
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

    /**
     * Handles the delete request for a task
     */
    private void deleteTask(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        taskDAO.deleteTask(id);
        response.sendRedirect("listTasks?projectId=" + projectId);
    }
    
    /**
     * Shows the edit task form (task-form.jsp) pre-filled with data
     */
    private void showEditTaskForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Task existingTask = taskDAO.getTaskById(id);
        request.setAttribute("task", existingTask);
        RequestDispatcher dispatcher = request.getRequestDispatcher("task-form.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the form submission for an updated task
     */
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