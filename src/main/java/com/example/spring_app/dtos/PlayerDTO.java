package com.example.spring_app.dtos;

import java.util.List;

public class PlayerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Long teamId;
    private List<Long> goalIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<Long> getGoalIds() {
        return goalIds;
    }

    public void setGoalIds(List<Long> goalIds) {
        this.goalIds = goalIds;
    }
}
