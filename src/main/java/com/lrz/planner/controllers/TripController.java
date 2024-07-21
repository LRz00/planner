/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.controllers;

import com.lrz.planner.entities.Trip;
import com.lrz.planner.trip.TripCreateResponseDTO;
import com.lrz.planner.trip.TripRepository;
import com.lrz.planner.trip.TripRequestDTO;
import com.lrz.planner.services.ParticipantsService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/trips")
public class TripController {
    
    @Autowired
    private ParticipantsService particpantService;
    
    @Autowired
    private TripRepository tripRepo;
    
    
    @PostMapping
    public ResponseEntity<TripCreateResponseDTO> createTrip(@RequestBody TripRequestDTO request){
        Trip newTrip = new Trip(request);
        
        this.tripRepo.save(newTrip);
        
        this.particpantService.registersParticipantsToTrip(request.emails_to_invite(), newTrip.getId());
        
        return ResponseEntity.ok(new TripCreateResponseDTO(newTrip.getId()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID id){
     Optional<Trip> trip = this.tripRepo.findById(id);
     
     return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
