package com.library.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Livre;
import com.library.model.Reservation;
import com.library.model.Reservation.ReservationStatus;
import com.library.model.User;
import com.library.repository.LivreRepository;
import com.library.repository.ReservationRepository;
import com.library.repository.UserRepository;

/**
 * Service for reservation operations
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private LivreRepository livreRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LivreService livreService;
    
    /**
     * Get a reservation by ID
     */
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'ID: " + id));
    }
    
    /**
     * Create a new reservation
     */
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // Validate inputs
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new BadRequestException("Les dates de début et de fin sont obligatoires");
        }
        
        if (reservation.getDateDebut().isAfter(reservation.getDateFin())) {
            throw new BadRequestException("La date de début doit être antérieure à la date de fin");
        }
        
        if (reservation.getDateDebut().isBefore(LocalDate.now())) {
            throw new BadRequestException("La date de début doit être dans le futur");
        }
        
        // Check if book exists
        Livre livre = livreRepository.findById(reservation.getLivre().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé"));
        
        // Check if user exists
        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        // Check if book is available
        if (!livre.isDisponible()) {
            throw new BadRequestException("Le livre n'est pas disponible");
        }
        
        // Check for overlapping reservations
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                livre.getId(), reservation.getDateDebut(), reservation.getDateFin());
        
        if (!overlappingReservations.isEmpty()) {
            throw new BadRequestException("Le livre est déjà réservé pour cette période");
        }
        
        // Create reservation
        Reservation newReservation = new Reservation();
        newReservation.setUser(user);
        newReservation.setLivre(livre);
        newReservation.setDateReservation(LocalDateTime.now());
        newReservation.setDateDebut(reservation.getDateDebut());
        newReservation.setDateFin(reservation.getDateFin());
        newReservation.setStatus(ReservationStatus.RESERVEE);
        
        // Save the reservation
        Reservation savedReservation = reservationRepository.save(newReservation);
        
        // If reservation starts today, mark book as unavailable
        if (reservation.getDateDebut().isEqual(LocalDate.now())) {
            livre.setDisponible(false);
            livreRepository.save(livre);
        }
        
        return savedReservation;
    }
    
    /**
     * Mark a reservation as borrowed
     */
    @Transactional
    public Reservation borrowBook(Long userId, Long livreId) {
        // Find active reservation
        List<Reservation> activeReservations = reservationRepository.findActiveReservationsForBook(livreId);
        
        Reservation reservation = activeReservations.stream()
                .filter(r -> r.getUser().getId().equals(userId) && r.getStatus() == ReservationStatus.RESERVEE)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Aucune réservation active trouvée pour cet utilisateur et ce livre"));
        
        // Update reservation status
        reservation.setStatus(ReservationStatus.EMPRUNTEE);
        
        // Mark book as borrowed
        livreService.markLivreAsBorrowed(livreId);
        
        return reservationRepository.save(reservation);
    }
    
    /**
     * Mark a reservation as returned
     */
    @Transactional
    public Reservation returnBook(Long userId, Long livreId) {
        // Find active borrowed reservation
        List<Reservation> activeReservations = reservationRepository.findActiveReservationsForBook(livreId);
        
        Reservation reservation = activeReservations.stream()
                .filter(r -> r.getUser().getId().equals(userId) && r.getStatus() == ReservationStatus.EMPRUNTEE)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Aucun emprunt actif trouvé pour cet utilisateur et ce livre"));
        
        // Update reservation status
        reservation.setStatus(ReservationStatus.RETOURNEE);
        reservation.setDateRetour(LocalDate.now());
        
        // Mark book as available
        livreService.markLivreAsReturned(livreId);
        
        return reservationRepository.save(reservation);
    }
    
    /**
     * Get all reservations for a user
     */
    public List<Reservation> getReservationsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        return reservationRepository.findByUser(user);
    }
    
    /**
     * Get all reservations for a book
     */
    public List<Reservation> getReservationsForBook(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé"));
        
        return reservationRepository.findByLivre(livre);
    }
}
