package com.example.spring_app.tabledatagateways;

import com.example.spring_app.dtos.MatchDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class MatchTableDataGateway {

    private final JdbcTemplate jdbcTemplate;

    public MatchTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MatchDTO> getAllMatches() {
        String sql = """
            SELECT m.*, ht.name AS home_team_name, at.name AS away_team_name
            FROM matches m
            JOIN teams ht ON m.home_team_id = ht.id
            JOIN teams at ON m.away_team_id = at.id
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MatchDTO match = new MatchDTO();
            match.setId(rs.getLong("id"));
            match.setHomeTeamId(rs.getLong("home_team_id"));
            match.setHomeTeamName(rs.getString("home_team_name"));
            match.setAwayTeamId(rs.getLong("away_team_id"));
            match.setAwayTeamName(rs.getString("away_team_name"));
            match.setLeagueId(rs.getLong("league_id"));
            match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());
            match.setHomeTeamScore(rs.getInt("home_team_score"));
            match.setAwayTeamScore(rs.getInt("away_team_score"));
            match.setRefereeId(rs.getLong("referee_id"));
            return match;
        });
    }

    public MatchDTO getMatchById(Long id) {
        String sql = """
            SELECT m.*, ht.name AS home_team_name, at.name AS away_team_name
            FROM matches m
            JOIN teams ht ON m.home_team_id = ht.id
            JOIN teams at ON m.away_team_id = at.id
            WHERE m.id = ?
            """;
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            MatchDTO match = new MatchDTO();
            match.setId(rs.getLong("id"));
            match.setHomeTeamId(rs.getLong("home_team_id"));
            match.setHomeTeamName(rs.getString("home_team_name"));
            match.setAwayTeamId(rs.getLong("away_team_id"));
            match.setAwayTeamName(rs.getString("away_team_name"));
            match.setLeagueId(rs.getLong("league_id"));
            match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());
            match.setHomeTeamScore(rs.getInt("home_team_score"));
            match.setAwayTeamScore(rs.getInt("away_team_score"));
            match.setRefereeId(rs.getLong("referee_id"));
            return match;
        });
    }

    public List<MatchDTO> getMatchesByLeagueId(Long leagueId) {
        String sql = """
            SELECT m.*, ht.name AS home_team_name, at.name AS away_team_name
            FROM matches m
            JOIN teams ht ON m.home_team_id = ht.id
            JOIN teams at ON m.away_team_id = at.id
            WHERE m.league_id = ?
            """;
        return jdbcTemplate.query(sql, new Object[]{leagueId}, (rs, rowNum) -> {
            MatchDTO match = new MatchDTO();
            match.setId(rs.getLong("id"));
            match.setHomeTeamId(rs.getLong("home_team_id"));
            match.setHomeTeamName(rs.getString("home_team_name"));
            match.setAwayTeamId(rs.getLong("away_team_id"));
            match.setAwayTeamName(rs.getString("away_team_name"));
            match.setLeagueId(rs.getLong("league_id"));
            match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());
            match.setHomeTeamScore(rs.getInt("home_team_score"));
            match.setAwayTeamScore(rs.getInt("away_team_score"));
            match.setRefereeId(rs.getLong("referee_id"));
            return match;
        });
    }

    public void createMatch(MatchDTO matchDTO) {
        String sql = """
            INSERT INTO matches (home_team_id, away_team_id, league_id, match_date, home_team_score, away_team_score, referee_id) 
            VALUES (?, ?, ?, ?, 0, 0, ?)
            """;
        jdbcTemplate.update(sql,
                matchDTO.getHomeTeamId(),
                matchDTO.getAwayTeamId(),
                matchDTO.getLeagueId(),
                matchDTO.getMatchDate(),
                matchDTO.getRefereeId());
    }

    public void updateMatch(Long id, MatchDTO matchDTO) {
        String sql = """
            UPDATE matches 
            SET home_team_id = ?, away_team_id = ?, league_id = ?, match_date = ?, home_team_score = ?, away_team_score = ?, referee_id = ? 
            WHERE id = ?
            """;
        jdbcTemplate.update(sql,
                matchDTO.getHomeTeamId(),
                matchDTO.getAwayTeamId(),
                matchDTO.getLeagueId(),
                matchDTO.getMatchDate(),
                matchDTO.getHomeTeamScore(),
                matchDTO.getAwayTeamScore(),
                matchDTO.getRefereeId(),
                id);
    }

    public void deleteMatch(Long id) {
        String sql = "DELETE FROM matches WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<MatchDTO> getMatchesByTeam(Long teamId) {
        String sql = """
            SELECT
                m.id,
                m.home_team_id,
                m.away_team_id,
                m.league_id,
                m.match_date,
                m.home_team_score,
                m.away_team_score,
                m.referee_id,
                t1.name AS home_team_name,
                t2.name AS away_team_name
            FROM matches m
            JOIN teams t1 ON m.home_team_id = t1.id
            JOIN teams t2 ON m.away_team_id = t2.id
            WHERE m.home_team_id = ? OR m.away_team_id = ?
            ORDER BY m.match_date DESC
        """;

        return jdbcTemplate.query(sql, new Object[]{teamId, teamId}, (rs, rowNum) -> {
            MatchDTO match = new MatchDTO();
            match.setId(rs.getLong("id"));
            match.setHomeTeamId(rs.getLong("home_team_id"));
            match.setAwayTeamId(rs.getLong("away_team_id"));
            match.setLeagueId(rs.getLong("league_id"));
            match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());
            match.setHomeTeamScore(rs.getObject("home_team_score", Integer.class));
            match.setAwayTeamScore(rs.getObject("away_team_score", Integer.class));
            match.setRefereeId(rs.getLong("referee_id"));
            match.setHomeTeamName(rs.getString("home_team_name"));
            match.setAwayTeamName(rs.getString("away_team_name"));
            return match;
        });
    }

    public Integer countConflicts(Long teamId, Long refereeId, LocalDateTime start, LocalDateTime end) {
        String sql = """
            SELECT COUNT(*) 
            FROM matches 
            WHERE (home_team_id = ? OR away_team_id = ? OR referee_id = ?) 
              AND match_date BETWEEN ? AND ?
            """;
        return jdbcTemplate.queryForObject(sql, Integer.class, teamId, teamId, refereeId, start, end);
    }

    public Integer countRefereeConflicts(LocalDateTime matchDate, Long refereeId) {
        String sql = """
            SELECT COUNT(*) FROM matches
            WHERE match_date = ? AND referee_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, matchDate, refereeId);
    }
}
