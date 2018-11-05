<%-- 
    Document   : user
    Created on : Oct 28, 2018, 1:37:58 PM
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
            .form-control, .list-group-item, .btn {
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
                            <a class="nav-link text-white active" href="${pageContext.request.contextPath}/User">Users</a>
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

            <div class="main-content row">

                <div class="left-content col-lg-6">
                    <h4 class="text-center mb-3">Current user list: <br>
                        <c:if test="${not empty error}">
                            <p> ${error}</p>
                        </c:if></h4>

                    <div class="scrolling-container">
                        <!--List of all Users-->
                        <table class="table">
                            <tr>
                                <th width="40%">UserName</th> 
                                <th width="30%">Permission</th>
                                <th width="30%">Action</th>
                            </tr>
                            <c:forEach var="currentUser" items="${userList}">
                                <tr>
                                    <td>
                                        <c:out value="${currentUser.userName}"/>  
                                    </td>
                                    <td>
                                        <c:forEach var="currentAuthority" items="${currentUser.permission}">
                                        <c:out value="${currentAuthority.authority}"/> 
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/displayEditUserForm?userId=${currentUser.userId}">
                                            Edit
                                        </a>
                                        |
                                        <a href="${pageContext.request.contextPath}/deleteUser?userId=${currentUser.userId}">
                                            Delete
                                        </a>  
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>

                </div>

                <div class="right-content col-lg-6">

                    <!--form to add a new user-->

                    <h4 class="text-center">Add a New User</h4>

                    <div id="add-user-form">
                        <form role="form" method="POST" action="createUser">
                            <div class="form-group">
                                <label for="userName" class="font-weight-bold">Enter a username:</label>
                                <input type="text" class="form-control" id="userName" name="userName" aria-describedby="userName" placeholder="">
                            </div>

                            <div class="form-group">
                                <label for="permission" class="font-weight-bold">Permission:</label>
                                <select class="form-control" name="permission" id="permission">
                                    <option value="ROLE_ADMIN">Admin</option>
                                    <option value="ROLE_STAFF">Employee</option>
                                    <option value="ROLE_COLLECTORS">User</option>
                                </select>
                            </div>
                            
                            <div class="form-group">
                                <label for="password" class="font-weight-bold">Enter a password:</label>
                                <input type="password" class="form-control" id="password" name="password" aria-describedby="password" placeholder="">
                            </div>

                            <button type="submit" class="btn" value="Create User">Submit</button>
                        </form>
                    </div>

                    <!--should user be able to add item to a category when it's created??-->

                </div>

            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    </body>
</html>