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
