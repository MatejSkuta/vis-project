package com.example.spring_app.entities;

import java.util.List;

public class League {

    private Long id;
    private String name;
    private Integer competitionYear;
    private String region;
    private List<Long> teamIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompetitionYear() {
        return competitionYear;
    }

    public void setCompetitionYear(Integer competitionYear) {
        this.competitionYear = competitionYear;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Long> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Long> teamIds) {
        this.teamIds = teamIds;
    }
}
