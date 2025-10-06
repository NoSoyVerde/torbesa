package net.ausiasmarch.math.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.ausiasmarch.math.model.MathScoreDTO;
import net.ausiasmarch.math.service.MathScoreService;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/math/mathgame")
public class MathGameServlet extends HttpServlet {

    private final MathScoreService scoreService = new MathScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/shared/login.jsp");
            return;
        }

        // Generar operación matemática (suma)
        int a = (int) (Math.random() * 50) + 1;
        int b = (int) (Math.random() * 50) + 1;
        int correctAnswer = a + b;

        List<Integer> options = new ArrayList<>();
        options.add(correctAnswer);
        Random rand = new Random();

        while (options.size() < 4) {
            int option = correctAnswer + rand.nextInt(21) - 10;
            if (option != correctAnswer && !options.contains(option)) options.add(option);
        }
        Collections.shuffle(options);

        request.setAttribute("operation", a + " + " + b + " = ?");
        request.setAttribute("a", a);
        request.setAttribute("b", b);
        request.setAttribute("options", options);

        // Leaderboard parcial
        try {
            List<MathScoreDTO> highScores = scoreService.getHighScores();
            request.setAttribute("highScores", highScores);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("mathgame.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/shared/login.jsp");
            return;
        }

        try {
            int a = Integer.parseInt(request.getParameter("a"));
            int b = Integer.parseInt(request.getParameter("b"));
            int guess = Integer.parseInt(request.getParameter("answer"));
            int correctAnswer = a + b;

            boolean isCorrect = (guess == correctAnswer);

            // Guardar resultado en la base de datos
            scoreService.set(user.getId(), isCorrect);

            // Obtener el score actualizado
            MathScoreDTO userScore = null;
            try {
                userScore = scoreService.getHighScores().stream()
                        .filter(s -> s.getUserId() == user.getId())
                        .findFirst().orElse(null);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Enviar atributos al JSP
            request.setAttribute("message", isCorrect ? "✅ Correct!" : "❌ Incorrect");
            request.setAttribute("a", a);
            request.setAttribute("b", b);
            request.setAttribute("userAnswer", guess);
            request.setAttribute("correctAnswer", correctAnswer);
            request.setAttribute("userScore", userScore);

            // Leaderboard
            try {
                List<MathScoreDTO> highScores = scoreService.getHighScores();
                request.setAttribute("highScores", highScores);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("mathscores.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
        }
    }
}
