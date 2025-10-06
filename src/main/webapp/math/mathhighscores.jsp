<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>High Scores - Math Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">

                <div class="card shadow-sm border-0">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="card-title mb-0">
                            <i class="bi bi-trophy"></i> Math Leaderboard
                        </h5>
                    </div>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover table-striped mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th scope="col"><i class="bi bi-hash"></i> Rank</th>
                                        <th scope="col"><i class="bi bi-person-fill"></i> User</th>
                                        <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> Score</th>
                                        <th scope="col" class="text-center"><i class="bi bi-target"></i> Tries</th>
                                        <th scope="col" class="text-center"><i class="bi bi-percent"></i> Accuracy</th>
                                        <th scope="col"><i class="bi bi-calendar-event"></i> Date/Time</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="score" items="${highScores}" varStatus="status">
                                        <tr class="${status.index < 3 ? (status.index == 0 ? 'table-warning' : (status.index == 1 ? 'table-info' : 'table-light')) : ''}">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${status.index == 0}">
                                                        <h5><i class="bi bi-trophy-fill text-secondary"></i> #1</h5>
                                                    </c:when>
                                                    <c:when test="${status.index == 1}">
                                                        <h5><i class="bi bi-award-fill text-secondary"></i> #2</h5>
                                                    </c:when>
                                                    <c:when test="${status.index == 2}">
                                                        <h5><i class="bi bi-award text-secondary"></i> #3</h5>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <h5>#${status.index + 1}</h5>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><strong class="h5">${score.username}</strong></td>
                                            <td class="text-center"><span class="badge bg-success">${score.score}</span></td>
                                            <td class="text-center"><span class="badge bg-info">${score.tries}</span></td>
                                            <td class="text-center">
                                                <span class="badge bg-primary">
                                                    <fmt:formatNumber value="${score.score / score.tries * 100}" maxFractionDigits="1"/>%
                                                </span>
                                            </td>
                                            <td>
                                                <small class="text-muted h5">
                                                    <fmt:formatDate value="${score.timestampAsDate}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                                </small>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <form method="get" action="MathGameServlet" class="d-inline me-3">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="bi bi-play-fill"></i> Play
                        </button>
                    </form>
                    <form method="get" action="../shared/LogoutServlet" class="d-inline">
                        <button type="submit" class="btn btn-outline-danger btn-lg">
                            <i class="bi bi-box-arrow-right"></i> Logout
                        </button>
                    </form>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
