package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import data.ArtikelDAOImpl;
import data.BestellingDAOImpl;
import data.BestelregelDAOImpl;
import domein.Artikel;
import domein.BestelRegel;
import domein.Bestelling;
import view.Menu;

public class BestelregelController {
	private BestelregelDAOImpl bestelregelDao;
	private ArtikelDAOImpl artikelDao;
	private BestellingDAOImpl bestellingdao;
	private ArrayList<Artikel> artikelen;

	public BestelregelController() {
		bestelregelDao = new BestelregelDAOImpl(Menu.em, BestelRegel.class);
		artikelDao = new ArtikelDAOImpl(Menu.em, Artikel.class);
		artikelen = artikelDao.findAll();
		bestellingdao = new BestellingDAOImpl(Menu.em, Bestelling.class);
	}

	public String voegBestelregelToe(int bestellingId, int artikelIndex, int aantal) {
		artikelen = artikelDao.findAll();
		Artikel artikel = artikelen.get(artikelIndex);
		BestelRegel bestelregel = new BestelRegel(aantal, bestellingId, artikel);
		bestelregel.setPrijs(artikel.getPrijs().multiply(new BigDecimal(aantal)));
		if (verlaagVoorraad(artikel, aantal)) {
			if (voorraadVerlagen(artikel, aantal)) {

				BestelRegel b = bestelregelDao.create(bestelregel);
				int id = b.getId();

				if (id > 0) {
					if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {
						;

						return "bestelregel aangemaakt en bestellingprijs bijgewerkt";
					}
					return "Bestelregel aangemaakt bestellingprijs update mislukt";
				}
				return "Bestelregel aanmaken mislukt";
			}
			return "voorraadverlagen mislukt";
		}
		return "voorraad te klein";
	}

	/**
	 * Verlaagt de voorraad mits de gewenste verlaging een positief aantal is en de
	 * verlaging niet groter is dan de huidige voorraad
	 * 
	 * @param aantal
	 *            de hoeveelheid waarmee de voorraad verlaagt moet worden
	 */
	protected boolean verlaagVoorraad(Artikel artikel, int aantal) {
		if (aantal > 0) {
			if ((artikel.getVoorraad() - aantal) < 0) {
				return false;
			}

			artikel.setVoorraad(artikel.getVoorraad() - aantal);
			return true;
		}
		return false;
	}

	protected void verhoogVoorraad(Artikel artikel, int aantal) {
		if (aantal > 0) {
			artikel.setVoorraad(artikel.getVoorraad() + aantal);
		}
	}

	public boolean bestellingTotaalPrijsUpdate(int bestellingId) {
		BigDecimal totaalPrijs = bepaalBestelregelsTotaalprijs(bestellingId);
		try {
			Bestelling bestelling = bestellingdao.findById(bestellingId);
			bestelling.settotaalprijs(totaalPrijs);
			bestellingdao.update(bestelling);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String pasBestelregelAan(int bestelregelId, int aantal, int artikelIndex) {
		artikelen = artikelDao.findAll();
		BestelRegel bestelregel = bestelregelDao.findById(bestelregelId);
		if (bestelregel == null) {
			return "bestelregel niet gevonden";
		}

		Artikel artikeloud = artikelDao.findById(bestelregel.getArtikel().getId());
		verhoogVoorraad(artikeloud, bestelregel.getAantal());
		artikelDao.update(artikeloud);

		Artikel artikelnieuw = artikelen.get(artikelIndex);
		bestelregel.setAantal(aantal);
		bestelregel.setArtikel(artikelnieuw);
		bestelregel.setPrijs(artikelnieuw.getPrijs().multiply(new BigDecimal(aantal)));
		if (verlaagVoorraad(artikelnieuw, aantal)) {
			if (voorraadVerlagen(artikelnieuw, aantal)) {
				bestelregelDao.update(bestelregel);
				if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {
					;
					// update voorraad
					return "bestelregel aangemaakt en bestellingprijs bijgewerkt";
				}
				return "Bestelregel aangemaakt bestellingprijs update mislukt";

			}
			return "voorraad verlagen mislukt";
		}
		return "voorraad te klein";
	}

	public String deleteBestelregel(int bestelregelId) {
		BestelRegel bestelregel = bestelregelDao.findById(bestelregelId);
		if (bestelregel == null) {
			return "bestelregel niet gevonden";
		}

		Artikel artikel = artikelDao.findById(bestelregel.getArtikel().getId());
		verhoogVoorraad(artikel, bestelregel.getAantal());
		artikelDao.update(artikel);

		int bestellingId = bestelregel.getBestellingId();
		if (bestelregelDao.delete(bestelregel)) {
			if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {
				// update voorraad
				return "bestelregel  verwijderd en bestellingprijs bijgewerkt";
			}
			return "Bestelregel verwijderd bestellingprijs update mislukt";
		}
		return "Bestelregel verwijderen mislukt";
	}

	public String zoekBestelregel(int bestelregelId, int bestellingId) {
		BestelRegel bestelregel = bestelregelDao.findById(bestelregelId);
		if (bestelregel == null) {
			return null;
		}
		if (bestelregel.getBestellingId() != bestellingId) {
			return null;
		}
		return (bestelregel.getId() + ": " + bestelregel.getAantal() + "‚ " + bestelregel.getPrijs() + ", "
				+ bestelregel.getBestellingId() + " " + bestelregel.getArtikel().getId());

	}

	public String[] getAlleBestelregels() {
		ArrayList<BestelRegel> bestelregels = bestelregelDao.findAll();
		String[] returnArray = new String[bestelregels.size()];
		for (int i = 0; i < bestelregels.size(); i++) {
			BestelRegel bestelregel = bestelregels.get(i);
			returnArray[i] = (bestelregel.getId() + ": " + bestelregel.getAantal() + "‚ " + bestelregel.getPrijs()
					+ ", " + bestelregel.getBestellingId() + " " + bestelregel.getArtikel().getId());
		}
		return returnArray;
	}

	public String[] zoekBestelregelsPerBestelling(int bestellingId) {
		ArrayList<BestelRegel> bestelregels = bestelregelDao.getAlleBestelregelsPerBestelling(bestellingId);
		String[] returnArray = new String[bestelregels.size()];
		for (int i = 0; i < bestelregels.size(); i++) {
			BestelRegel b = bestelregels.get(i);
			returnArray[i] = ("id: " + b.getId() + " aantal: " + b.getAantal() + "prijs: euro " + b.getPrijs()
					+ ",bestellingId: " + b.getBestellingId() + ", artikelId: " + b.getArtikel().getId());
		}
		return returnArray;
	}

	public BigDecimal bepaalBestelregelsTotaalprijs(int bestellingId) {
		ArrayList<BestelRegel> bestelregels = bestelregelDao.getAlleBestelregelsPerBestelling(bestellingId);
		BigDecimal totaalprijs = new BigDecimal("0.00");
		for (int i = 0; i < bestelregels.size(); i++) {
			totaalprijs = totaalprijs.add(bestelregels.get(i).getPrijs());

		}
		return totaalprijs;
	}

	public boolean voorraadVerlagen(Artikel artikel, int aantal) {
		verlaagVoorraad(artikel, aantal);
		try {
			artikelDao.update(artikel);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean voorraadVerhogen(Artikel artikel, int aantal) {
		verhoogVoorraad(artikel, aantal);
		try {
			artikelDao.update(artikel);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
