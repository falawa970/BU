package com.library.soap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.library.model.Livre;
import com.library.model.Reservation;
import com.library.service.LivreService;
import com.library.service.ReservationService;

import com.library.soap.generated.AjouterLivreRequest;
import com.library.soap.generated.AjouterLivreResponse;
import com.library.soap.generated.ModifierLivreRequest;
import com.library.soap.generated.ModifierLivreResponse;
import com.library.soap.generated.PreterLivreRequest;
import com.library.soap.generated.PreterLivreResponse;
import com.library.soap.generated.RetournerLivreRequest;
import com.library.soap.generated.RetournerLivreResponse;
import com.library.soap.generated.SupprimerLivreRequest;
import com.library.soap.generated.SupprimerLivreResponse;
//import com.library.soap.generated.Livre;
//import com.library.soap.generated.Reservation;


/**
 * SOAP endpoint for library administration operations
 */
@Endpoint
public class BibliothequeEndpoint {

    private static final String NAMESPACE_URI = "http://bibliotheque.ws";
    
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private ReservationService reservationService;
    
    /**
     * Add a new book
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ajouterLivreRequest")
    @ResponsePayload
    public AjouterLivreResponse ajouterLivre(@RequestPayload AjouterLivreRequest request) {
        AjouterLivreResponse response = new AjouterLivreResponse();
        
        // Convert from SOAP DTO to entity
        Livre livre = new Livre();
        livre.setTitre(request.getLivre().getTitre());
        livre.setAuteur(request.getLivre().getAuteur());
        livre.setIsbn(request.getLivre().getIsbn());
        livre.setDescription(request.getLivre().getDescription());
        livre.setAnneePublication(request.getLivre().getAnneePublication());
        
        // Save the book
        Livre savedLivre = livreService.addLivre(livre);
        
        // Convert back to SOAP DTO
        com.library.soap.generated.Livre livreResponse = new com.library.soap.generated.Livre();
        BeanUtils.copyProperties(savedLivre, livreResponse);
        
        response.setLivre(livreResponse);
        response.setSuccess(true);
        response.setMessage("Livre ajouté avec succès");
        
        return response;
    }
    
    /**
     * Update an existing book
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifierLivreRequest")
    @ResponsePayload
    public ModifierLivreResponse modifierLivre(@RequestPayload ModifierLivreRequest request) {
        ModifierLivreResponse response = new ModifierLivreResponse();
        
        // Convert from SOAP DTO to entity
        Livre livre = new Livre();
        livre.setTitre(request.getLivre().getTitre());
        livre.setAuteur(request.getLivre().getAuteur());
        livre.setIsbn(request.getLivre().getIsbn());
        livre.setDescription(request.getLivre().getDescription());
        livre.setAnneePublication(request.getLivre().getAnneePublication());
        
        // Update the book
        Livre updatedLivre = livreService.updateLivre(request.getLivreId(), livre);
        
        // Convert back to SOAP DTO
        com.library.soap.generated.Livre livreResponse = new com.library.soap.generated.Livre();
        BeanUtils.copyProperties(updatedLivre, livreResponse);
        
        response.setLivre(livreResponse);
        response.setSuccess(true);
        response.setMessage("Livre modifié avec succès");
        
        return response;
    }
    
    /**
     * Delete a book
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "supprimerLivreRequest")
    @ResponsePayload
    public SupprimerLivreResponse supprimerLivre(@RequestPayload SupprimerLivreRequest request) {
        SupprimerLivreResponse response = new SupprimerLivreResponse();
        
        livreService.deleteLivre(request.getLivreId());
        
        response.setSuccess(true);
        response.setMessage("Livre supprimé avec succès");
        
        return response;
    }
    
    /**
     * Lend a book to a user
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "preterLivreRequest")
    @ResponsePayload
    public PreterLivreResponse preterLivre(@RequestPayload PreterLivreRequest request) {
        PreterLivreResponse response = new PreterLivreResponse();
        
        Reservation reservation = reservationService.borrowBook(request.getUserId(), request.getLivreId());
        
        // Convert to SOAP DTO
        com.library.soap.generated.Reservation reservationResponse = new com.library.soap.generated.Reservation();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setStatus(reservation.getStatus().toString());
        
        response.setReservation(reservationResponse);
        response.setSuccess(true);
        response.setMessage("Livre prêté avec succès");
        
        return response;
    }
    
    /**
     * Return a book
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "retournerLivreRequest")
    @ResponsePayload
    public RetournerLivreResponse retournerLivre(@RequestPayload RetournerLivreRequest request) {
        RetournerLivreResponse response = new RetournerLivreResponse();
        
        Reservation reservation = reservationService.returnBook(request.getUserId(), request.getLivreId());
        
        // Convert to SOAP DTO
        com.library.soap.generated.Reservation reservationResponse = new com.library.soap.generated.Reservation();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setStatus(reservation.getStatus().toString());
        
        response.setReservation(reservationResponse);
        response.setSuccess(true);
        response.setMessage("Livre retourné avec succès");
        
        return response;
    }
}
