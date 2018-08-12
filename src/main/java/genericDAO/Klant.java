package genericDAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Klant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 45)
    @ColumnDefault("null")
    private String voornaam;
    
    @Column(length = 45)
    @ColumnDefault("null")
    private String tussenvoegsel;
    
    @Column(length = 45)
    @ColumnDefault("null")
    private String achternaam;
    
    @Column(name = "account_id")
    private int accountId; // foreign key opgenomen om het leven makkelijk te maken

    public Klant() {
    }

    /**
     * Creëert een nieuwe klant zonder een nieuw adres
     *
     * @param voornaam de voornaam van de nieuwe klant
     * @param tussenvoegsel
     * @param achternaam de achternaam van de nieuwe klant
     */
    public Klant(String voornaam, String tussenvoegsel, String achternaam, int accountid) {
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.accountId = accountid;
    }

    public Klant(String voornaam, String achternaam, int accountid) {
        this.voornaam = voornaam;
        this.tussenvoegsel = null;
        this.achternaam = achternaam;
        this.accountId = accountid;

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
     * @return the voornaam
     */
    public String getVoornaam() {
        return voornaam;
    }

    /**
     * @param voornaam the voornaam to set
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    /**
     * @return the tussenvoegsel
     */
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    /**
     * @param tussenvoegsel the tussenvoegsel to set
     */
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    /**
     * @return the achternaam
     */
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * @param achternaam the achternaam to set
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

}
