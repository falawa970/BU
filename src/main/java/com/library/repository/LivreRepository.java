package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.model.Livre;

/**
 * Repository for book operations
 */
@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    
    /**
     * Find books that are available (not reserved or borrowed)
     */
    @Query("SELECT l FROM Livre l WHERE l.disponible = true")
    List<Livre> findAvailableBooks();
    
    /**
     * Find books by title containing the search term (case insensitive)
     */
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    
    /**
     * Find books by author containing the search term (case insensitive)
     */
    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
    
    /**
     * Find a book by its ISBN
     */
    Livre findByIsbn(String isbn);
}
