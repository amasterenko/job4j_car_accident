<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
          crossorigin="anonymous">
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"
            integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Car accident</title>
</head>
<body>
<div class="container-fluid">
    <div class="row p-4 text-center">
        <div class="col text-center"><h4>Accidents:</h4></div>
    </div>
    <div class="row p-4">
        <div class="col-md-6 offset-md-3">
            <ul class="nav float-left">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value='/create'/>">Create accident</a>
                </li>
            </ul>
            <ul class="nav float-right">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value='/logout'/>">${pageContext.request.userPrincipal.name} | logout</a>
                    </li>
                </c:if>
            </ul>

            <table class="table table-sm top-buffer text-center">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Rule</th>
                    <th>Text</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${accidents}" var="accident">
                <tr>
                    <td>${accident.getId()}</td>
                    <td>${accident.getName()}</td>
                    <td>${accident.getAddress()}</td>
                    <td>${accident.getType().getName()}</td>
                    <td>
                    <div class="btn-group">
                        <a class="btn btn-link dropdown-toggle align-middle" href="" role="button"
                           id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <c:forEach items="${accident.getRules()}" var="rule">
                            <a class="dropdown-item" href="">${rule.getName()}</a>
                            </c:forEach>
                        </div>
                    </div>
                    </td>
                    <td>${accident.getText()}</td>
                    <td><a href='<c:url value="/update?id=${accident.id}"/>' title="Edit">
                        <i class="fa fa-edit mr-3"></i>
                    </a></td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
