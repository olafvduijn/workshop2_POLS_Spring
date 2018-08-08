package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import data.BestellingDao;
import data.BestelregelDao;
import data.DaoFactory;
import data.KlantDao;
import domein.BestelRegel;
import domein.Bestelling;
import domein.Klant;

public class BestellingController {
	
private BestellingDao bestellingDao;
private KlantDao klantDao;



	public BestellingController(){
		bestellingDao = DaoFactory.getBestellingDao();
		klantDao = DaoFactory.getKlantDao();
		
	}	
	public int voegBestellingToe(int klantId){
		Klant klant= klantDao.getKlant(klantId);
		Integer id = bestellingDao.createBestelling(new Bestelling(klant));     
		return id;
	}
	


	public boolean deleteBestelling(int bestellingId, int klantId){
		Klant klant= klantDao.getKlant(klantId);
		Bestelling bestelling = bestellingDao.getBestelling(bestellingId);          
		if(bestelling == null){
			return false;
		}
/*		BestelregelDao brdao=DaoFactory.getBestelregelDao();
		ArrayList<BestelRegel> bestelregellijst=brdao.getAlleBestelregelsPerBestelling(bestellingId);
		BestelregelController brContr=new BestelregelController();
		for (int index = bestelregellijst.size(); index>=0;index--) {
			int bestelregelId=bestelregellijst.get(index).getBestellingId();
			brContr.deleteBestelregel(bestelregelId);
		}
*/		
		return bestellingDao.deleteBestellingen(bestelling);
	}
	
	
	public String zoekBestelling(int bestellingId, int klantId){		
	Klant klant= klantDao.getKlant(klantId);
	Bestelling bestelling = bestellingDao.getBestelling(bestellingId);           
	if(bestelling == null){
		return "bestelling niet gevonden ";
	}
	if(bestelling.GetKlantId()!=klantId) {
		return "bestellingId is niet van huidige klant ";
	}
	return ("bestellingnummer: " + bestelling.getId() + " totaalprijs: " + bestelling.getTotaalPrijs() + "‚ klantnummer: " + bestelling.GetKlantId());
	
}
	

	public String[] zoekBestellingenPerKlant(int klantId){	
		Klant klant= klantDao.getKlant(klantId);
		ArrayList<Bestelling> bestellingen = bestellingDao.getAlleBestellingenPerKlant(klant);
		String[] returnArray = new String[bestellingen.size()];
		for(int i=0; i<bestellingen.size(); i++){
			Bestelling b = bestellingen.get(i);	
			returnArray[i] = "bestellingnummer: " + b.getId() + " prijs: " + b.getTotaalPrijs()+ " , klantnummer: " + b.GetKlantId(); 
		}
		return returnArray;
	}
	
	public boolean isBestaandeBestelling(int bestellingId) {
		if(bestellingDao.getBestelling(bestellingId)!=null) {
			return true;
		}
		else return false;
	}
	
	
	
	public String[] getAlleBestelling(){ 
		ArrayList <Bestelling> bestellingen = bestellingDao.getAlleBestelling();
		String[] returnArray = new String[bestellingen.size()];
		for(int i=0; i<bestellingen.size(); i++){
			Bestelling b = bestellingen.get(i);	
			returnArray[i] = "bestellingnummer: " + b.getId() + " prijs: " + b.getTotaalPrijs()+ " , klantnummer: " + b.GetKlantId(); 
		}
		return returnArray;
	}

}
