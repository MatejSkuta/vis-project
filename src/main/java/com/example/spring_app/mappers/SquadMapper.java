package com.example.spring_app.mappers;

import com.example.spring_app.dtos.SquadDTO;
import com.example.spring_app.entities.Squad;
import org.springframework.stereotype.Component;

@Component
public class SquadMapper {

    public SquadDTO toDTO(Squad squad) {
        SquadDTO dto = new SquadDTO();
        dto.setPlayerId(squad.getPlayerId());
        dto.setMatchId(squad.getMatchId());
        dto.setJerseyNumber(squad.getJerseyNumber());
        return dto;
    }

    public Squad toEntity(SquadDTO dto) {
        Squad squad = new Squad();
        squad.setPlayerId(dto.getPlayerId());
        squad.setMatchId(dto.getMatchId());
        squad.setJerseyNumber(dto.getJerseyNumber());
        return squad;
    }
}
