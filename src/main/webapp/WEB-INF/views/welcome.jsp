<!DOCTYPE html>
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

    ${message}

</head>

<body id="page-top" class="index">
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

                <img align="center" style="margin-top: 5px; margin-left: 5px;" class="img-responsive" height="40em" width="40em" src="../resources/img/logoinverted.png" alt="">
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>

                    <li class="page-scroll">
                        <a href="leaderboard">LEADERBOARD</a>
                    </li>
                    <li class="page-scroll">
                        <a href="habits">HABITS</a>
                    </li>
                    <li class="page-scroll">
                        <a href="addfriends">ADD FRIENDS</a>
                    </li>
                    <li class="page-scroll">
                        <a href="challenges">CHALLENGES</a>
                    </li>

                    <li class="page-scroll">
                        <a href="about">ABOUT</a>
                    </li>


                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Header -->
    <header>
        <div class="container" id="maincontent" tabindex="-1">
            <div class="row">
                <div class="col-lg-12">
                    <img class="img-responsive" style="width:20em; height:20em;" src="../resources/img/portfolio/mascot1.png" alt="">
                    <br>
                    <div class="intro-text">
                        <h1 class="name">HABIT HERO</h1>
                        <hr class="star-light">

                        <span class="skills">Congrats! You have succesfully logged in!</span>
                        <br>
                        <br>


                        <span class="skills">Your User # is: ${message}</span>
                        <br>
                        <br>
                        <span class="skills">What do you want to do next? You can: </span>


                    </div>
                    <br>


                    <a href="habits" class="btn btn-lg btn-outline col-lg-8 col-lg-offset-2 text-center" style="color: #ffffff;  align: center;">CREATE NEW HABIT</a>
                    <br>
                    <a href="addfriends" class="btn btn-lg btn-outline col-lg-8 col-lg-offset-2 text-center" style="color: #ffffff;  align: center;">ADD FRIENDS</a>
                    <br>
                    <a href="leaderboard" class="btn btn-lg btn-outline col-lg-8 col-lg-offset-2 text-center" style="color: #ffffff;  align: center;">VIEW LEADERBOARD</a>
                    <br>
                    <a href="about" class="btn btn-lg btn-outline col-lg-8 col-lg-offset-2 text-center" style="color: #ffffff;  align: center;">READ ABOUT HABIT HERO</a>
                </div>

            </div>
        </div>
    </header>


    <!-- Footer -->
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
        </div>
    </footer>

    <!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
    <div class="scroll-top page-scroll hidden-sm hidden-xs hidden-lg hidden-md">
        <a class="btn btn-primary" href="#page-top">
            <i class="fa fa-chevron-up"></i>
        </a>
    </div>


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

</body>

</html>
