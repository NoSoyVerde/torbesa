<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="${pageContext.request.contextPath}/shared/menu.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 text-center">
            <h1 class="display-4 mb-4">Math Challenge</h1>
            <p class="lead mb-4">Solve the problem and see your score on the leaderboard!</p>

            <!-- Formulario del juego -->
            <form method="post" action="${pageContext.request.contextPath}/math/mathgame">
                <div class="mb-3">
                    <label for="question" class="form-label h5">Question:</label>
                    <input type="text" id="question" name="question" class="form-control text-center" value="${question}" readonly>
                </div>
                <div class="mb-3">
                    <label for="userAnswer" class="form-label h5">Your Answer:</label>
                    <input type="number" id="userAnswer" name="userAnswer" class="form-control text-center" required>
                </div>
                <button type="submit" class="btn btn-success btn-lg">
                    <i class="bi bi-play-fill"></i> Submit Answer
                </button>
            </form>

            <!-- Botones de acción -->
            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/math/mathscores" class="btn btn-warning me-2">
                    <i class="bi bi-trophy-fill"></i> View Leaderboard
                </a>
                <a href="${pageContext.request.contextPath}/shared/LogoutServlet" class="btn btn-outline-danger">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/shared/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
