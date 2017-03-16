<%--
  Created by IntelliJ IDEA.
  User: adamm
  Date: 3/14/2017
  Time: 11:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

${message}


<!-- Navigation Bar-->
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

<!-- ADD FRIENDS SECTION-->
<section id="addfriends">
    <div class="container row col-lg-12 text-center">
        <h2>FIND FRIENDS</h2>
        <hr class="star-primary">
    </div>
</section>


<form  action="addFriendButton" method="post">

    Add a friend<input type="text" name="userid">
    <input type="submit" value="add">


</form>
</table>
<table border="1">

    <tr>

        <th>friend name</th>
        <th>friend points</th>
    </tr>
    <c:forEach var="friend" items="${friends}">
        <tr>
            <td>${friend.fullname}</td>
            <td>${friend.points}</td>


        </tr>
    </c:forEach>

</table>




<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>
