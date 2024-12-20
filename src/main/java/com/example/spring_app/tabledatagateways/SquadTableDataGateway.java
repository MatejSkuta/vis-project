package com.example.spring_app.tabledatagateways;

import com.example.spring_app.dtos.SquadDTO;
import com.example.spring_app.entities.Squad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SquadTableDataGateway {
    private final JdbcTemplate jdbcTemplate;

    public SquadTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SquadDTO> getSquadByMatchId(Long matchId) {
        String sql = """
        SELECT s.id_player, s.id_match, s.jersey_number, p.first_name, p.last_name, p.team_id
        FROM squads s
        JOIN players p ON s.id_player = p.id
        WHERE s.id_match = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{matchId}, (rs, rowNum) -> {
            SquadDTO squadDTO = new SquadDTO();
            squadDTO.setPlayerId(rs.getLong("id_player"));
            squadDTO.setMatchId(rs.getLong("id_match"));
            squadDTO.setJerseyNumber(rs.getInt("jersey_number"));
            squadDTO.setFirstName(rs.getString("first_name"));
            squadDTO.setLastName(rs.getString("last_name"));
            squadDTO.setTeamId(rs.getLong("team_id"));
            return squadDTO;
        });
    }

    public int getSquadSize(Long matchId, Long teamId) {
        String sql = """
        SELECT COUNT(*)
        FROM squads s
        JOIN players p ON s.id_player = p.id
        WHERE s.id_match = ? AND p.team_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, new Object[]{matchId, teamId}, Integer.class);
    }

    public boolean isPlayerInSquad(Long playerId, Long matchId) {
        String sql = "SELECT COUNT(*) FROM squads WHERE id_player = ? AND id_match = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{playerId, matchId}, Integer.class);
        return count != null && count > 0;
    }

    public void addPlayerToSquad(Squad squad) {
        String sql = "INSERT INTO squads (id_player, id_match, jersey_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, squad.getPlayerId(), squad.getMatchId(), squad.getJerseyNumber());
    }

    public void removePlayerFromSquad(Long playerId, Long matchId) {
        String sql = "DELETE FROM squads WHERE id_player = ? AND id_match = ?";
        jdbcTemplate.update(sql, playerId, matchId);
    }

    public List<SquadDTO> getSquadByMatchAndTeam(Long matchId, Long teamId) {
        String sql = """
        SELECT s.id_player, s.id_match, s.jersey_number, p.first_name, p.last_name, p.team_id
        FROM squads s
        JOIN players p ON s.id_player = p.id
        WHERE s.id_match = ? AND p.team_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{matchId, teamId}, (rs, rowNum) -> {
            SquadDTO squadDTO = new SquadDTO();
            squadDTO.setPlayerId(rs.getLong("id_player"));
            squadDTO.setMatchId(rs.getLong("id_match"));
            squadDTO.setJerseyNumber(rs.getInt("jersey_number"));
            squadDTO.setFirstName(rs.getString("first_name"));
            squadDTO.setLastName(rs.getString("last_name"));
            squadDTO.setTeamId(rs.getLong("team_id"));
            return squadDTO;
        });
    }
}
