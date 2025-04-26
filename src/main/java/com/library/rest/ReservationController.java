package com.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Reservation;
import com.library.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for reservation operations
 */
@RestController
@RequestMapping("/reservations")
@Tag(name = "Réservations", description = "API pour la gestion des réservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    /**
     * Get a reservation by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Suivi d'une réservation", 
               description = "Récupère les informations détaillées d'une réservation à partir de son ID")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }
    
    /**
     * Create a new reservation
     */
    @PostMapping
    @Operation(summary = "Réservation d'un livre", 
               description = "Crée une nouvelle réservation pour un livre à une période précise")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);
    }
}
