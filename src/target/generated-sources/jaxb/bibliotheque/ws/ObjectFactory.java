
package bibliotheque.ws;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bibliotheque.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bibliotheque.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AjouterLivreRequest }
     * 
     */
    public AjouterLivreRequest createAjouterLivreRequest() {
        return new AjouterLivreRequest();
    }

    /**
     * Create an instance of {@link Livre }
     * 
     */
    public Livre createLivre() {
        return new Livre();
    }

    /**
     * Create an instance of {@link AjouterLivreResponse }
     * 
     */
    public AjouterLivreResponse createAjouterLivreResponse() {
        return new AjouterLivreResponse();
    }

    /**
     * Create an instance of {@link ModifierLivreRequest }
     * 
     */
    public ModifierLivreRequest createModifierLivreRequest() {
        return new ModifierLivreRequest();
    }

    /**
     * Create an instance of {@link ModifierLivreResponse }
     * 
     */
    public ModifierLivreResponse createModifierLivreResponse() {
        return new ModifierLivreResponse();
    }

    /**
     * Create an instance of {@link SupprimerLivreRequest }
     * 
     */
    public SupprimerLivreRequest createSupprimerLivreRequest() {
        return new SupprimerLivreRequest();
    }

    /**
     * Create an instance of {@link SupprimerLivreResponse }
     * 
     */
    public SupprimerLivreResponse createSupprimerLivreResponse() {
        return new SupprimerLivreResponse();
    }

    /**
     * Create an instance of {@link PreterLivreRequest }
     * 
     */
    public PreterLivreRequest createPreterLivreRequest() {
        return new PreterLivreRequest();
    }

    /**
     * Create an instance of {@link PreterLivreResponse }
     * 
     */
    public PreterLivreResponse createPreterLivreResponse() {
        return new PreterLivreResponse();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link RetournerLivreRequest }
     * 
     */
    public RetournerLivreRequest createRetournerLivreRequest() {
        return new RetournerLivreRequest();
    }

    /**
     * Create an instance of {@link RetournerLivreResponse }
     * 
     */
    public RetournerLivreResponse createRetournerLivreResponse() {
        return new RetournerLivreResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

}
