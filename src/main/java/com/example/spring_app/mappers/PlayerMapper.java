package com.example.spring_app.mappers;

import com.example.spring_app.dtos.PlayerDTO;
import com.example.spring_app.entities.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setPosition(player.getPosition());
        dto.setTeamId(player.getTeamId());
        dto.setGoalIds(player.getGoalIds());
        return dto;
    }

    public Player toEntity(PlayerDTO dto) {
        Player player = new Player();
        player.setId(dto.getId());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setPosition(dto.getPosition());
        player.setTeamId(dto.getTeamId());
        player.setGoalIds(dto.getGoalIds());
        return player;
    }
}
