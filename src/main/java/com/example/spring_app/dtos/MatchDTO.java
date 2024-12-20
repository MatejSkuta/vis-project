package com.example.spring_app.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class MatchDTO {

    private Long id;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long leagueId;
    private LocalDateTime matchDate;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Long refereeId;
    private List<Long> goalIds;
    private String homeTeamName;
    private String awayTeamName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Long getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Long refereeId) {
        this.refereeId = refereeId;
    }

    public List<Long> getGoalIds() {
        return goalIds;
    }

    public void setGoalIds(List<Long> goalIds) {
        this.goalIds = goalIds;
    }

    public void setHomeTeamName(String homeTeamName){
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamName(){
        return homeTeamName;
    }

    public void setAwayTeamName(String awayTeamName){
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamName(){
        return awayTeamName;
    }
}
