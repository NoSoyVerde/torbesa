package net.ausiasmarch.math.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import net.ausiasmarch.math.model.MathScoreDTO;
import net.ausiasmarch.math.service.MathScoreService;

@WebServlet("/math/mathscores")
public class MathScoreServlet extends HttpServlet {

    private final MathScoreService scoreService;

    public MathScoreServlet() {
        this.scoreService = new MathScoreService();
    }

    // Constructor para inyección de tests
    public MathScoreServlet(MathScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MathScoreDTO> highScoresList = scoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("mathhighscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            forwardError(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Internal error: " + e.getMessage());
            request.setAttribute("errorMessage", "Internal error");
            forwardError(request, response);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            forwardError(request, response);
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e1) {
            System.err.println("Error forwarding to error page: " + e1.getMessage());
        }
    }
}
