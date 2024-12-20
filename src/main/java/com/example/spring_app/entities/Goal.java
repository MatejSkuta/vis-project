package com.example.spring_app.entities;

public class Goal {

    private Long id;
    private Long playerId;
    private Long matchId;
    private Integer goalMinute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getGoalMinute() {
        return goalMinute;
    }

    public void setGoalMinute(Integer goalMinute) {
        this.goalMinute = goalMinute;
    }
}
