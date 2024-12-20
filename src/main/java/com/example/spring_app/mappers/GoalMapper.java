package com.example.spring_app.mappers;

import com.example.spring_app.dtos.GoalDTO;
import com.example.spring_app.entities.Goal;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    public GoalDTO toDTO(Goal goal) {
        GoalDTO dto = new GoalDTO();
        dto.setId(goal.getId());
        dto.setPlayerId(goal.getPlayerId());
        dto.setMatchId(goal.getMatchId());
        dto.setGoalMinute(goal.getGoalMinute());
        return dto;
    }

    public Goal toEntity(GoalDTO dto) {
        Goal goal = new Goal();
        goal.setId(dto.getId());
        goal.setPlayerId(dto.getPlayerId());
        goal.setMatchId(dto.getMatchId());
        goal.setGoalMinute(dto.getGoalMinute());
        return goal;
    }
}
