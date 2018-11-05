<%-- 
    Document   : editUserForm
    Created on : Oct 28, 2018, 2:47:14 PM
    Author     : timpinkerton
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Squatch Watch</title>

        <!--CDN for Bootstrap 4-->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

        <!--custom CSS-->
        <link rel="stylesheet" href="css/customStyles.css">
        <style>
            .form-group, .form-control, .list-group-item, .btn {
                background-color: #f4e5d0;
                opacity: 0.7;
            }
        </style>
    </head>
    <body>
        <div class="container">

            <nav class="navbar navbar-expand-lg navbar-light mb-2">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/Article">Articles</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/Item">Inventory</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/Category">Category</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/User">Users</a>
                        </li>
                        <li class="nav-item">
                            <c:if test="${pageContext.request.userPrincipal.name == null}">
                                <a class="nav-link text-white" href="${pageContext.request.contextPath}/Login">Log In</a>
                            </c:if>
                        </li>
                        <li class="nav-item">
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <a class="nav-link text-white" href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                            </c:if>
                        </li>

                    </ul>
                </div>
            </nav>

            <div class="main-content">

                <!--form to edit a user-->

                <h4 class="text-center">Edit User <br>
                    <c:if test="${not empty error}">
                        <p> ${error}</p>
                    </c:if></h4>

                <div id="add-user-form">
                    <form role="form" method="POST" action="editUser">
                        <div class="form-group">
                            <label for="userName">Enter a name for this user:</label>
                            <input type="text" class="form-control" id="userName" name="userName" aria-describedby="userName" value="${user.userName}">
                        </div>

                        <div class="form-group">
                            <label for="permission">Permission:</label>
                            <select class="form-control" name="permission" id="permission" placeholder="current permission">

                                <!--
                                <c:if test="${user.permission.size() > 1 }">
                                    <c:out value="${user.permission.authorites[1]}"></c:out>
                                </c:if>

                                <c:if test="${user.permission.size() < 4}">
                                    <option value="" selected disabled hidden>${user.permission}</option>
                                </c:if>
                                -->

                                <option value="" selected disabled hidden>${user.permission}</option>
                                <option value="ROLE_ADMIN">Admin</option>    
                                <option value="ROLE_STAFF">Staff</option>
                                <option value="ROLE_COLLECTOR">Collector</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="password" class="font-weight-bold">Enter a password:</label>
                            <input type="password" class="form-control" id="password" name="password" aria-describedby="password" placeholder="">
                        </div>

                        <input type="hidden" id="userId" name="userId" value="${user.userId}">
                        <button type="submit" class="btn" value="Edit User">Submit</button>
                    </form>
                </div>

            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    </body>
</html>