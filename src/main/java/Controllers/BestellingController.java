package Controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import data.BestellingDAOImpl;
import data.KlantDAOImpl;
import domein.Bestelling;
import domein.Klant;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class BestellingController {

    @Autowired
    private BestellingDAOImpl bestellingDao;
    @Autowired
    private KlantDAOImpl klantDao;

    public BestellingController() {
//        bestellingDao = new BestellingDAOImpl(EntityManagerPols.em, Bestelling.class);
//        klantDao = new KlantDAOImpl(EntityManagerPols.em, Klant.class);

    }

    public int voegBestellingToe(int klantId) {
        Klant klant = klantDao.findById(klantId);
        Bestelling bestelling = bestellingDao.create(new Bestelling(klant));
        return bestelling.getId();
    }

    public boolean deleteBestelling(int bestellingId, int klantId) {
        Klant klant = klantDao.findById(klantId);
        Bestelling bestelling = bestellingDao.findById(bestellingId);
        if (bestelling == null) {
            return false;
        }
        return bestellingDao.delete(bestelling);
    }

    public String zoekBestelling(int bestellingId, int klantId) {
        Klant klant = klantDao.findById(klantId);
        Bestelling bestelling = bestellingDao.findById(bestellingId);
        if (bestelling == null) {
            return "bestelling niet gevonden ";
        }
        if (bestelling.GetKlantId() != klantId) {
            return "bestellingId is niet van huidige klant ";
        }
        return ("bestellingnummer: " + bestelling.getId() + " totaalprijs: " + bestelling.gettotaalprijs()
                + "‚ klantnummer: " + bestelling.GetKlantId());

    }

    public String[] zoekBestellingenPerKlant(int klantId) {
        Klant klant = klantDao.findById(klantId);
        ArrayList<Bestelling> bestellingen = bestellingDao.getAlleBestellingenPerKlant(klant);
        String[] returnArray = new String[bestellingen.size()];
        for (int i = 0; i < bestellingen.size(); i++) {
            Bestelling b = bestellingen.get(i);
            returnArray[i] = "bestellingnummer: " + b.getId() + " prijs: " + b.gettotaalprijs() + " , klantnummer: "
                    + b.GetKlantId();
        }
        return returnArray;
    }

    public boolean isBestaandeBestelling(int bestellingId) {
        if (bestellingDao.findById(bestellingId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public String[] getAlleBestelling() {
        ArrayList<Bestelling> bestellingen = bestellingDao.findAll();
        String[] returnArray = new String[bestellingen.size()];
        for (int i = 0; i < bestellingen.size(); i++) {
            Bestelling b = bestellingen.get(i);
            returnArray[i] = "bestellingnummer: " + b.getId() + " prijs: " + b.gettotaalprijs() + " , klantnummer: "
                    + b.GetKlantId();
        }
        return returnArray;
    }

}
