<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Tasks for: <c:out value="${project.name}" /></title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body>
    <header>
      <a href="./">&lt;&lt; Back to All Projects</a>

      <h1>Tasks for: <c:out value="${project.name}" /></h1>
    </header>

    <main>
      <h2>My Tasks</h2>

      <a href="newTask?projectId=<c:out value='${project.id}' />"
        >Add New Task</a
      >

      <hr />

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Status</th>
            <th>Priority</th>
            <th>Due Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="task" items="${taskList}">
            <tr>
              <td><c:out value="${task.id}" /></td>
              <td><c:out value="${task.title}" /></td>
              <td><c:out value="${task.status}" /></td>
              <td><c:out value="${task.priority}" /></td>
              <td><c:out value="${task.dueDate}" /></td>
              <td>
                <a
                  href="editTask?id=<c:out value='${task.id}' />&projectId=<c:out value='${project.id}' />"
                  >Edit</a
                >
                &nbsp;&nbsp;&nbsp;
                <a
                  href="deleteTask?id=<c:out value='${task.id}' />&projectId=<c:out value='${project.id}' />"
                  >Delete</a
                >
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </main>
  </body>
</html>
