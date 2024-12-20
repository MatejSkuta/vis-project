package com.example.spring_app.dtos;

public class TeamDTO {

    private Long id;
    private String name;
    private Integer foundedYear;
    private String stadiumName;
    private Long leagueId;

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

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
    public void setLeagueId(Long leagueId){
        this.leagueId=leagueId;
    }

    public Long getLeagueId() {
        return leagueId;
    }
}
