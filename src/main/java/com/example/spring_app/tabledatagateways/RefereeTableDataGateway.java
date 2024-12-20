package com.example.spring_app.tabledatagateways;

import com.example.spring_app.entities.Referee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RefereeTableDataGateway {

    private final JdbcTemplate jdbcTemplate;

    public RefereeTableDataGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Referee> getAllReferees() {
        String sql = "SELECT * FROM referees";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Referee referee = new Referee();
            referee.setId(rs.getLong("id"));
            referee.setFirstName(rs.getString("first_name"));
            referee.setLastName(rs.getString("last_name"));
            referee.setAge(rs.getInt("age"));
            referee.setExperienceLevel(rs.getString("experience_level"));
            return referee;
        });
    }

    public Referee getRefereeById(Long id) {
        String sql = "SELECT * FROM referees WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Referee referee = new Referee();
            referee.setId(rs.getLong("id"));
            referee.setFirstName(rs.getString("first_name"));
            referee.setLastName(rs.getString("last_name"));
            referee.setAge(rs.getInt("age"));
            referee.setExperienceLevel(rs.getString("experience_level"));
            return referee;
        });
    }

    public void createReferee(Referee referee) {
        String sql = "INSERT INTO referees (first_name, last_name, age, experience_level) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                referee.getFirstName(),
                referee.getLastName(),
                referee.getAge(),
                referee.getExperienceLevel());
    }

    public void updateReferee(Long id, Referee referee) {
        String sql = "UPDATE referees SET first_name = ?, last_name = ?, age = ?, experience_level = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                referee.getFirstName(),
                referee.getLastName(),
                referee.getAge(),
                referee.getExperienceLevel(),
                id);
    }

    public void deleteReferee(Long id) {
        String sql = "DELETE FROM referees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}