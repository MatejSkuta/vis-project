package com.example.spring_app.services;

import com.example.spring_app.dtos.TeamDTO;
import com.example.spring_app.entities.Team;
import com.example.spring_app.mappers.TeamMapper;
import com.example.spring_app.tabledatagateways.TeamTableDataGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    private final TeamTableDataGateway teamGateway;
    private final TeamMapper teamMapper;

    public TeamService(TeamTableDataGateway teamGateway, TeamMapper teamMapper) {
        this.teamGateway = teamGateway;
        this.teamMapper = teamMapper;
    }

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamGateway.getAllTeams();
        return teams.stream().map(teamMapper::toDTO).toList();
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamGateway.getTeamById(id);
        return teamMapper.toDTO(team);
    }

    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        teamGateway.createTeam(team);
        return teamDTO;
    }

    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        teamGateway.updateTeam(id, team);
        return teamDTO;
    }

    public void deleteTeam(Long id) {
        teamGateway.deleteTeam(id);
    }

    public List<TeamDTO> getTeamsByLeague(Long leagueId) {
        List<Team> teams = teamGateway.getTeamsByLeague(leagueId);
        return teams.stream().map(teamMapper::toDTO).toList();
    }

    public Map<String, Object> getTeamStats(Long teamId) {
        return teamGateway.getTeamStats(teamId);
    }
    public List<Map<String, Object>> getMatchPerformance(Long teamId) {
        return teamGateway.getMatchPerformance(teamId);
    }

}
