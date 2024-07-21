/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.services;

import com.lrz.planner.DTOs.ParticipantCreatedResponseDTO;
import com.lrz.planner.DTOs.ParticipantDataDTO;
import com.lrz.planner.entities.Participant;
import com.lrz.planner.entities.Trip;
import com.lrz.planner.repositories.ParticipantRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lara
 */

@Service
public class ParticipantsService {
    
    @Autowired
    private ParticipantRepository repository;
    
    public void registersParticipantsToTrip(List<String> participantsToInvite, Trip trip){   
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        
        this.repository.saveAll(participants);
        
    }
    
    public ParticipantCreatedResponseDTO registerParticipantToTrip(Trip trip, String email){
        Participant newParticipant = new Participant(email, trip);
        
        this.repository.save(newParticipant);
        
        return new ParticipantCreatedResponseDTO(newParticipant.getId());
    }
    public void triggerConfirmationEmailToParticipants(UUID id){
    }

    public void triggerConfirmationEmailToParticipant(String email) {
        

    }

    public List<ParticipantDataDTO> getAllParticpantFromTrip(UUID id) {
        return this.repository.findByTripId(id).stream().map(
                participant -> new ParticipantDataDTO(participant.getId(),participant.getName(), 
                        participant.getEmail(), participant.getIsConfirmed())).toList();
    }
}
