package genericDAO;

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
public class Artikel {

    //
    // Een straight-forward POJO
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 45)
    @ColumnDefault("null")
    private String naam;
    
    @ColumnDefault("0")
    private BigDecimal prijs;
    
    @ColumnDefault("0")
    private int voorraad;

    public Artikel() {

    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
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
