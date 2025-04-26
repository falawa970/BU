
package bibliotheque.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="livreId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="livre" type="{http://bibliotheque.ws}livre"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "livreId",
    "livre"
})
@XmlRootElement(name = "modifierLivreRequest")
public class ModifierLivreRequest {

    protected long livreId;
    @XmlElement(required = true)
    protected Livre livre;

    /**
     * Gets the value of the livreId property.
     * 
     */
    public long getLivreId() {
        return livreId;
    }

    /**
     * Sets the value of the livreId property.
     * 
     */
    public void setLivreId(long value) {
        this.livreId = value;
    }

    /**
     * Gets the value of the livre property.
     * 
     * @return
     *     possible object is
     *     {@link Livre }
     *     
     */
    public Livre getLivre() {
        return livre;
    }

    /**
     * Sets the value of the livre property.
     * 
     * @param value
     *     allowed object is
     *     {@link Livre }
     *     
     */
    public void setLivre(Livre value) {
        this.livre = value;
    }

}
