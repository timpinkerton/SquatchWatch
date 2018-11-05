<%-- 
    Document   : itemDetail
    Created on : Oct 24, 2018, 1:20:30 PM
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
                            <a class="nav-link text-white active" href="${pageContext.request.contextPath}/Item">Inventory</a>
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

            <div>
                <h1 class="text-center"><c:out value="${item.itemName}"/></h1>
                <h4 class="text-center">Item Value: $<c:out value="${item.itemValue}"/></h4>
            </div>

            <div class="category-container">
                <div class="row justify-content-center">

                    <div>
                        <p>Categories: <br>
                            <c:if test="${not empty error}">
                            <p> ${error}</p>
                        </c:if></p>
                    </div>

                    <div>
                        <ul>
                            <!--will have a forEach to make each Category an <li>--> 

                            <!--need method for get category by itemId-->

                            <c:forEach var="currentCategory" items="${categories}">
                                <li><c:out value="${currentCategory.categoryName}"/></li>
                                </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>

            <div class="item-description-container">



                <div class="row justify-content-center m-3">


                    <div class="col-md-4">
                        <h4>Description:</h4>
                        <p><c:out value="${item.description}"/></p>
                    </div>

                    <!--http://localhost:8080/SquatchWatch/-->

                    <!--${pageContext.request.contextPath}-->

                    <!--../../../../SquatchWatch/ItemImages-->

                    <div class="col-md-4 text-center">
                        <img src="${pageContext.request.contextPath}/swImages/${item.filename}"
                             class="" alt="${item.imageTitle}" width="300" height="300">
                    </div>
                </div>

            </div>
            <div class="text-center">
                <sec:authorize access="hasRole('ROLE_STAFF')">

                    <a href="${pageContext.request.contextPath}/displayEditItemForm?itemId=${item.itemId}">
                        <button class="btn">Edit Item</button>
                    </a>
                    <!--</div>-->
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <!--<div class="text-center">-->
                    <a href="${pageContext.request.contextPath}/deleteItem?itemId=${item.itemId}">
                        <button class="btn">Delete Item</button>
                    </a>

                </sec:authorize>
            </div>  
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</body>
</html>

