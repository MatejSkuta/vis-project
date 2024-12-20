package com.example.spring_app.controllers;

import com.example.spring_app.dtos.LeagueDTO;
import com.example.spring_app.services.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getAllLeagues() {
        return ResponseEntity.ok(leagueService.getAllLeagues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable Long id) {
        return ResponseEntity.ok(leagueService.getLeagueById(id));
    }

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody LeagueDTO leagueDTO) {
        return ResponseEntity.ok(leagueService.createLeague(leagueDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> updateLeague(@PathVariable Long id, @RequestBody LeagueDTO leagueDTO) {
        return ResponseEntity.ok(leagueService.updateLeague(id, leagueDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.noContent().build();
    }
}
