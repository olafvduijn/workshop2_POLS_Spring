package Controllers;

import data.AdresDao;
import data.DaoFactory;
import data.KlantDao;
import domein.Adres;
import domein.Adres.AdresType;
import domein.Klant;

public class AdresController {
	
	AdresDao adresDao;
	Klant klant;
	Adres adres;
	
	public AdresController() {
		adresDao=DaoFactory.getAdresDao();
	}
	
	public void setKlant(int klantId) {
		KlantDao klantDao=DaoFactory.getKlantDao();
		klant=klantDao.getKlant(klantId);
	}

	public boolean factuurAdresAanwezig()  {
		return adresDao.factuurAdresAanwezig(klant.getId());
	}

	public boolean BezorgAdresAanwezig()  {
		return adresDao.bezorgAdresAanwezig(klant.getId());
	}

	public String toonPostadres()  {
		return adresDao.toonPostadres(klant.getId());
	}

	public String toonFactuuradres()  {
		return adresDao.toonFactuuradres(klant.getId());
	}
	
	public String toonBezorgadres()  {
		return adresDao.toonBezorgadres(klant.getId());
	}

	public void setPostadres()  {
		adres=adresDao.getAdres(klant.getId(), AdresType.POSTADRES);
	}
	
	public void setFactuuradres()  {
		adres=adresDao.getAdres(klant.getId(), AdresType.FACTUURADRES);
	}
	
	public void setBezorgadres()  {
		adres=adresDao.getAdres(klant.getId(), AdresType.BEZORGADRES);
	}

	public void wijzigStraat(String nieuweStraat)  {
		adres.setStraatnaam(nieuweStraat);
		System.out.println("huisnummer: "+adres.getHuisnummer());
		adresDao.updateAdres(adres, adres.getId());
	}

	public void wijzigHuisnummer(int nieuwHuisnummer)  {
		adres.sethuisnummer(nieuwHuisnummer);
		adresDao.updateAdres(adres, adres.getId());
	}

	public void wijzigToevoeging(Object nieuweToevoeging)  {
		if (nieuweToevoeging.equals(null)) {
			adres.setToevoeging(null);
		}
		else {
			adres.setToevoeging(nieuweToevoeging.toString());
		}
		adresDao.updateAdres(adres, adres.getId());
		
	}

	public void wijzigPostcode(String nieuwePostcode)  {
		adres.setPostcode(nieuwePostcode);
		adresDao.updateAdres(adres, adres.getId());
		
	}

	public void wijzigPlaats(String nieuwePlaats)  {
		adres.setWoonplaats(nieuwePlaats);
		adresDao.updateAdres(adres, adres.getId());
	}

	public void verwijderAdres()  {
		adresDao.deleteAdres(adres);
		
	}

	public void maakFactuurAdres(String straatnaam, int huisnummer, String toevoeging, String postcode, String plaats) {
		adres=new Adres (AdresType.FACTUURADRES, straatnaam, huisnummer,toevoeging, postcode, plaats, klant.getId());
		adresDao.createAdres(adres, klant.getId());
		
		
	}

	public void maakBezorgAdres(String straatnaam, int huisnummer, String toevoeging, String postcode, String plaats) {
		adres=new Adres (AdresType.BEZORGADRES, straatnaam, huisnummer,toevoeging, postcode, plaats, klant.getId());
		adresDao.createAdres(adres, klant.getId());
	}

	

}
