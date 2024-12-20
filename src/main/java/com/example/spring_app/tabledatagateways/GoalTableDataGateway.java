package com.example.spring_app.tabledatagateways;

import com.example.spring_app.dtos.GoalDTO;
import com.example.spring_app.entities.Goal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoalTableDataGateway {
    private final JdbcTemplate jdbcTemplate;

    public GoalTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Goal> getAllGoals() {
        String sql = "SELECT * FROM goals";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Goal goal = new Goal();
            goal.setId(rs.getLong("id"));
            goal.setPlayerId(rs.getLong("player_id"));
            goal.setMatchId(rs.getLong("match_id"));
            goal.setGoalMinute(rs.getInt("goal_minute"));
            return goal;
        });
    }

    public Goal getGoalById(Long id) {
        String sql = "SELECT * FROM goals WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Goal g = new Goal();
            g.setId(rs.getLong("id"));
            g.setPlayerId(rs.getLong("player_id"));
            g.setMatchId(rs.getLong("match_id"));
            g.setGoalMinute(rs.getInt("goal_minute"));
            return g;
        });
    }

    public void createGoal(Goal goal) {
        String sql = "INSERT INTO goals (player_id, match_id, goal_minute) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                goal.getPlayerId(),
                goal.getMatchId(),
                goal.getGoalMinute());
    }

    public void updateGoal(Long id, Goal goal) {
        String sql = "UPDATE goals SET player_id = ?, match_id = ?, goal_minute = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                goal.getPlayerId(),
                goal.getMatchId(),
                goal.getGoalMinute(),
                id);
    }

    public void deleteGoalById(Long goalId) {
        String deleteSql = "DELETE FROM goals WHERE id = ?";
        jdbcTemplate.update(deleteSql, goalId);
    }

    public List<Goal> getGoalsByMatchId(Long matchId) {
        String sql = """
            SELECT g.id, g.player_id, g.match_id, g.goal_minute
            FROM goals g
            WHERE g.match_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{matchId}, (rs, rowNum) -> {
            Goal goal = new Goal();
            goal.setId(rs.getLong("id"));
            goal.setPlayerId(rs.getLong("player_id"));
            goal.setMatchId(rs.getLong("match_id"));
            goal.setGoalMinute(rs.getInt("goal_minute"));
            return goal;
        });
    }

    public void addGoal(Goal goal) {
        String sql = "INSERT INTO goals (player_id, match_id, goal_minute) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                goal.getPlayerId(),
                goal.getMatchId(),
                goal.getGoalMinute());
    }

    public void updateMatchScore(Long matchId) {
        String homeGoalsSql = """
            SELECT COUNT(*) FROM goals g
            JOIN players p ON g.player_id = p.id
            WHERE g.match_id = ? AND p.team_id = (
                SELECT home_team_id FROM matches WHERE id = ?
            )
        """;
        int homeGoals = jdbcTemplate.queryForObject(homeGoalsSql, new Object[]{matchId, matchId}, Integer.class);

        String awayGoalsSql = """
            SELECT COUNT(*) FROM goals g
            JOIN players p ON g.player_id = p.id
            WHERE g.match_id = ? AND p.team_id = (
                SELECT away_team_id FROM matches WHERE id = ?
            )
        """;
        int awayGoals = jdbcTemplate.queryForObject(awayGoalsSql, new Object[]{matchId, matchId}, Integer.class);

        // Aktualizace tabulky matches
        String updateMatchSql = "UPDATE matches SET home_team_score = ?, away_team_score = ? WHERE id = ?";
        jdbcTemplate.update(updateMatchSql, homeGoals, awayGoals, matchId);
    }
    public List<GoalDTO> getGoalsByMatchIdWithDetails(Long matchId) {
        String sql = """
        SELECT g.id, g.player_id, g.match_id, g.goal_minute,
               p.first_name, p.last_name, t.id AS team_id
        FROM goals g
        JOIN players p ON g.player_id = p.id
        JOIN teams t ON p.team_id = t.id
        WHERE g.match_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{matchId}, (rs, rowNum) -> {
            GoalDTO goalDTO = new GoalDTO();
            goalDTO.setId(rs.getLong("id"));
            goalDTO.setPlayerId(rs.getLong("player_id"));
            goalDTO.setMatchId(rs.getLong("match_id"));
            goalDTO.setGoalMinute(rs.getInt("goal_minute"));
            goalDTO.setFirstName(rs.getString("first_name"));
            goalDTO.setLastName(rs.getString("last_name"));
            goalDTO.setTeamId(rs.getLong("team_id"));
            return goalDTO;
        });
    }
}
