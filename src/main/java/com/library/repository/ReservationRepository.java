package com.library.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.model.Livre;
import com.library.model.Reservation;
import com.library.model.Reservation.ReservationStatus;
import com.library.model.User;

/**
 * Repository for reservation operations
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    /**
     * Find all reservations for a specific user
     */
    List<Reservation> findByUser(User user);
    
    /**
     * Find all reservations for a specific book
     */
    List<Reservation> findByLivre(Livre livre);
    
    /**
     * Find reservations by status
     */
    List<Reservation> findByStatus(ReservationStatus status);
    
    /**
     * Find active reservations for a book (reserved or borrowed, not returned or cancelled)
     */
    @Query("SELECT r FROM Reservation r WHERE r.livre.id = :livreId AND (r.status = 'RESERVEE' OR r.status = 'EMPRUNTEE')")
    List<Reservation> findActiveReservationsForBook(@Param("livreId") Long livreId);
    
    /**
     * Find active reservations for a user
     */
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND (r.status = 'RESERVEE' OR r.status = 'EMPRUNTEE')")
    List<Reservation> findActiveReservationsForUser(@Param("userId") Long userId);
    
    /**
     * Find overlapping reservations for a book in a given date range
     */
    @Query("SELECT r FROM Reservation r WHERE r.livre.id = :livreId AND r.status IN ('RESERVEE', 'EMPRUNTEE') " +
           "AND ((r.dateDebut <= :dateFin AND r.dateFin >= :dateDebut) OR " +
           "(r.dateDebut >= :dateDebut AND r.dateDebut <= :dateFin))")
    List<Reservation> findOverlappingReservations(
            @Param("livreId") Long livreId,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
