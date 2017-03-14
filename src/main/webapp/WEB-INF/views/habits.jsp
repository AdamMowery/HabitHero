<%--
  Created by IntelliJ IDEA.
  User: adamm
  Date: 3/13/2017
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <h3>Add a new habit here.  </h3>
                <hr class="star-primary">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">

                <form name="sentMessage" id="contactForm" novalidate>
                    <div class="row control-group">
                        <div class="form-group col-xs-12 floating-label-form-group controls">
                            <label for="name">Enter new habit here. </label>
                            <input type="text" class="form-control" placeholder="Name" id="name" required data-validation-required-message="Please enter your name.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>

                    <br>
                    <div id="success"></div>
                    <div class="row">
                        <div class="form-group col-xs-12">
                            <button type="submit" class="btn btn-success btn-lg">Send</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <hr />
        <h3 class="text-center">
            YOUR CURRENT HABITS ARE
        </h3>
        <hr class="star-primary">
        <table style="width:100%">
            <tr class="text-center">
                <td>HABITS</td>
                <td>CURRENT STREAK</td>
                <td>POINTS EARNED EACH ONE</td>
            </tr>
            <hr />
            <tr class = "text-center">
                <td>Apply for one job today.</td>
                <td>3 days</td>
                <td>50</td>
            </tr>

        </table>
    </div>
</section>

</body>
</html>
