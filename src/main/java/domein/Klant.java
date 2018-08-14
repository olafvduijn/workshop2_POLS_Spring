package domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    
    @OneToOne    
    private Account account;

	public Klant() {
    }


    public Klant(String voornaam, String tussenvoegsel, String achternaam, Account account) {
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.account = account;
    }

    public Klant(String voornaam, String achternaam, Account account) {
        this.voornaam = voornaam;
        this.tussenvoegsel = null;
        this.achternaam = achternaam;
        this.account = account;

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

    public Account getAccount() {
 		return account;
 	}

 	public void setAccount(Account account) {
 		this.account = account;
 	}
}
