package com.example.spring_app.services;

import com.example.spring_app.dtos.LeagueDTO;
import com.example.spring_app.entities.League;
import com.example.spring_app.mappers.LeagueMapper;
import com.example.spring_app.tabledatagateways.LeagueTableDataGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    private final LeagueTableDataGateway leagueGateway;
    private final LeagueMapper leagueMapper;

    public LeagueService(LeagueTableDataGateway leagueGateway, LeagueMapper leagueMapper) {
        this.leagueGateway = leagueGateway;
        this.leagueMapper = leagueMapper;
    }

    public List<LeagueDTO> getAllLeagues() {
        List<League> leagues = leagueGateway.getAllLeagues();
        return leagues.stream().map(leagueMapper::toDTO).toList();
    }

    public LeagueDTO getLeagueById(Long id) {
        League league = leagueGateway.getLeagueById(id);
        return leagueMapper.toDTO(league);
    }

    public LeagueDTO createLeague(LeagueDTO leagueDTO) {
        League league = leagueMapper.toEntity(leagueDTO);
        leagueGateway.createLeague(league);
        return leagueDTO;
    }

    public LeagueDTO updateLeague(Long id, LeagueDTO leagueDTO) {
        League league = leagueMapper.toEntity(leagueDTO);
        leagueGateway.updateLeague(id, league);
        return leagueDTO;
    }

    public void deleteLeague(Long id) {
        leagueGateway.deleteLeague(id);
    }
}
