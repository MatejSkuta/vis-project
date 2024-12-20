package com.example.spring_app.controllers;

import com.example.spring_app.dtos.GoalDTO;
import com.example.spring_app.services.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDTO> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    /*@PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.createGoal(goalDTO));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable Long id, @RequestBody GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.updateGoal(id, goalDTO));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<String> deleteGoalById(@PathVariable Long goalId) {
        try {
            goalService.deleteGoalById(goalId);
            return ResponseEntity.ok("Goal deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete goal.");
        }
    }
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<GoalDTO>> getGoalsByMatchId(@PathVariable Long matchId) {
        List<GoalDTO> goals = goalService.getGoalsByMatchId(matchId);
        return ResponseEntity.ok(goals);
    }

    @PostMapping
    public ResponseEntity<Void> addGoal(@RequestBody GoalDTO goalDTO) {
        goalService.addGoal(goalDTO);
        goalService.updateMatchScore(goalDTO.getMatchId());
        return ResponseEntity.ok().build();
    }
}
