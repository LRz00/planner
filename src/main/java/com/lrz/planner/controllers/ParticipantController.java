/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.controllers;

import com.lrz.planner.DTOs.ParticipantDetailsDTO;
import com.lrz.planner.entities.Participant;
import com.lrz.planner.repositories.ParticipantRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lara
 */

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    
    @Autowired
    private ParticipantRepository repo;
    
    @PostMapping("/{id}")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantDetailsDTO details){
        Optional<Participant> participant = this.repo.findById(id);
        
        if(participant.isPresent()){
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(details.name());
            
            this.repo.save(rawParticipant);
            
            return ResponseEntity.ok(rawParticipant);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    
}
