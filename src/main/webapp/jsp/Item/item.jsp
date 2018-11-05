<%-- 
    Document   : item
    Created on : Oct 24, 2018, 2:43:04 PM
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
                <h1 class="text-center">Sasquatch Supplies</h1>
            </div>

            <div class="main-content row">

                <div class="left-content col-lg-6" >
                    <h4 class="text-center mb-3">Items <br>
                        <c:if test="${not empty error}">
                            <p> ${error}</p>
                        </c:if></h4>

                    <div class="scrolling-container">
                        <!--List of all inventory items-->
                        <ul class="list-group" id="item-list">
                            <c:forEach var="currentItem" items="${itemList}">
                                <a href="${pageContext.request.contextPath}/displayItemDetail?itemId=${currentItem.itemId}">
                                    <li class="list-group-item m-1">
                                        <c:out value="${currentItem.itemName}"/>
                                    </li>
                                </a>
                            </c:forEach>
                        </ul>
                    </div>


                </div>

                <div class="right-content  col-lg-6">
                    <h4 class="text-center">Search</h4>

                    <form action="searchItem" method="POST" role="form">

                        <div class="form-group">
                            <label for="valueSearch" class="font-weight-bold">Item Value:</label>
                            <select class="form-control" id="valueSearch" name="value" >
                                <option value="0,9999">0 - 9999</option>
                                <option value="10000,19999">10000 - 19999</option>
                                <option value="20000,29999">20000 - 29999</option>
                                <option value="30000,39999">30000 - 39999</option>
                                <option value="40000,49999">40000 - 49999</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="searchItem" class="font-weight-bold">Search By Name:</label>
                            <input type="text" class="form-control" id="search-input" name="name" aria-describedby="searchItem" placeholder="">
                        </div>

                        <div class="form-group">
                            <label for="searchDescription" class="font-weight-bold">Search By Description:</label>
                            <input type="text" class="form-control" id="searchDescription" name="description" placeholder="">
                        </div>

                        <div class="form-group">
                            <label for="categorySearch" class="font-weight-bold">Category:</label>
                            <select class="form-control" id="categorySearch" name="category" >
                                <option value="" disabled selected>Choose Category</option>
                                <c:forEach var="currentCat" items="${categoryList}">
                                    <option>
                                        <c:out value="${currentCat.categoryName}"/>
                                    </option>
                                </c:forEach> 
                            </select>
                        </div>

                        <div class="text-center">
                            <a href="${pageContext.request.contextPath}/Item" class="btn" id="reset-button">Reset</a>
                            <button type="submit" class="btn" id="search-button">Search</button>
                        </div>
                    </form>

                    <sec:authorize access="hasRole('ROLE_STAFF')">
                        <div class="text-center mt-4">
                            <a href="${pageContext.request.contextPath}/displayAddItemForm">
                                <button type="" class="btn">Add New Item</button>
                            </a>
                        </div>
                    </sec:authorize>

                </div>

            </div>

        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/SearchValues.js"></script>
    </body>
</html>