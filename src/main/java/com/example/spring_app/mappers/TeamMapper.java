package com.example.spring_app.mappers;

import com.example.spring_app.dtos.TeamDTO;
import com.example.spring_app.entities.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    public Team toEntity(TeamDTO dto) {
        Team team = new Team();
        team.setId(dto.getId());
        team.setName(dto.getName());
        team.setStadiumName(dto.getStadiumName());
        team.setFoundedYear(dto.getFoundedYear());
        team.setLeagueId(dto.getLeagueId());
        return team;
    }

    public TeamDTO toDTO(Team entity) {
        TeamDTO dto = new TeamDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStadiumName(entity.getStadiumName());
        dto.setFoundedYear(entity.getFoundedYear());
        dto.setLeagueId(entity.getLeagueId());
        return dto;
    }
}
