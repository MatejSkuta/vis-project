package com.example.spring_app.mappers;

import com.example.spring_app.dtos.RefereeDTO;
import com.example.spring_app.entities.Referee;
import org.springframework.stereotype.Component;

@Component
public class RefereeMapper {

    public RefereeDTO toDTO(Referee referee) {
        RefereeDTO dto = new RefereeDTO();
        dto.setId(referee.getId());
        dto.setFirstName(referee.getFirstName());
        dto.setLastName(referee.getLastName());
        dto.setAge(referee.getAge());
        dto.setExperienceLevel(referee.getExperienceLevel());
        return dto;
    }

    public Referee toEntity(RefereeDTO dto) {
        Referee referee = new Referee();
        referee.setId(dto.getId());
        referee.setFirstName(dto.getFirstName());
        referee.setLastName(dto.getLastName());
        referee.setAge(dto.getAge());
        referee.setExperienceLevel(dto.getExperienceLevel());
        return referee;
    }
}
