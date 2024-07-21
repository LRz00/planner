/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.controllers;

import com.lrz.planner.DTOs.ParticipantCreatedResponseDTO;
import com.lrz.planner.DTOs.ParticipantDataDTO;
import com.lrz.planner.DTOs.ParticipantDetailsDTO;
import com.lrz.planner.entities.Participant;
import com.lrz.planner.entities.Trip;
import com.lrz.planner.trip.TripCreateResponseDTO;
import com.lrz.planner.trip.TripRepository;
import com.lrz.planner.trip.TripRequestDTO;
import com.lrz.planner.services.ParticipantsService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<TripCreateResponseDTO> createTrip(@RequestBody TripRequestDTO request) {
        Trip newTrip = new Trip(request);

        this.tripRepo.save(newTrip);

        this.particpantService.registersParticipantsToTrip(request.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponseDTO(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepo.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, TripRequestDTO request) {
        Optional<Trip> trip = this.tripRepo.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setDestination(request.destination());
            rawTrip.setEndsAt(LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setStartsAt(LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME));

            this.tripRepo.save(rawTrip);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepo.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);
            this.tripRepo.save(rawTrip);
            this.particpantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParticipantCreatedResponseDTO> InviteParticipant(@RequestBody ParticipantDetailsDTO request, @PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepo.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantCreatedResponseDTO response = this.particpantService.registerParticipantToTrip(rawTrip, request.email());

            if (rawTrip.getIsConfirmed()) {
                this.particpantService.triggerConfirmationEmailToParticipant(request.email());
            }

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataDTO>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantDataDTO> participants = this.particpantService.getAllParticpantFromTrip(id);
        
        return ResponseEntity.ok(participants);
    }
}
