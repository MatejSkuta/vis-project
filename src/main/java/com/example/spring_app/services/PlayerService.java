package com.example.spring_app.services;

import com.example.spring_app.dtos.PlayerDTO;
import com.example.spring_app.entities.Player;
import com.example.spring_app.mappers.PlayerMapper;
import com.example.spring_app.tabledatagateways.PlayerTableDataGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    private final PlayerTableDataGateway playerGateway;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerTableDataGateway playerGateway, PlayerMapper playerMapper) {
        this.playerGateway = playerGateway;
        this.playerMapper = playerMapper;
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerGateway.getAllPlayers();
        return players.stream().map(playerMapper::toDTO).toList();
    }

    public PlayerDTO getPlayerById(Long id) {
        Player player = playerGateway.getPlayerById(id);
        return playerMapper.toDTO(player);
    }

    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        playerGateway.createPlayer(player);
        return playerDTO;
    }

    public PlayerDTO updatePlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        playerGateway.updatePlayer(player);
        return playerDTO;
    }

    public void deletePlayer(Long id) {
        playerGateway.deletePlayer(id);
    }

    public List<PlayerDTO> getPlayersByTeamId(Long teamId) {
        List<Player> players = playerGateway.getPlayersByTeamId(teamId);
        return players.stream().map(playerMapper::toDTO).toList();
    }

    public Map<String, Object> getPlayerStats(Long playerId) {
        List<Map<String, Object>> matches = playerGateway.getPlayerStats(playerId);
        int totalGoals = playerGateway.getTotalGoals(playerId);

        return Map.of(
                "matches", matches,
                "totalGoals", totalGoals
        );
    }

    public List<PlayerDTO> getPlayersByLeagueId(Long leagueId) {
        List<Player> players = playerGateway.getPlayersByLeagueId(leagueId);
        return players.stream().map(playerMapper::toDTO).toList();
    }
}

