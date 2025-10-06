package net.ausiasmarch.math.service;

import net.ausiasmarch.math.dao.MathScoreDAO;
import net.ausiasmarch.math.model.MathScoreDTO;
import net.ausiasmarch.shared.connection.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MathScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            MathScoreDAO scoreDAO = new MathScoreDAO(oConnection);
            if (scoreDAO.count(userId) > 1) scoreDAO.sanitize();

            MathScoreDTO userScore = scoreDAO.get(userId);
            if (!Objects.isNull(userScore)) {
                userScore.setTries(userScore.getTries() + 1);
                if (correct) userScore.setScore(userScore.getScore() + 1);
                return scoreDAO.update(userScore) > 0;
            } else {
                userScore = new MathScoreDTO();
                userScore.setUserId(userId);
                userScore.setTries(1);
                userScore.setScore(correct ? 1 : 0);
                userScore.setTimestamp(LocalDateTime.now());
                return scoreDAO.insert(userScore) > 0;
            }
        }
    }

    public List<MathScoreDTO> getHighScores() throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            MathScoreDAO scoreDAO = new MathScoreDAO(oConnection);
            return scoreDAO.getTop10();
        }
    }
}
