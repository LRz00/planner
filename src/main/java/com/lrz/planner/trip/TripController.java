/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.trip;

import com.lrz.planner.trip.participants.ParticipantsService;
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
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequest request){
        Trip newTrip = new Trip(request);
        
        this.tripRepo.save(newTrip);
        
        this.particpantService.registersParticipantsToTrip(request.emails_to_invite(), newTrip.getId());
        
        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID id){
     Optional<Trip> trip = this.tripRepo.findById(id);
     
     return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
