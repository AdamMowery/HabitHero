<%--
  Created by IntelliJ IDEA.
  User: adamm
  Date: 3/13/2017
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

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



<!-- About Section -->
<section class="success" id="leaderboard">
    <div class="leaderboard">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2>LEADERBOARD</h2>
                <hr class="star-light">
            </div>
        </div>
        <div class="">
            <div class="text-center">
                <p class="text-center">LEADERBOARD </p>
                <table border="1">

                    <tr>

                        <th>name</th>
                        <th>points</th>
                    </tr>
                    <c:forEach var="friend" items="${message}">
                        <tr>
                            <td>${friend.fullname}</td>
                            <td>${friend.points}</td>


                        </tr>
                    </c:forEach>

                </table>

                <br />
                <br />
            </div>

        </div>

    </div>
</section>
</body>
</html>
