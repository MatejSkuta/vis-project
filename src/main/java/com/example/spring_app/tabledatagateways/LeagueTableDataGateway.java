package com.example.spring_app.tabledatagateways;

import com.example.spring_app.entities.League;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeagueTableDataGateway {
    private final JdbcTemplate jdbcTemplate;

    public LeagueTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<League> getAllLeagues() {
        String sql = "SELECT * FROM leagues";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            League league = new League();
            league.setId(rs.getLong("id"));
            league.setName(rs.getString("name"));
            league.setCompetitionYear(rs.getInt("competition_year"));
            league.setRegion(rs.getString("region"));
            return league;
        });
    }

    public League getLeagueById(Long id) {
        String sql = "SELECT * FROM leagues WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            League league = new League();
            league.setId(rs.getLong("id"));
            league.setName(rs.getString("name"));
            league.setCompetitionYear(rs.getInt("competition_year"));
            league.setRegion(rs.getString("region"));
            return league;
        });
    }

    public void createLeague(League league) {
        String sql = "INSERT INTO leagues (name, competition_year, region) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                league.getName(),
                league.getCompetitionYear(),
                league.getRegion());
    }

    public void updateLeague(Long id, League league) {
        String sql = "UPDATE leagues SET name = ?, competition_year = ?, region = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                league.getName(),
                league.getCompetitionYear(),
                league.getRegion(),
                id);
    }

    public void deleteLeague(Long id) {
        String sql = "DELETE FROM leagues WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
