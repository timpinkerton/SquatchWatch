<%-- 
    Document   : addItemForm
    Created on : Oct 25, 2018, 2:09:24 PM
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

                <h1 class="text-center">Add Item <br>
                    <c:if test="${not empty error}">
                        <p> ${error}</p>
                    </c:if></h1>

                <div class="form-container col-md-8 m-auto">
                        <form role="form" method="POST" action="createItem"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="nameInput" class="font-weight-bold">Name:</label>
                                <input type="text" class="form-control" id="search-input" name="nameInput" aria-describedby="nameInput" placeholder="">
                            </div>

                            <div class="form-group">
                                <label for="descriptionInput" class="font-weight-bold">Description:</label>
                                <textarea type="text" class="form-control" id="descriptionInput" name="descriptionInput" placeholder="">
                                </textarea>
                            </div>

                            <div class="form-group">
                                <label for="valueInput" class="font-weight-bold">Item Value:</label>
                                <input type="text" class="form-control" name="valueInput" id="valueInput">
                            </div>

                            <div class="form-group">
                                <label for="categoryInput" class="font-weight-bold">Category:</label>
                                <select multiple class="form-control" name="categoryInput" id="categorySearch">
                                    <c:forEach var="currentCategory" items="${categoryList}">
                                        <option value="${currentCategory.categoryId}"><c:out value="${currentCategory.categoryName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="value" class="font-weight-bold">Is this a featured item?</label>
                                <input type="radio" name="featured" value="true"> Yes
                                <input type="radio" name="featured" value="false" checked> No
                            </div>

                            <div class="form-group">
                                <label for="imageTitle" class="font-weight-bold">Display Title:</label>
                                <input type="text" 
                                       id="imageTitle" 
                                       name="imageTitle" class="form-control"/>
                            </div>

                            <div class="form-group text-center mt-2">
                                <label for="itemImage" class="font-weight-bold">Upload Image:</label>
                                <input type="file"
                                       id="itemImage" name="itemImage"/>
                            </div>

                            <div class="text-center mt-3">
                                <button type="submit" class="btn" value="Create Item">Submit</button>
                            </div>

                        </form>

                </div>

            </div>
        </div>
                        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
                      
    </body>
</html>
