<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    
    <title>
        <c:if test="${project != null}">
            Edit Project
        </c:if>
        <c:if test="${project == null}">
            Add New Project
        </c:if>
    </title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <header>
        <h1>
            <c:if test="${project != null}">
                Edit Project
            </c:if>
            <c:if test="${project == null}">
                Add New Project
            </c:if>
        </h1>
    </header>

<%--     <main>
        <c:if test="${project != null}">
            <form action="editProject" method="POST">
                <input type="hidden" name="id" value="<c:out value='${project.id}' />" />
        </c:if>
        <c:if test="${project == null}">
            <form action="newProject" method="POST">
        </c:if>

            <label for="name">Project Name:</label>
            <input type="text" id="name" name="projectName" required 
                   value="<c:out value='${project.name}' />" />
            
            <button type="submit">Save Project</button>
        </form>
    </main> --%>
    
    <main>
        <c:set var="actionUrl" value="${project != null ? 'editProject' : 'newProject'}" />

        <form action="${actionUrl}" method="POST">
            
            <c:if test="${project != null}">
                <input type="hidden" name="id" value="<c:out value='${project.id}' />" />
            </c:if>

            <label for="name">Project Name:</label>
            <input type="text" id="name" name="projectName" required 
                   value="<c:out value='${project.name}' />" />
            
            <button type="submit">Save Project</button>
        </form>
    </main>

</body>
</html>