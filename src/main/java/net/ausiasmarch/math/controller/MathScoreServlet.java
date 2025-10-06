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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MathScoreDTO> highScoresList = scoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("mathhighscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database or internal error");
            try {
                request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
