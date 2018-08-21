package Controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import data.AccountDAOImpl;
import data.AdresDAOImpl;
import data.KlantDAOImpl;
import domein.Account;
import domein.Adres;
import domein.Klant;
import domein.Adres.AdresType;
import utility.EntityManagerPols;

@Component
public class KlantController {
	
    private AccountDAOImpl accountDao;
	
    private KlantDAOImpl klantDao;
	
    private AdresDAOImpl adresDao;

    public KlantController() {
    	accountDao = new AccountDAOImpl();
        klantDao = new KlantDAOImpl(EntityManagerPols.em, Klant.class);
        adresDao = new AdresDAOImpl(EntityManagerPols.em, Adres.class);
    }

    public Klant getKlant(int klantId) {
        Klant klant = klantDao.findById(klantId);

        return klant;
    }

    public boolean voegKlantToe(String voornaam, String tussenvoegsel, String achternaam, int accountId, String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats) {
        Account account = accountDao.findById(accountId);
        Klant klant = new Klant(voornaam, tussenvoegsel, achternaam, account);
        klant = klantDao.create(klant);
        //int klantid=klant.getId();
        Adres adres = new Adres(AdresType.POSTADRES, straatnaam, huisnummer, toevoeging, postcode, woonplaats, klant);
        adresDao.create(adres);
        return true;
    }

    public boolean pasVoornaamAan(int id, String voornaam) {
        Klant klant = klantDao.findById(id);
        if (klant == null) {
            return false;
        }
        klant.setVoornaam(voornaam);
        klantDao.update(klant);
        return true;
    }

    public boolean pasTussenvoegselAan(int id, String tussenvoegsel) {
        Klant klant = klantDao.findById(id);
        if (klant == null) {
            return false;
        }
        klant.setTussenvoegsel(tussenvoegsel);
        klantDao.update(klant);
        return true;
    }

    public boolean pasAchternaamAan(int id, String achternaam) {
        Klant klant = klantDao.findById(id);
        if (klant == null) {
            return false;
        }
        klant.setAchternaam(achternaam);
        klantDao.update(klant);
        return true;
    }

    public boolean deleteKlant(int id) {
        Klant klant = klantDao.findById(id);
        if (klant == null) {
            return false;
        }
        klant.setId(id);
        return klantDao.delete(klant);
    }

    public String zoekKlant(int id) {
        Klant klant = klantDao.findById(id);
        if (klant == null) {
            return "";
        }
        klant.setId(id);
        if (klant.getTussenvoegsel() != null) {
            return (klant.getId() + ": " + klant.getVoornaam() + klant.getTussenvoegsel() + " " + klant.getAchternaam() + " " + klant.getAccount().getId());
        } else {
            return klant.getId() + ": " + klant.getVoornaam() + " " + klant.getAchternaam() + " " + klant.getAccount().getId();
        }

    }

    public String[] getAlleKlanten() {
        ArrayList<Klant> klanten = klantDao.findAll();
        String[] returnArray = new String[klanten.size()];
        for (int i = 0; i < klanten.size(); i++) {
            Klant klant = klanten.get(i);
            if (klant.getTussenvoegsel() != null) {
                returnArray[i] = klant.getId() + ": " + klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam() + " " + "accountId: " + klant.getAccount().getId();
            } else {
                returnArray[i] = klant.getId() + ": " + klant.getVoornaam() + " " + klant.getAchternaam() + " " + "accountId: " + klant.getAccount().getId();
            }
        }
        return returnArray;
    }

    public int getKlantIdvanAccount(Account account) {
        Klant klant = klantDao.getAlleKlantenPerAccount(account.getId());
        return klant.getId();
    }

    public boolean isBestaandeKlant(int klantId) {
        if (zoekKlant(klantId).equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
