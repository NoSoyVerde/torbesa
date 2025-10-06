<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/shared/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Math Challenge Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-lg">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">Solve the Math Challenge!</h2>

                        <!-- Mostrar mensaje de resultado si existe -->
                        <c:if test="${not empty message}">
                            <div class="alert ${message eq '✅ Correcto!' ? 'alert-success' : 'alert-danger'} text-center">
                                ${message}
                            </div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/math/mathgame>
                            <div class="mb-4 text-center">
                                <h4 class="text-primary">${operation}</h4>
                            </div>

                            <div class="mb-3 d-flex justify-content-center">
                                <div class="w-50 text-start">
                                    <c:forEach var="option" items="${options}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="answer"
                                                   id="option${option}" value="${option}" required>
                                            <label class="form-check-label" for="option${option}">${option}</label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Campos ocultos para enviar los números a doPost -->
                            <input type="hidden" name="a" value="${a}">
                            <input type="hidden" name="b" value="${b}">

                            <button type="submit" class="btn btn-success w-100">Submit Answer</button>
                        </form>

                        <div class="mt-4 text-center">
                            <span class="fw-bold">Your Score:</span>
                            <span class="badge bg-primary">${score}</span>
                        </div>

                        <div class="mt-3 text-center">
                            <a href="${pageContext.request.contextPath}/math/mathscores" class="btn btn-warning w-50">View High Scores</a>
                        </div>

                        <div class="mt-2 text-center">
                            <form method="get" action="${pageContext.request.contextPath}/shared/LogoutServlet">
                                <button type="submit" class="btn btn-outline-danger w-50">Logout</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
