package com.example.spring_app.controllers;

import com.example.spring_app.dtos.SquadDTO;
import com.example.spring_app.services.SquadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/squads")
public class SquadController {

    private final SquadService squadService;

    public SquadController(SquadService squadService) {
        this.squadService = squadService;
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<SquadDTO>> getSquadByMatchId(@PathVariable Long matchId) {
        List<SquadDTO> squad = squadService.getSquadByMatchId(matchId);
        return ResponseEntity.ok(squad);
    }

    @PostMapping
    public ResponseEntity<String> addPlayerToSquad(@RequestBody SquadDTO squadDTO) {
        int currentSquadSize = squadService.getSquadSize(squadDTO.getMatchId(), squadDTO.getTeamId());

        if (currentSquadSize >= SquadService.MAX_PLAYERS) {
            return ResponseEntity.badRequest().body("Cannot add more players. Maximum squad size is " + SquadService.MAX_PLAYERS + ".");
        }

        squadService.addPlayerToSquad(squadDTO);
        return ResponseEntity.ok("Player added successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> removePlayerFromSquad(@RequestBody SquadDTO squadDTO) {
        int currentSquadSize = squadService.getSquadSize(squadDTO.getMatchId(), squadDTO.getTeamId());

        if (currentSquadSize <= SquadService.MIN_PLAYERS) {
            return ResponseEntity.badRequest().body("Cannot remove player. Minimum squad size is " + SquadService.MIN_PLAYERS + ".");
        }

        squadService.removePlayerFromSquad(squadDTO);
        return ResponseEntity.ok("Player removed successfully.");
    }

    @GetMapping("/match/{matchId}/team/{teamId}")
    public ResponseEntity<List<SquadDTO>> getSquadByMatchAndTeam(
            @PathVariable Long matchId,
            @PathVariable Long teamId
    ) {
        List<SquadDTO> squad = squadService.getSquadByMatchAndTeam(matchId, teamId);
        return ResponseEntity.ok(squad);
    }
}
