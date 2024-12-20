package com.example.spring_app.services;

import com.example.spring_app.dtos.SquadDTO;
import com.example.spring_app.entities.Squad;
import com.example.spring_app.mappers.SquadMapper;
import com.example.spring_app.tabledatagateways.SquadTableDataGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SquadService {

    public static final int MIN_PLAYERS = 7;
    public static final int MAX_PLAYERS = 18;

    private final SquadTableDataGateway squadGateway;
    private final SquadMapper squadMapper;

    public SquadService(SquadTableDataGateway squadGateway, SquadMapper squadMapper) {
        this.squadGateway = squadGateway;
        this.squadMapper = squadMapper;
    }

    public List<SquadDTO> getSquadByMatchId(Long matchId) {
        return squadGateway.getSquadByMatchId(matchId);
    }

    public int getSquadSize(Long matchId, Long teamId) {
        return squadGateway.getSquadSize(matchId, teamId);
    }

    public void addPlayerToSquad(SquadDTO squadDTO) {
        if (squadGateway.isPlayerInSquad(squadDTO.getPlayerId(), squadDTO.getMatchId())) {
            throw new IllegalArgumentException("Player is already in the squad.");
        }

        Squad squad = squadMapper.toEntity(squadDTO);
        squadGateway.addPlayerToSquad(squad);
    }

    public void removePlayerFromSquad(SquadDTO squadDTO) {
        int currentSize = getSquadSize(squadDTO.getMatchId(), squadDTO.getTeamId());
        if (currentSize <= MIN_PLAYERS) {
            throw new IllegalArgumentException("Cannot remove player. Minimum squad size is " + MIN_PLAYERS + ".");
        }

        squadGateway.removePlayerFromSquad(squadDTO.getPlayerId(), squadDTO.getMatchId());
    }

    public List<SquadDTO> getSquadByMatchAndTeam(Long matchId, Long teamId) {
        return squadGateway.getSquadByMatchAndTeam(matchId, teamId);
    }
}
