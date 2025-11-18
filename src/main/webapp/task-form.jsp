<%@ page language="java" contentType="text/HTML; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    
    <title>
        <c:if test="${task != null}">Edit Task</c:if>
        <c:if test="${task == null}">Add New Task</c:if>
    </title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div class="container">
        <header>
            <c:set var="projectId" value="${task != null ? task.projectId : projectId}" />
        
            <a href="listTasks?projectId=<c:out value='${projectId}' />">&lt;&lt; Back to Tasks</a>
            
            <h1>
                <c:if test="${task != null}">Edit Task</c:if>
                <c:if test="${task == null}">Add New Task</c:if>
            </h1>
        </header>
    
        <main>
            <c:set var="actionUrl" value="${task != null ? 'editTask' : 'newTask'}" />
    
            <form action="${actionUrl}" method="POST">
                
                <c:if test="${task != null}">
                    <input type="hidden" name="id" value="<c:out value='${task.id}' />" />
                </c:if>
            
                <input type="hidden" name="projectId" value="<c:out value='${projectId}' />" />
    
                <div>
                    <label for="title">Task Title:</label>
                    <input type="text" id="title" name="title" required 
                           value="<c:out value='${task.title}' />" />
                </div>
                
                <div>
                    <label for="status">Status:</label>
                    <select id="status" name="status">
                        <option value="Not Completed" ${task.status == 'Not Completed' ? 'selected' : ''}>Not Completed</option>
                        <option value="In Progress" ${task.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                        <option value="Completed" ${task.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    </select>
                </div>
                
                <div>
                    <label for="priority">Priority:</label>
                    <select id="priority" name="priority">
                        <option value="Low" ${task.priority == 'Low' ? 'selected' : ''}>Low</option>
                        <option value="Medium" ${task.priority == 'Medium' ? 'selected' : ''}>Medium</option>
                        <option value="High" ${task.priority == 'High' ? 'selected' : ''}>High</option>
                    </select>
                </div>
                
                <div>
                    <label for="dueDate">Due Date:</label>
                    <input type="date" id="dueDate" name="dueDate" 
                           value="<c:out value='${task.dueDate}' />" />
                </div>
                
                <button type="submit">Save Task</button>
            </form>
        </main>
    </div> 

</body>
</html>