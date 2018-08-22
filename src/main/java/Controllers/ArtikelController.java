package Controllers;

import data.ArtikelDAOImpl;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import domein.Artikel;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ArtikelController {

    @Autowired
    private ArtikelDAOImpl artikelDao;
//    private ArrayList<Artikel> artikelen;

    public ArtikelController() {
//        artikelDao = new ArtikelDAOImpl(EntityManagerPols.em, Artikel.class);
//        artikelen = artikelDao.findAll();
    }

    public String[] getAlleArtikelen() {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        String[] returnArray = new String[artikelen.size()];
        for (int i = 0; i < artikelen.size(); i++) {
            Artikel a = artikelen.get(i);
            String inVoorraad = a.getVoorraad() > 0 ? a.getVoorraad() + " in voorraad." : "UITVERKOCHT!";
            returnArray[i] = "nummer: " + i + " naam: " + a.getNaam() + " euro " + a.getPrijs().toPlainString()
                    + " voorraad: " + inVoorraad;
        }
        return returnArray;
    }

    public boolean voegArtikelToe2(Artikel artikel) {
        Artikel newArtikel = artikelDao.create(artikel);
        return newArtikel != null;
    }

    public boolean voegArtikelToe(String naam, BigDecimal prijs, int voorraad) {
        Artikel newArtikel = artikelDao.create(new Artikel(naam, prijs, voorraad));
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        return newArtikel != null;
    }

    public boolean pasNaamAan(int index, String naam) {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        Artikel artikel = artikelen.get(index);
        artikel = artikelDao.findById(artikel.getId());
        if (artikel == null) {
            return false;
        }
        artikel.setNaam(naam);
        artikelDao.update(artikel);
        return artikel.getId() > 0;
    }

    public boolean pasPrijsAan(int index, BigDecimal prijs) {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        Artikel artikel = artikelen.get(index);
        artikel = artikelDao.findById(artikel.getId());
        if (artikel == null) {
            return false;
        }
        artikel.setPrijs(prijs);
        artikelDao.update(artikel);
        return artikel.getId() > 0;
    }

    public boolean pasVoorraadAan(int index, int aantal) {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        Artikel artikel = artikelen.get(index);
        artikel = artikelDao.findById(artikel.getId());
        if (artikel == null) {
            return false;
        }
        artikel.setVoorraad(aantal);
        artikelDao.update(artikel);
        return artikel.getId() > 0;
    }

    public String zoekArtikel(int index) {
         ArrayList<Artikel>artikelen = artikelDao.findAll();
        Artikel artikel = artikelen.get(index);
        artikel = artikelDao.findById(artikel.getId());
        if (artikel == null) {
            return null;
        } else {
            String returnstring = ("" + index + " " + artikel.getNaam() + " " + artikel.getPrijs() + " "
                    + artikel.getVoorraad());
            return returnstring;
        }
    }

    public boolean deleteArtikel(int index) {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        Artikel artikel = artikelen.get(index);
        artikel = artikelDao.findById(artikel.getId());
        if (artikel == null) {
            return false;
        }
        if (artikelDao.delete(artikel)) {
            artikelen = artikelDao.findAll();
            return true;
        }
        return false;
    }

    public boolean isBestaandArtikelnr(int artikelIndex) {
        ArrayList<Artikel> artikelen = artikelDao.findAll();
        if (artikelIndex < artikelen.size()) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal textToBigDecimal(String text) {
        String nieuweText = text.replace(',', '.');
        BigDecimal prijs = new BigDecimal(nieuweText);
        return prijs;
    }

}
