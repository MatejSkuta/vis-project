package com.example.spring_app.mappers;

import com.example.spring_app.dtos.LeagueDTO;
import com.example.spring_app.entities.League;
import org.springframework.stereotype.Component;

@Component
public class LeagueMapper {

    public LeagueDTO toDTO(League league) {
        LeagueDTO dto = new LeagueDTO();
        dto.setId(league.getId());
        dto.setName(league.getName());
        dto.setCompetitionYear(league.getCompetitionYear());
        dto.setRegion(league.getRegion());
        dto.setTeamIds(league.getTeamIds());
        return dto;
    }

    public League toEntity(LeagueDTO dto) {
        League league = new League();
        league.setId(dto.getId());
        league.setName(dto.getName());
        league.setCompetitionYear(dto.getCompetitionYear());
        league.setRegion(dto.getRegion());
        league.setTeamIds(dto.getTeamIds());
        return league;
    }
}
