package net.ausiasmarch.math.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.math.model.MathScoreDTO;

public class MathScoreDAO {

    private final Connection oConnection;

    public MathScoreDAO(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public MathScoreDTO get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }

        MathScoreDTO score = null;
        String sql = "SELECT * FROM math_score WHERE user_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    score = new MathScoreDTO(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("score"),
                            rs.getInt("tries"),
                            rs.getTimestamp("timestamp").toLocalDateTime()
                    );
                }
            }
        }
        return score;
    }

    public int count(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM math_score WHERE user_id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public void sanitize() throws SQLException {
        String sql = "DELETE s1 FROM math_score s1 " +
                     "INNER JOIN math_score s2 ON s1.user_id = s2.user_id " +
                     "WHERE s1.timestamp < s2.timestamp";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            int deletedRows = stmt.executeUpdate();
            System.out.println("Sanitized " + deletedRows + " duplicate math scores");
        }
    }

    public List<MathScoreDTO> getTop10() throws SQLException {
        List<MathScoreDTO> scores = new ArrayList<>();
        String sql = "SELECT * FROM math_score ORDER BY score DESC, timestamp DESC LIMIT 10";
        try (Statement stmt = oConnection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                scores.add(new MathScoreDTO(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        }
        return scores;
    }

    public int insert(MathScoreDTO score) throws SQLException {
        String sql = "INSERT INTO math_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getScore());
            stmt.setInt(3, score.getTries());
            return stmt.executeUpdate();
        }
    }

    public int update(MathScoreDTO score) throws SQLException {
        String sql = "UPDATE math_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, score.getScore());
            stmt.setInt(2, score.getTries());
            stmt.setInt(3, score.getUserId());
            return stmt.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM math_score WHERE id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}
