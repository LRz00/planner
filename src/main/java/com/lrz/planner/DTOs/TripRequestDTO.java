/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.lrz.planner.trip;

import java.util.List;

/**
 *
 * @author lara
 */
public record TripRequestDTO(String destination, String starts_at, String ends_at, List<String> emails_to_invite,
        String owner_name, String owner_email) {
}
