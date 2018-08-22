package Controllers;

import data.AdresDAOImpl;
import data.KlantDAOImpl;
import domein.Adres;
import domein.Adres.AdresType;
import domein.Klant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdresController {

    @Autowired
    private AdresDAOImpl adresDao;
    @Autowired
    private KlantDAOImpl klantDao;
    Klant klant;
    Adres adres;

    public AdresController() {
//        klantDao = new KlantDAOImpl(EntityManagerPols.em, Klant.class);
    }

    public void setKlant(int klantId) {
        klant = klantDao.findById(klantId);
    }

    public boolean factuurAdresAanwezig() {
        return adresDao.factuurAdresAanwezig(klant.getId());
    }

    public boolean BezorgAdresAanwezig() {
        return adresDao.bezorgAdresAanwezig(klant.getId());
    }

    public String toonPostadres() {
        return adresDao.toonPostadres(klant.getId());
    }

    public String toonFactuuradres() {
        return adresDao.toonFactuuradres(klant.getId());
    }

    public String toonBezorgadres() {
        return adresDao.toonBezorgadres(klant.getId());
    }

    public void setPostadres() {
        adres = adresDao.getAdres(klant.getId(), AdresType.POSTADRES);
    }

    public void setFactuuradres() {
        adres = adresDao.getAdres(klant.getId(), AdresType.FACTUURADRES);
    }

    public void setBezorgadres() {
        adres = adresDao.getAdres(klant.getId(), AdresType.BEZORGADRES);
    }

    public void wijzigStraat(String nieuweStraat) {
        adres.setStraatnaam(nieuweStraat);
        System.out.println("huisnummer: " + adres.getHuisnummer());
        adresDao.update(adres);
    }

    public void wijzigHuisnummer(int nieuwHuisnummer) {
        adres.sethuisnummer(nieuwHuisnummer);
        adresDao.update(adres);
    }

    public void wijzigToevoeging(Object nieuweToevoeging) {
        if (nieuweToevoeging.equals(null)) {
            adres.setToevoeging(null);
        } else {
            adres.setToevoeging(nieuweToevoeging.toString());
        }
        adresDao.update(adres);

    }

    public void wijzigPostcode(String nieuwePostcode) {
        adres.setPostcode(nieuwePostcode);
        adresDao.update(adres);

    }

    public void wijzigPlaats(String nieuwePlaats) {
        adres.setWoonplaats(nieuwePlaats);
        adresDao.update(adres);
    }

    public void verwijderAdres() {
        adresDao.delete(adres);

    }

    public void maakFactuurAdres(String straatnaam, int huisnummer, String toevoeging, String postcode, String plaats) {
        adres = new Adres(AdresType.FACTUURADRES, straatnaam, huisnummer, toevoeging, postcode, plaats, klant);
        adresDao.create(adres);

    }

    public void maakBezorgAdres(String straatnaam, int huisnummer, String toevoeging, String postcode, String plaats) {
        adres = new Adres(AdresType.BEZORGADRES, straatnaam, huisnummer, toevoeging, postcode, plaats, klant);
        adresDao.create(adres);
    }

}
