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
    <title>Car accident</title>
</head>
<body>
<div class="container-fluid">
    <div class="row p-4">
        <div class="col-md-6 offset-md-3">
            <div class="row p-4">
                <div class="col text-center"><h4>New accident:</h4></div>
            </div>
            <div class="row justify-content-center">
                <form action="<c:url value='/save'/>" method='POST' class="was-validated">
                    <div class="row py-2">
                        <div class="col">
                            <label for="inputName">Title</label>
                            <input type="text" name="name" id="inputName" class="form-control form-control-sm"
                                   placeholder="New accident title" required>
                        </div>
                    </div>
                    <div class="row py-2">
                        <div class="col">
                            <label for="inputAddr">Address</label>
                            <input type="text" name="address" id="inputAddr" class="form-control form-control-sm"
                                   placeholder="Address of the accident" required>
                        </div>
                    </div>
                    <div class="row py-2">
                        <div class="col">
                            <div class="form-group input-group-sm">
                                <label for="selectType">Type</label>
                                <select class="custom-select mr-sm-2" id="selectType" name="type.id" required>
                                    <c:forEach var="type" items="${types}">
                                        <option value="${type.id}">${type.name}</option>
                                    </c:forEach>
                                </select>
                                <div>
                                </div>
                            </div>
                            <div class="form-group input-group-sm">
                                <label for="selectRule">Rule</label>
                                <select class="custom-select mr-sm-2" id="selectRule" name="rIds" multiple required>
                                    <c:forEach var="rule" items="${rules}">
                                        <option value="${rule.id}">${rule.name}</option>
                                    </c:forEach>
                                </select>
                                <div>
                                </div>
                            </div>
                            <label for="inputText">Description</label>
                            <textarea class="form-control form-control-sm" rows=2 name="text" id="inputText"
                                      placeholder="Description" required></textarea>
                            <div class="d-flex p-2"></div>
                            <button class="btn btn-lg btn-outline-dark btn-block" id="submit" type="submit">Save
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
