package com.library.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.model.Livre;
import com.library.service.LivreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for book operations
 */
@RestController
@RequestMapping("/livres")
@Tag(name = "Livres", description = "API pour la gestion des livres")
public class LivreController {

    @Autowired
    private LivreService livreService;
    
    /**
     * Get all books
     */
    @GetMapping
    @Operation(summary = "Récupération de la liste des livres", 
               description = "Récupère tous les livres de la bibliothèque")
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreService.getAllLivres();
        return ResponseEntity.ok(livres);
    }
    
    /**
     * Get a book by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Afficher les informations d'un livre", 
               description = "Récupère les informations détaillées d'un livre à partir de son ID")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Livre livre = livreService.getLivreById(id);
        return ResponseEntity.ok(livre);
    }
    
    /**
     * Get available books
     */
    @GetMapping("/disponibles")
    @Operation(summary = "Récupérer les livres disponibles", 
               description = "Récupère tous les livres actuellement disponibles (non prêtés et non réservés)")
    public ResponseEntity<List<Livre>> getAvailableLivres() {
        List<Livre> livres = livreService.getAvailableLivres();
        return ResponseEntity.ok(livres);
    }
    @PostMapping
    public Livre createLivre(@RequestBody Livre livre){
        return  livreService.save(livre);
    }
}
