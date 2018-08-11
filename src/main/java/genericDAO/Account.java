package genericDAO;

import POJO.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Account {

    public enum Rol {
        klant, medewerker, beheerder;

        public static Rol toRol(String rol) {
            String rollowercase = rol.toLowerCase();
            if (rollowercase.equals("beheerder")) {
                return Rol.beheerder;
            }

            if (rollowercase.equals("medewerker")) {
                return Rol.medewerker;
            } else {
                return Rol.klant;
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 45)
    @ColumnDefault("null")
    private String userNaam;

    @Column(length = 60)
    @ColumnDefault("null")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Rol rol;

    @Transient
    private Klant klant;

    public Account() {

    }

    /**
     * Creëert een nieuwe account
     *
     * @param userNaam de gewenste gebruikersnaam om mee in te loggen
     * @param password het gewenste wachtwoord om mee in te loggen
     * @param rol de rol die de betreffende gebruiker krijgt, op basis waarvan
     * diens rechten in de app worden bepaald
     */
    public Account(String userNaam, String password, Rol rol) {
        this.userNaam = userNaam;
        this.password = password;
        this.rol = rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserNaam(String userNaam) {
        this.userNaam = userNaam;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getId() {
        return this.id;
    }

    public String getUserNaam() {
        return this.userNaam;
    }

    public String getPassword() {
        return this.password;
    }

    public Rol getRol() {
        return this.rol;
    }

    public Klant getKlant() {
        return this.klant;
    }

    public void voegKlantToe(Klant klant) {
        this.klant = klant;
    }

//    public void wijzigKlant(String voornaam, String tussenvoegsel, String achternaam) {
//        this.klant.setVoornaam(voornaam);
//        this.klant.setTussenvoegsel(tussenvoegsel);
//        this.klant.setAchternaam(achternaam);
//    }
//    public void wijzigKlant(String voornaam, String achternaam) {
//        this.klant.setVoornaam(voornaam);
//        this.klant.setTussenvoegsel(null);
//        this.klant.setAchternaam(achternaam);
//    }
//    public void verwijderKlant() {
//        this.klant = null;
//    }
    public boolean equals(Account account) {
        if (this.id != account.getId()) {
            return false;
        }
        if (!this.userNaam.equals(account.getUserNaam())) {
            return false;
        }
        if (!this.password.equals(account.getPassword())) {
            return false;
        }
        if (this.rol != account.getRol()) {
            return false;
        }
        if (!this.klant.equals(account.getKlant())) {
            return false;
        }
        return true;
    }

}
