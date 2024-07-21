/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.lrz.planner.DTOs;

import java.util.UUID;

/**
 *
 * @author lara
 */
public record ParticipantDataDTO(UUID id, String name, String email, Boolean isConfirmed) {

}
