package Controllers;
import java.util.ArrayList;

import data.AdresDao;
import data.DaoFactory;
import data.KlantDao;
import domein.Account;
import domein.Adres;
import domein.Klant;
import domein.Adres.AdresType;

public class KlantController {
	private KlantDao klantDao;
	private AdresDao adresDao;
	
	public KlantController(){
		klantDao = DaoFactory.getKlantDao();
	}
	
	public Klant getKlant(int klantId) {
		Klant klant = klantDao.getKlant(klantId);           
		
		return klant;
	}
	
	public boolean voegKlantToe(String voornaam, String tussenvoegsel, String achternaam,int accountId, String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats){
		Klant klant=new Klant(voornaam, tussenvoegsel, achternaam, accountId);
		Integer id = klantDao.createKlant(klant);
		int klantid=klant.getId();
		Adres adres =new Adres(AdresType.POSTADRES, straatnaam, huisnummer,toevoeging, postcode, woonplaats, klantid);
		adresDao=DaoFactory.getAdresDao();
		adresDao.createAdres(adres, klantid);
		return id > 0;
	}
	
	public boolean pasVoornaamAan(int id, String voornaam){   
		Klant klant = klantDao.getKlant(id);
		if(klant == null){
			return false;
		}
		klant.setVoornaam(voornaam);
		return klantDao.updateKlant(klant);      
	}
	
	public boolean pasTussenvoegselAan(int id, String tussenvoegsel){   
		Klant Klant = klantDao.getKlant(id);
		if(Klant == null){
			return false;
		}
		Klant.setTussenvoegsel(tussenvoegsel);
		return klantDao.updateKlant(Klant);     
	}
	
	public boolean pasAchternaamAan(int id, String achternaam){   
		Klant Klant = klantDao.getKlant(id);
		if(Klant == null){
			return false;
		}
		Klant.setAchternaam(achternaam);
		return klantDao.updateKlant(Klant);      
		
	}
	
	public boolean deleteKlant(int id){
		Klant klant = klantDao.getKlant(id);           
		if(klant == null){
			return false;
		}
		klant.setId(id);
		return klantDao.deleteKlant(klant); 
	}
	
	public String zoekKlant(int id){
		Klant klant = klantDao.getKlant(id);           
		if(klant == null){
			return "";
		}
		klant.setId(id);
		if (klant.getTussenvoegsel()!=null) {
			return (klant.getId() + ": " + klant.getVoornaam()  + klant.getTussenvoegsel() + " " + klant.getAchternaam() + " " + klant.getAccountId());
		}
		else {
			return klant.getId() + ": " + klant.getVoornaam()  + " " + klant.getAchternaam() + " " + klant.getAccountId();
		}
		
		
	}
	
	public String[] getAlleKlanten(){ 
		ArrayList<Klant> klanten = klantDao.getAlleKlanten();
		String[] returnArray = new String[klanten.size()];
		for(int i=0; i<klanten.size(); i++){
			Klant klant = klanten.get(i);
			if (klant.getTussenvoegsel()!=null) {
				returnArray[i] = klant.getId() + ": " + klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam() + " " + "accountId: "+klant.getAccountId();
			}
			else {
				returnArray[i] = klant.getId() + ": " + klant.getVoornaam() + " " + klant.getAchternaam() + " " + "accountId: "+klant.getAccountId();
			}
		}
		return returnArray;
	}
	
	public int getKlantIdvanAccount(Account account) {
		Klant klant=klantDao.getAlleKlantenPerAccount(account.getId());
		return klant.getId();
	}

	public boolean isBestaandeKlant(int klantId) {
		if(zoekKlant(klantId).equals("")){
			return false;
		}
		else return true;
	}
 
	
}
