package com.example.spring_app.tabledatagateways;

import com.example.spring_app.entities.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PlayerTableDataGateway {

    private final JdbcTemplate jdbcTemplate;

    public PlayerTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Player> getAllPlayers() {
        String sql = "SELECT * FROM players";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Player player = new Player();
            player.setId(rs.getLong("id"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
            player.setPosition(rs.getString("position"));
            player.setTeamId(rs.getLong("team_id"));
            return player;
        });
    }

    public Player getPlayerById(Long id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Player player = new Player();
            player.setId(rs.getLong("id"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
            player.setPosition(rs.getString("position"));
            player.setTeamId(rs.getLong("team_id"));
            return player;
        });
    }

    public void createPlayer(Player player) {
        String sql = "INSERT INTO players (first_name, last_name, position, team_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, player.getFirstName(), player.getLastName(), player.getPosition(), player.getTeamId());
    }

    public void updatePlayer(Player player) {
        String sql = "UPDATE players SET first_name = ?, last_name = ?, position = ?, team_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, player.getFirstName(), player.getLastName(), player.getPosition(), player.getTeamId(), player.getId());
    }

    public void deletePlayer(Long id) {
        String sql = "DELETE FROM players WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Player> getPlayersByTeamId(Long teamId) {
        String sql = "SELECT * FROM players WHERE team_id = ?";
        return jdbcTemplate.query(sql, new Object[]{teamId}, (rs, rowNum) -> {
            Player player = new Player();
            player.setId(rs.getLong("id"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
            player.setPosition(rs.getString("position"));
            player.setTeamId(rs.getLong("team_id"));
            return player;
        });
    }

    public List<Map<String, Object>> getPlayerStats(Long playerId) {
        String sql = """
            SELECT 
                m.match_date,
                COUNT(g.id) AS total_goals
            FROM 
                matches m
            LEFT JOIN 
                goals g ON g.match_id = m.id AND g.player_id = ?
            WHERE 
                m.home_team_id IN (
                    SELECT p.team_id 
                    FROM squads s 
                    JOIN players p ON s.id_player = p.id 
                    WHERE s.id_player = ?
                )
                OR m.away_team_id IN (
                    SELECT p.team_id 
                    FROM squads s 
                    JOIN players p ON s.id_player = p.id 
                    WHERE s.id_player = ?
                )
            GROUP BY 
                m.match_date
            ORDER BY 
                m.match_date DESC
            LIMIT 10
        """;
        return jdbcTemplate.query(sql, new Object[]{playerId, playerId, playerId}, (rs, rowNum) -> {
            Map<String, Object> match = Map.of(
                    "matchDate", rs.getTimestamp("match_date").toLocalDateTime(),
                    "totalGoals", rs.getInt("total_goals")
            );
            return match;
        });
    }

    public int getTotalGoals(Long playerId) {
        String sql = "SELECT COUNT(*) FROM goals WHERE player_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{playerId}, Integer.class);
    }

    public List<Player> getPlayersByLeagueId(Long leagueId) {
        String sql = """
        SELECT p.* FROM players p
        JOIN teams t ON p.team_id = t.id
        WHERE t.league_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{leagueId}, (rs, rowNum) -> {
            Player player = new Player();
            player.setId(rs.getLong("id"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
            player.setPosition(rs.getString("position"));
            player.setTeamId(rs.getLong("team_id"));
            return player;
        });
    }
}

