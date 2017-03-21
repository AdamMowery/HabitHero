<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Habit Hero Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Theme CSS -->
    <link href="../resources/css/freelancer.min.css" rel="stylesheet">


    <!-- Custom Fonts -->
    <link href="../resources/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body id="page-top" class="index text-center">



<div id="skipnav"><a href="#maincontent">Skip to main content</a></div>

<!-- Navigation -->
<nav id="mainNav" class="navbar navbar-default text-center navbar-fixed-top navbar-custom">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand" href="#page-top">HABIT HERO</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li class="page-scroll">
                    <a href="habits">HABITS</a>
                </li>

                <li class="page-scroll">
                    <a href="leaderboard">LEADERBOARD</a>
                </li>

                <li class="page-scroll">
                    <a href="about">ABOUT</a>
                </li>

                <li class="page-scroll">
                    <a href="addfriends">ADD FRIENDS</a>
                </li>


            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>


<!-- Header -->
<header style="background-color: #2B3B50" class="text-center">

    <section style="background-color: #50A1FF; " class="success text-center" id="newhabit">



        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3>${message}</h3>
                    <h2>Create new Habit</h2>
                    <h3>Add a new habit here. </h3>
                    <hr class="star-light" style = "color: #ffffff" >
                </div>
            </div>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">

                    <form action="addTask" style = "color: #2B3B50" method="post">

                        Enter a new task<input type="text" style = "color: #2B3B50" name="task">
                        <input type="submit" style = "color: #2B3B50" value="Add">
                    </form>

                </div>
            </div>
            <h3 class="text-center">
                YOUR CURRENT HABITS ARE
            </h3>
            <table border="2" style = "color: #FFFFFF; text-color: #FFFFFF; padding-left: 5em; margin-left: 15em;" class="success text-center">

                <tr style = "color: #FFFFFF;" class = "success text-center">

                    <th style = "color: #FFFFFF;" class = "success text-center">Task</th>
                    <th style = "color: #FFFFFF;" class = "success text-center">Complete</th>
                    <th style = "color: #FFFFFF;" class = "success text-center">Delete a task</th>
                </tr>
                <c:forEach var="task" items="${tasks}">
                <tr style = "color: #FFFFFF;" class = "success text-center">
                    <td style = "color: #FFFFFF;" class = "success text-center">${task.taskId}</td>
                    <td style = "color: #FFFFFF;" class = "success text-center"><a href="complete?taskId=${task.taskId}" style = "color: #2B3B50; text-decoration: underline;" > Complete </a></td>
                    <td style = "color: #FFFFFF;" class = "success text-center"><a href="deleteTask?taskId=${task.taskId}" style = "color: #2B3B50; text-decoration: underline;"> Delete </a></td>
                </tr>
                </c:forEach>
            </table>

        </div>

    </section>
</header>

</body>

<!-- Footer -->
<!-- COMMENTED OUT THIS THING BECAUSE IT WASN"T SHOWING UP IN THE RIGHT SPOT
//TODO PUT THIS BACK AT THE BOTTOM OF THIS PAGE
<footer class="text-center">
    <div class="footer-above">
        <div class="container">
            <div class="row">
                <div class="footer-col col-md-4">
                    <h3>Location</h3>
                    <p>
                        <br>Detroit, Michigan</p>
                </div>
                <div class="footer-col col-md-4">
                    <h3>Around the Web</h3>
                    <ul class="list-inline">
                        <li>
                            <a href="#" class="btn-social btn-outline"><span class="sr-only">Facebook</span><i class="fa fa-fw fa-facebook"></i></a>
                        </li>
                        <li>
                            <a href="#" class="btn-social btn-outline"><span class="sr-only">Google Plus</span><i class="fa fa-fw fa-google-plus"></i></a>
                        </li>
                        <li>
                            <a href="#" class="btn-social btn-outline"><span class="sr-only">Twitter</span><i class="fa fa-fw fa-twitter"></i></a>
                        </li>
                        <li>
                            <a href="#" class="btn-social btn-outline"><span class="sr-only">Linked In</span><i class="fa fa-fw fa-linkedin"></i></a>
                        </li>
                        <li>
                            <a href="#" class="btn-social btn-outline"><span class="sr-only">Dribble</span><i class="fa fa-fw fa-dribbble"></i></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer-below">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        Copyright &copy; Your Website 2016
                    </div>
                </div>
            </div>
        </div>
</footer>
-->


<!-- jQuery -->
<script src="../resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

<!-- Contact Form JavaScript -->
<script src="../resources/js/jqBootstrapValidation.js"></script>
<script src="../resources/js/contact_me.js"></script>

<!-- Theme JavaScript -->
<script src="../resources/js/freelancer.min.js"></script>



</html>
