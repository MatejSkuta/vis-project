package com.example.spring_app.services;

import com.example.spring_app.dtos.RefereeDTO;
import com.example.spring_app.entities.Referee;
import com.example.spring_app.mappers.RefereeMapper;
import com.example.spring_app.tabledatagateways.RefereeTableDataGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeService {

    private final RefereeTableDataGateway refereeGateway;
    private final RefereeMapper refereeMapper;

    public RefereeService(RefereeTableDataGateway refereeGateway, RefereeMapper refereeMapper) {
        this.refereeGateway = refereeGateway;
        this.refereeMapper = refereeMapper;
    }


    public List<RefereeDTO> getAllReferees() {
        List<Referee> referees = refereeGateway.getAllReferees();
        return referees.stream().map(refereeMapper::toDTO).toList();
    }


    public RefereeDTO getRefereeById(Long id) {
        Referee referee = refereeGateway.getRefereeById(id);
        return refereeMapper.toDTO(referee);
    }


    public RefereeDTO createReferee(RefereeDTO refereeDTO) {
        Referee referee = refereeMapper.toEntity(refereeDTO);
        refereeGateway.createReferee(referee);
        return refereeDTO;
    }


    public RefereeDTO updateReferee(Long id, RefereeDTO refereeDTO) {
        Referee referee = refereeMapper.toEntity(refereeDTO);
        refereeGateway.updateReferee(id, referee);
        return refereeDTO;
    }

    public void deleteReferee(Long id) {
        refereeGateway.deleteReferee(id);
    }
}