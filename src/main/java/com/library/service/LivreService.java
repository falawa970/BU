package com.library.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Livre;
import com.library.repository.LivreRepository;
import com.library.repository.ReservationRepository;

/**
 * Service for book operations
 */
@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    /**
     * Get all books
     */
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }
    
    /**
     * Get a book by ID
     */
    public Livre getLivreById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé avec l'ID: " + id));
    }
    
    /**
     * Get available books
     */
    public List<Livre> getAvailableLivres() {
        return livreRepository.findAvailableBooks();
    }
    
    /**
     * Add a new book
     */
    @Transactional
    public Livre addLivre(Livre livre) {
        // Check if ISBN already exists
        if (livre.getIsbn() != null && livreRepository.findByIsbn(livre.getIsbn()) != null) {
            throw new BadRequestException("Un livre avec cet ISBN existe déjà");
        }
        
        livre.setDateCreation(LocalDateTime.now());
        livre.setDateModification(LocalDateTime.now());
        livre.setDisponible(true);
        
        return livreRepository.save(livre);
    }
    
    /**
     * Update an existing book
     */
    @Transactional
    public Livre updateLivre(Long id, Livre livreDetails) {
        Livre livre = getLivreById(id);
        
        // Check if ISBN already exists for another book
        if (livreDetails.getIsbn() != null && !livreDetails.getIsbn().equals(livre.getIsbn())) {
            Livre existingLivre = livreRepository.findByIsbn(livreDetails.getIsbn());
            if (existingLivre != null && !existingLivre.getId().equals(id)) {
                throw new BadRequestException("Un livre avec cet ISBN existe déjà");
            }
        }
        
        livre.setTitre(livreDetails.getTitre());
        livre.setAuteur(livreDetails.getAuteur());
        livre.setIsbn(livreDetails.getIsbn());
        livre.setDescription(livreDetails.getDescription());
        livre.setAnneePublication(livreDetails.getAnneePublication());
        livre.setDateModification(LocalDateTime.now());
        
        return livreRepository.save(livre);
    }
    
    /**
     * Delete a book
     */
    @Transactional
    public void deleteLivre(Long id) {
        Livre livre = getLivreById(id);
        
        // Check if the book has active reservations
        if (!reservationRepository.findActiveReservationsForBook(id).isEmpty()) {
            throw new BadRequestException("Impossible de supprimer un livre avec des réservations actives");
        }
        
        livreRepository.delete(livre);
    }
    
    /**
     * Mark a book as borrowed
     */
    @Transactional
    public Livre markLivreAsBorrowed(Long id) {
        Livre livre = getLivreById(id);
        
        if (!livre.isDisponible()) {
            throw new BadRequestException("Le livre n'est pas disponible");
        }
        
        livre.setDisponible(false);
        livre.setDateModification(LocalDateTime.now());
        
        return livreRepository.save(livre);
    }
    
    /**
     * Mark a book as returned
     */
    @Transactional
    public Livre markLivreAsReturned(Long id) {
        Livre livre = getLivreById(id);
        
        livre.setDisponible(true);
        livre.setDateModification(LocalDateTime.now());
        
        return livreRepository.save(livre);
    }

    public Livre save(Livre livre) {
        return null;
    }
}
