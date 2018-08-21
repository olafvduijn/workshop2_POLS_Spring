// TO DO: BigDecimal
// TO DO: type verwijderen???
// TO DO: exception voor negatieve prijs
package domein;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author FeniksBV
 */
@Entity
public class Artikel implements Serializable {

    //
    // Een straight-forward POJO
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45)
    @ColumnDefault("null")
    private String naam;

    @ColumnDefault("0")
    private BigDecimal prijs;

    @ColumnDefault("0")
    private int voorraad;

    public Artikel() {

    }

    public Artikel(int id) {
        this.id = id;
    }

    public Artikel(String naam, BigDecimal prijs, int voorraad) {
        this.naam = naam;
        this.prijs = prijs;
        this.voorraad = voorraad;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @param naam the naam to set
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * @return the prijs
     */
    public BigDecimal getPrijs() {
        return prijs;
    }

    /**
     * @param prijs the prijs to set
     */
    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    /**
     * @return the voorraad
     */
    public int getVoorraad() {
        return voorraad;
    }

    /**
     * @param voorraad the voorraad to set
     */
    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

}
