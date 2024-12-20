package com.example.spring_app.controllers;

import com.example.spring_app.dtos.TeamDTO;
import com.example.spring_app.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.createTeam(teamDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<TeamDTO>> getTeamsByLeague(@PathVariable Long leagueId) {
        List<TeamDTO> teams = teamService.getTeamsByLeague(leagueId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{teamId}/stats")
    public ResponseEntity<Map<String, Object>> getTeamStats(@PathVariable Long teamId) {
        Map<String, Object> stats = teamService.getTeamStats(teamId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{teamId}/performance")
    public ResponseEntity<List<Map<String, Object>>> getTeamPerformance(@PathVariable Long teamId) {
        List<Map<String, Object>> performance = teamService.getMatchPerformance(teamId);
        return ResponseEntity.ok(performance);
    }

}
