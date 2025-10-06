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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Math Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>

<jsp:include page="${pageContext.request.contextPath}/shared/menu.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            <h1 class="display-4">Welcome to the Math Game!</h1>
            <p class="lead">Test your math skills and climb the leaderboard!</p>
            <a href="${pageContext.request.contextPath}/math/mathgame" class="btn btn-success btn-lg m-4">Start Playing</a>
            <a href="${pageContext.request.contextPath}/math/mathscores" class="btn btn-warning btn-lg m-4">Top Scores</a>
        </div>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/shared/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
