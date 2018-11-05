<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Squatch Watch</title>
        <!-- Bootstrap core CSS -->
<!--        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        -->

        <!--CDN for Bootstrap 4-->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

        <!--Google Fonts-->
        <link href="https://fonts.googleapis.com/css?family=Abhaya+Libre|Arbutus+Slab" rel="stylesheet">
        
        
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
                            <a class="nav-link text-white active" href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a>
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

            <h1 class="text-center">Squatch Watch</h1>

            <!--This is mostly a test, it provides a logout link as well as an if tag to test login privileges-->
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <h4>Hello : ${pageContext.request.userPrincipal.name}</h4>
            </c:if>


            <div>
                <p>A very small stage in a vast cosmic arena corpus callosum brain is the seed of intelligence another world permanence of the stars shores of the cosmic ocean. At the edge of forever the sky calls to us courage of our questions at the edge of forever hearts of the stars hundreds of thousands. A still more glorious dawn awaits Sea of Tranquility a mote of dust suspended in a sunbeam emerged into consciousness great turbulent clouds take root and flourish and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>
            </div>

            <div class="featured-items-container">

                <h4 class="text-center">Featured items:</h4>

                <div class="row justify-content-center">
                    <c:forEach items="${featuredItems}" var="item">
                        <a href="${pageContext.request.contextPath}/displayItemDetail?itemId=${item.itemId}">
                            <div class="featured-item-div border btn m-2">
                                <div class="feature-text">${item.itemName}</div>
                                <div class="feature-text text-truncate">${item.description}</div>
                                <img src="${pageContext.request.contextPath}/swImages/${item.filename}"
                                     class="" alt="${item.imageTitle}" width="200" height="200">
                            </div>
                        </a>
                    </c:forEach>

                </div>

            </div>

        </div>

        <!-- Placed at the end of the document so the pages load faster -->
<!--        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>-->

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    </body>
</html>

