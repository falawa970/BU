
package bibliotheque.ws;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for livre complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="livre"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="titre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="auteur" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isbn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="anneePublication" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="disponible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="dateCreation" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="dateModification" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "livre", propOrder = {
    "id",
    "titre",
    "auteur",
    "isbn",
    "description",
    "anneePublication",
    "disponible",
    "dateCreation",
    "dateModification"
})
public class Livre {

    protected Long id;
    @XmlElement(required = true)
    protected String titre;
    @XmlElement(required = true)
    protected String auteur;
    protected String isbn;
    protected String description;
    protected Integer anneePublication;
    protected Boolean disponible;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateModification;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the titre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Sets the value of the titre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitre(String value) {
        this.titre = value;
    }

    /**
     * Gets the value of the auteur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Sets the value of the auteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuteur(String value) {
        this.auteur = value;
    }

    /**
     * Gets the value of the isbn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the value of the isbn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsbn(String value) {
        this.isbn = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the anneePublication property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnneePublication() {
        return anneePublication;
    }

    /**
     * Sets the value of the anneePublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnneePublication(Integer value) {
        this.anneePublication = value;
    }

    /**
     * Gets the value of the disponible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisponible() {
        return disponible;
    }

    /**
     * Sets the value of the disponible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisponible(Boolean value) {
        this.disponible = value;
    }

    /**
     * Gets the value of the dateCreation property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreation() {
        return dateCreation;
    }

    /**
     * Sets the value of the dateCreation property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreation(XMLGregorianCalendar value) {
        this.dateCreation = value;
    }

    /**
     * Gets the value of the dateModification property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateModification() {
        return dateModification;
    }

    /**
     * Sets the value of the dateModification property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateModification(XMLGregorianCalendar value) {
        this.dateModification = value;
    }

}
