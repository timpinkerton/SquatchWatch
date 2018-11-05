<%-- 
    Document   : category
    Created on : Oct 25, 2018, 3:30:02 PM
    Author     : timpinkerton
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                            <a class="nav-link text-white active" href="${pageContext.request.contextPath}/Category">Category</a>
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

            <div class="main-content row">

                <div class="left-content col-lg-6">
                    <h4 class="text-center mb-3">Categories:</h4>

                    <c:if test="${not empty error}">
                        <p> ${error}</p>
                    </c:if>
                    <div class="scrolling-container">
                        <!--List of all Categories-->
                        <ul class="list-group">
                            <c:forEach var="currentCategory" items="${categoryList}">
                                <li class="list-group-item m-1">
                                    <div class="d-flex">
                                        <div class="mr-auto">
                                            <c:out value="${currentCategory.categoryName}"/>
                                        </div>
                                        
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <div class="mr-2">
                                                <a href="${pageContext.request.contextPath}/displayCategoryEditForm?categoryId=${currentCategory.categoryId}">
                                                    Edit
                                                </a>
                                            </div>

                                            <div class="mr-2">
                                                <a href="${pageContext.request.contextPath}/deleteCategory?categoryId=${currentCategory.categoryId}">
                                                    Delete
                                                </a> 
                                            </div>
                                        </sec:authorize>
                                                    
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>

                <div class="right-content col-lg-6">

                    <!--form to add a new category-->

                    <h4 class="text-center">Add a New Category</h4>

                    <div id="add-category-form">
                        <form role="form" method="POST" action="createCategory">
                            <div class="form-group">
                                <label for="categoryName" class="font-weight-bold">Enter a name for this new category:</label>
                                <input type="text" class="form-control" id="categoryName" name="categoryName" aria-describedby="categoryName" placeholder="" required>
                            </div>

                            <button type="submit" class="btn" value="Create Category">Submit</button>
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