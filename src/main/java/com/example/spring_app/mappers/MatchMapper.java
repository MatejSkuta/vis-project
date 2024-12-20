package com.example.spring_app.mappers;

import com.example.spring_app.dtos.MatchDTO;
import com.example.spring_app.entities.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public MatchDTO toDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setHomeTeamId(match.getHomeTeamId());
        dto.setAwayTeamId(match.getAwayTeamId());
        dto.setLeagueId(match.getLeagueId());
        dto.setMatchDate(match.getMatchDate());
        dto.setHomeTeamScore(match.getHomeTeamScore());
        dto.setAwayTeamScore(match.getAwayTeamScore());
        dto.setRefereeId(match.getRefereeId());
        dto.setGoalIds(match.getGoalIds());
        return dto;
    }

    public Match toEntity(MatchDTO dto) {
        Match match = new Match();
        match.setId(dto.getId());
        match.setHomeTeamId(dto.getHomeTeamId());
        match.setAwayTeamId(dto.getAwayTeamId());
        match.setLeagueId(dto.getLeagueId());
        match.setMatchDate(dto.getMatchDate());
        match.setHomeTeamScore(dto.getHomeTeamScore());
        match.setAwayTeamScore(dto.getAwayTeamScore());
        match.setRefereeId(dto.getRefereeId());
        match.setGoalIds(dto.getGoalIds());
        return match;
    }
}
