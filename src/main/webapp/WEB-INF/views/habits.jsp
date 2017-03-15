<%--
  Created by IntelliJ IDEA.
  User: adamm
  Date: 3/13/2017
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <th>Delete</th>
            </tr>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td></td>
                    <td>${task.taskId}</td>

                </tr>
            </c:forEach>

        </table>
        <table border="1">

            <tr>

                <th>friend id</th>
                <th>friend name</th>
            </tr>
            <c:forEach var="friend" items="${friends}">
                <tr>
                    <td></td>
                    <td>${friend.userId}</td>


                </tr>
            </c:forEach>

        </table>
    </div>
</section>

</body>
</html>
