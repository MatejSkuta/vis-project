package com.example.spring_app.tabledatagateways;

import com.example.spring_app.entities.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeamTableDataGateway {

    private final JdbcTemplate jdbcTemplate;

    public TeamTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Team> getAllTeams() {
        String sql = "SELECT id, name, founded_year, stadium_name, league_id FROM teams";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Team team = new Team();
            team.setId(rs.getLong("id"));
            team.setName(rs.getString("name"));
            team.setFoundedYear(rs.getInt("founded_year"));
            team.setStadiumName(rs.getString("stadium_name"));
            team.setLeagueId(rs.getLong("league_id"));
            return team;
        });
    }

    public Team getTeamById(Long id) {
        String sql = """
                SELECT id, name, founded_year, stadium_name, league_id
                FROM teams
                WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Team team = new Team();
            team.setId(rs.getLong("id"));
            team.setName(rs.getString("name"));
            team.setFoundedYear(rs.getInt("founded_year"));
            team.setStadiumName(rs.getString("stadium_name"));
            team.setLeagueId(rs.getLong("league_id"));
            return team;
        });
    }

    public void createTeam(Team team) {
        String sql = "INSERT INTO teams (name, founded_year, stadium_name, league_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                team.getName(),
                team.getFoundedYear(),
                team.getStadiumName(),
                team.getLeagueId());
    }

    public void updateTeam(Long id, Team team) {
        String sql = "UPDATE teams SET name = ?, founded_year = ?, stadium_name = ?, league_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                team.getName(),
                team.getFoundedYear(),
                team.getStadiumName(),
                team.getLeagueId(),
                id);
    }

    public void deleteTeam(Long id) {
        String sql = "DELETE FROM teams WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Team> getTeamsByLeague(Long leagueId) {
        String sql = """
            SELECT id, name, founded_year, stadium_name, league_id
            FROM teams
            WHERE league_id = ?
        """;
        return jdbcTemplate.query(sql, new Object[]{leagueId}, (rs, rowNum) -> {
            Team team = new Team();
            team.setId(rs.getLong("id"));
            team.setName(rs.getString("name"));
            team.setFoundedYear(rs.getInt("founded_year"));
            team.setStadiumName(rs.getString("stadium_name"));
            team.setLeagueId(rs.getLong("league_id"));
            return team;
        });
    }

    public Map<String, Object> getTeamStats(Long teamId) {
        String matchesSql = """
            SELECT COUNT(*) AS matchesPlayed,
                   SUM(CASE WHEN home_team_id = ? AND home_team_score > away_team_score THEN 1 
                            WHEN away_team_id = ? AND away_team_score > home_team_score THEN 1 
                            ELSE 0 END) AS wins,
                   SUM(CASE WHEN home_team_id = ? AND home_team_score = away_team_score THEN 1 
                            WHEN away_team_id = ? AND home_team_score = away_team_score THEN 1 
                            ELSE 0 END) AS draws,
                   SUM(CASE WHEN home_team_id = ? AND home_team_score < away_team_score THEN 1 
                            WHEN away_team_id = ? AND home_team_score > away_team_score THEN 1 
                            ELSE 0 END) AS losses,
                   SUM(CASE WHEN home_team_id = ? THEN home_team_score 
                            WHEN away_team_id = ? THEN away_team_score ELSE 0 END) AS goalsScored,
                   SUM(CASE WHEN home_team_id = ? THEN away_team_score 
                            WHEN away_team_id = ? THEN home_team_score ELSE 0 END) AS goalsConceded
            FROM matches
            WHERE home_team_id = ? OR away_team_id = ?
        """;

        return jdbcTemplate.queryForObject(matchesSql, new Object[]{
                teamId, teamId,
                teamId, teamId,
                teamId, teamId,
                teamId, teamId,
                teamId, teamId,
                teamId, teamId
        }, (rs, rowNum) -> {
            Map<String, Object> stats = new HashMap<>();
            stats.put("matchesPlayed", rs.getInt("matchesPlayed"));
            stats.put("wins", rs.getInt("wins"));
            stats.put("draws", rs.getInt("draws"));
            stats.put("losses", rs.getInt("losses"));
            stats.put("goalsScored", rs.getInt("goalsScored"));
            stats.put("goalsConceded", rs.getInt("goalsConceded"));
            return stats;
        });
    }
    public List<Map<String, Object>> getMatchPerformance(Long teamId) {
        String performanceSql = """
            SELECT match_date,
                   home_team_id,
                   away_team_id,
                   home_team_score,
                   away_team_score,
                   CASE 
                        WHEN home_team_id = ? AND home_team_score > away_team_score THEN 'Win'
                        WHEN away_team_id = ? AND away_team_score > home_team_score THEN 'Win'
                        WHEN home_team_score = away_team_score THEN 'Draw'
                        ELSE 'Loss'
                   END AS result
            FROM matches
            WHERE home_team_id = ? OR away_team_id = ?
            ORDER BY match_date DESC
        """;

        return jdbcTemplate.query(performanceSql, new Object[]{teamId, teamId, teamId, teamId}, (rs, rowNum) -> {
            Map<String, Object> match = new HashMap<>();
            match.put("date", rs.getDate("match_date").toLocalDate());
            match.put("homeTeamId", rs.getLong("home_team_id"));
            match.put("awayTeamId", rs.getLong("away_team_id"));
            match.put("homeTeamScore", rs.getInt("home_team_score"));
            match.put("awayTeamScore", rs.getInt("away_team_score"));
            match.put("result", rs.getString("result"));
            return match;
        });
    }
}