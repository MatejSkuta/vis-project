package com.example.spring_app.controllers;

import com.example.spring_app.dtos.RefereeDTO;
import com.example.spring_app.services.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referees")
public class RefereeController {

    @Autowired
    private RefereeService refereeService;

    @GetMapping
    public List<RefereeDTO> getAllReferees() {
        return refereeService.getAllReferees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefereeDTO> getRefereeById(@PathVariable Long id) {
        RefereeDTO referee = refereeService.getRefereeById(id);
        if (referee != null) {
            return ResponseEntity.ok(referee);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public RefereeDTO createReferee(@RequestBody RefereeDTO refereeDTO) {
        return refereeService.createReferee(refereeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefereeDTO> updateReferee(@PathVariable Long id, @RequestBody RefereeDTO refereeDTO) {
        RefereeDTO updatedReferee = refereeService.updateReferee(id, refereeDTO);
        if (updatedReferee != null) {
            return ResponseEntity.ok(updatedReferee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferee(@PathVariable Long id) {
        refereeService.deleteReferee(id);
        return ResponseEntity.noContent().build();
    }
}
