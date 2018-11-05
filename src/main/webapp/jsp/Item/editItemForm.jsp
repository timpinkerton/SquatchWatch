<%-- 
    Document   : editItem
    Created on : Oct 25, 2018, 1:05:36 PM
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
            .form-control, .list-group-item, .btn, #itemImage {
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
                <h1 class="text-center"Edit Item</h1>
            </div>

            <div class="main-content">

                <h1 class="text-center">Edit Item <br>
                    <c:if test="${not empty error}">
                        <p> ${error}</p>
                    </c:if></h1>

                <form role="form" method="POST" action="editItem">
                    <div class="main-content row">
                        <div class="left-content col-lg-6">
                            <div class="form-group">
                                <label for="itemName">Name:</label>
                                <input type="text" class="form-control" id="itemName" name="itemName" aria-describedby="itemName" placeholder="" value="${item.itemName}">
                            </div>

                            <div class="form-group">
                                <label for="description">Description:</label>
                                <input type="text" class="form-control" id="description" name="description" aria-describedby="description" placeholder="" value="${item.description}">
                            </div>

                            <div class="form-group">
                                <label for="value">Item Value:</label>
                                <input class="form-control" id="value" name="value" value="${item.itemValue}">
                            </div>

                            <div class="form-group">
                                <p>${item.itemName} is currently in these categories:</p>
                                <ul>
                                    <c:forEach var="currentCategory" items="${categories}">
                                        <li><c:out value="${currentCategory.categoryName}"/></li>
                                        </c:forEach>
                                </ul>
                            </div>

                            <div class="form-group">
                                <label for="categories">If you wish to change categories, select them below:</label>
                                <select multiple class="form-control" name="categories" id="categories">
                                    <c:forEach var="currentCategory" items="${categoryList}">
                                        <option value="${currentCategory.categoryId}"><c:out value="${currentCategory.categoryName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="value">Is this a featured item?</label>
                                <input type="radio" name="featured" value="true"> Yes
                                <input type="radio" name="featured" value="false" checked> No
                            </div>
                        </div>


                        <div class="right-content  col-lg-6">

                            <div>
                                <img src="${pageContext.request.contextPath}/swImages/${item.filename}" alt="No Image Available"
                                     width="400" height="400">
                            </div>

                            <div class="form-group text-center mt-2">
                                <label for="imageInput">Browse</label>
                                <input type="file"
                                       id="itemImage" name="itemImage"
                                       accept="image/png, image/jpeg"/>
                            </div>

                        </div>
                    </div>
                    <input type="hidden" id="itemId" name="itemId" value="${item.itemId}">
                    <div class="text-center mt-3">
                        <button type="submit" class="btn">Submit</button>
                    </div>

                </form>

            </div>
        </div>
    </body>
</html>
