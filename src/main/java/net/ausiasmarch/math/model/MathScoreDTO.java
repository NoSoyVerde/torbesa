package net.ausiasmarch.math.model;

import java.time.LocalDateTime;
import java.util.Date;

public class MathScoreDTO {

    private int id;
    private int userId;
    private String username; // NUEVO
    private int score;
    private int tries;
    private LocalDateTime timestamp;

    public MathScoreDTO() {
        this.id = 0;
        this.userId = 0;
        this.username = "";
        this.score = 0;
        this.tries = 0;
        this.timestamp = null;
    }

    public MathScoreDTO(int id, int userId, String username, int score, int tries, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.score = score;
        this.tries = tries;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getScore() { return score; }
    public int getTries() { return tries; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setScore(int score) { this.score = score; }
    public void setTries(int tries) { this.tries = tries; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    // Getter auxiliar para JSP
    public Date getTimestampAsDate() {
        return timestamp != null ? java.sql.Timestamp.valueOf(timestamp) : null;
    }
}
