/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lrz.planner.entities;

import com.lrz.planner.trip.TripRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author lara
 */
@Table(name="trips")
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
     @Column(nullable = false)
    private String destination;
    
    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;
   
    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;
    
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;
    
    @Column(name="owner_name", nullable = false)
    private String ownerName;
    
    @Column(name="owner_email", nullable = false)
    private String ownerEmail;
    
    
    public Trip(TripRequestDTO request){
        this.destination = request.destination();
        this.isConfirmed = false;
        this.ownerEmail = request.owner_email();
        this.ownerName = request.owner_name();
        this.startsAt = LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAt = LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
    }

    public Trip(UUID id, String destination, LocalDateTime startsAt, LocalDateTime endsAt, Boolean isConfirmed, String ownerName, String ownerEmail) {
        this.id = id;
        this.destination = destination;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.isConfirmed = isConfirmed;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
    }

    public Trip() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
    
    
}
