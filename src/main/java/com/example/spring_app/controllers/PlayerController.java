package com.example.spring_app.controllers;

import com.example.spring_app.dtos.PlayerDTO;
import com.example.spring_app.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{playerId}/stats")
    public ResponseEntity<Map<String, Object>> getPlayerStats(@PathVariable Long playerId) {
        Map<String, Object> stats = playerService.getPlayerStats(playerId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeamId(@PathVariable Long teamId) {
        List<PlayerDTO> players = playerService.getPlayersByTeamId(teamId);
        return ResponseEntity.ok(players);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        PlayerDTO player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO createdPlayer = playerService.createPlayer(playerDTO);
        return ResponseEntity.ok(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(
            @PathVariable Long id,
            @RequestBody PlayerDTO playerDTO) {
        playerDTO.setId(id); // Nastavíme ID hráče
        PlayerDTO updatedPlayer = playerService.updatePlayer(playerDTO);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByLeagueId(@PathVariable Long leagueId) {
        List<PlayerDTO> players = playerService.getPlayersByLeagueId(leagueId);
        return ResponseEntity.ok(players);
    }

}
