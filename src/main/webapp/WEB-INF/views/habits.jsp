<%--
  Created by IntelliJ IDEA.
  User: adamm
  Date: 3/13/2017
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Habitz</title>

</head>
<body>
${message}




<section id="newhabit">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2>Create new Habit</h2>
                <h3>Add a new habit here. </h3>
                <hr class="star-primary">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">

                <form action="addTask" method="post">

                    Enter a new task<input type="text" name="task">
                    <input type="submit" value="Add">
                </form>

            </div>
        </div>
        <hr/>
        <h3 class="text-center">
            YOUR CURRENT HABITS ARE
        </h3>
        <table border="1">

            <tr>

                <th>task</th>
                <th>complete</th>
            </tr>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.taskId}</td>
                    <td><a href="complete?taskId=${task.taskId}"> Complete </a></td>

                </tr>
            </c:forEach>

    </div>
</section>

</body>
</html>
