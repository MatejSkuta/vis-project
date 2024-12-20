package com.example.spring_app.controllers;

import com.example.spring_app.dtos.MatchDTO;
import com.example.spring_app.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByLeagueId(@PathVariable Long leagueId) {
        List<MatchDTO> matches = matchService.getMatchesByLeagueId(leagueId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        MatchDTO match = matchService.getMatchById(id);
        return ResponseEntity.ok(match);
    }

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody MatchDTO matchDTO) {
        try {
            MatchDTO createdMatch = matchService.createMatch(matchDTO);
            return ResponseEntity.ok(createdMatch);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
        MatchDTO updatedMatch = matchService.updateMatch(id, matchDTO);
        return ResponseEntity.ok(updatedMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable Long teamId) {
        List<MatchDTO> matches = matchService.getMatchesByTeam(teamId);
        return ResponseEntity.ok(matches);
    }
}
