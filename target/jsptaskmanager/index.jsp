<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>JSP Task Manager</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body>
    <header>
      <h1>JSP Task Manager</h1>
    </header>

    <main>
      <h2>My Projects</h2>

      <a href="newProject">Add New Project</a>

      <hr />

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Project Name</th>
            <th>Created On</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="project" items="${projectList}">
            <tr>
              <td><c:out value="${project.id}" /></td>
              <td>
                <a href="listTasks?projectId=<c:out value='${project.id}' />">
                  <c:out value="${project.name}" />
                </a>
              </td>
              <td><c:out value="${project.createdAt}" /></td>
              <td>
                <a href="editProject?id=<c:out value='${project.id}' />"
                  >Edit</a
                >
                &nbsp;&nbsp;&nbsp;
                <a href="deleteProject?id=<c:out value='${project.id}' />"
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
