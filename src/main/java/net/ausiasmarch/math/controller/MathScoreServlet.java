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

    private final MathScoreService scoreService = new MathScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("sessionUser") == null) {
            try { response.sendRedirect(request.getContextPath() + "/index.jsp"); } catch (IOException e) { e.printStackTrace(); }
            return;
        }

        try {
            List<MathScoreDTO> highScores = scoreService.getHighScores();
            request.setAttribute("highScores", highScores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("mathhighscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
            try { request.setAttribute("errorMessage", e.getMessage()); request.getRequestDispatcher("../shared/error.jsp").forward(request, response); } 
            catch (ServletException | IOException ex) { ex.printStackTrace(); }
        }
    }
}
