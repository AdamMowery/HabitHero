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


<nav id="mainNav" class="navbar navbar-default navbar-fixed-top navbar-custom">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand" href="#page-top">HABITZ</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li class="page-scroll">
                    <a href="addfriends">Add Friends</a>
                </li>

                <li class="page-scroll">
                    <a href="leaderboard">Leaderboard</a>
                </li>

                <li class="page-scroll">
                    <a href="habits">HABITS</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>


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

                <th>Task</th>
                <th>Complete</th>
                <th>Delete a task</th>
            </tr>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.taskId}</td>
                    <td><a href="complete?taskId=${task.taskId}"> Complete </a></td>
                    <td><a href="deleteTask?taskId=${task.taskId}"> Delete </a></td>
                </tr>
            </c:forEach>

    </div>
</section>

</body>
</html>
