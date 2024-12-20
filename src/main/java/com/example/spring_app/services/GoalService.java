package com.example.spring_app.services;

import com.example.spring_app.dtos.GoalDTO;
import com.example.spring_app.entities.Goal;
import com.example.spring_app.mappers.GoalMapper;
import com.example.spring_app.tabledatagateways.GoalTableDataGateway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private final GoalTableDataGateway goalGateway;
    private final GoalMapper goalMapper;

    public GoalService(GoalTableDataGateway goalGateway, GoalMapper goalMapper) {
        this.goalGateway = goalGateway;
        this.goalMapper = goalMapper;
    }


    public List<GoalDTO> getAllGoals() {
        List<Goal> goals = goalGateway.getAllGoals();
        return goals.stream().map(goalMapper::toDTO).toList();
    }

    public GoalDTO getGoalById(Long id) {
        Goal goal = goalGateway.getGoalById(id);
        return goalMapper.toDTO(goal);
    }


    public GoalDTO createGoal(GoalDTO goalDTO) {
        Goal goal = goalMapper.toEntity(goalDTO);
        goalGateway.createGoal(goal);
        return goalDTO;
    }


    public GoalDTO updateGoal(Long id, GoalDTO goalDTO) {
        Goal goal = goalMapper.toEntity(goalDTO);
        goalGateway.updateGoal(id, goal);
        return goalDTO;
    }


    public void deleteGoalById(Long goalId) {
        Goal goal = goalGateway.getGoalById(goalId);
        if (goal == null) {
            throw new IllegalArgumentException("Goal not found with ID: " + goalId);
        }

        goalGateway.deleteGoalById(goalId);
        goalGateway.updateMatchScore(goal.getMatchId());
    }

    public List<GoalDTO> getGoalsByMatchId(Long matchId) {
        return goalGateway.getGoalsByMatchIdWithDetails(matchId);
    }

    public void addGoal(GoalDTO goalDTO) {
        Goal goal = goalMapper.toEntity(goalDTO);
        goalGateway.addGoal(goal);
    }

    public void updateMatchScore(Long matchId) {
        goalGateway.updateMatchScore(matchId);
    }


}
