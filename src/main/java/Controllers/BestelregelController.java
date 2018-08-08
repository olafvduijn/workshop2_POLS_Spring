package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import data.ArtikelDao;
import data.BestellingDao;
import data.BestelregelDao;
import data.DaoFactory;
import domein.Artikel;
import domein.BestelRegel;

public class BestelregelController {
private BestelregelDao bestelregelDao;
private ArtikelDao artikelDao;
private BestellingDao bestellingdao;
private ArrayList<Artikel>artikelen;

	public BestelregelController(){
		bestelregelDao = DaoFactory.getBestelregelDao();
		artikelDao = DaoFactory.getArtikelDao();
		artikelen = artikelDao.getAlleArtikelen();
		bestellingdao= DaoFactory.getBestellingDao();
	}
	
	
	public String voegBestelregelToe(int bestellingId, int artikelIndex, int aantal){
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel =artikelen.get(artikelIndex);
		BestelRegel bestelregel = new BestelRegel(aantal, bestellingId, artikel);
		bestelregel.setPrijs(artikel.getPrijs().multiply(new BigDecimal(aantal)));
		if(artikel.verlaagVoorraad(aantal)) {
			if (voorraadVerlagen(artikel,aantal)) {
					
				Integer id = bestelregelDao.createBestelregel(bestelregel);
				
				if (id>0) {
					if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {;
				 	
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
	
	public boolean bestellingTotaalPrijsUpdate(int bestellingId){
		BigDecimal totaalPrijs = bepaalBestelregelsTotaalprijs(bestellingId);
		if (bestellingdao.updateBestellingen(totaalPrijs, bestellingId)) {
			return true;
		}
		return false;
	}
	
	public String pasBestelregelAan(int bestelregelId,int aantal, int artikelIndex){  
		artikelen = artikelDao.getAlleArtikelen();
		BestelRegel bestelregel = bestelregelDao.getBestelRegel(bestelregelId);
		if(bestelregel == null){
			return "bestelregel niet gevonden";
		}
		
		Artikel artikeloud = artikelDao.getArtikel(bestelregel.getArtikel().getId());
		artikeloud.verhoogVoorraad(bestelregel.getAantal());
		artikelDao.updateArtikel(artikeloud);
		
		
		Artikel artikelnieuw =artikelen.get(artikelIndex);
		bestelregel.setAantal(aantal);
		bestelregel.setArtikel(artikelnieuw);
		bestelregel.setPrijs(artikelnieuw.getPrijs().multiply(new BigDecimal(aantal)));
		if(artikelnieuw.verlaagVoorraad(aantal)) {
			if (voorraadVerlagen(artikelnieuw,aantal)) {
				if(bestelregelDao.updateBestelRegel(bestelregel)) {      
					 if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {;
					 //update voorraad 
					 return "bestelregel aangemaakt en bestellingprijs bijgewerkt";
					 }
					 return "Bestelregel aangemaakt bestellingprijs update mislukt";
				}
				return "Bestelregel aanmaken mislukt";
			}
			return "voorraad verlagen mislukt";
		}
		return "voorraad te klein";
	}
	
	
	
	public String deleteBestelregel(int bestelregelId){
		BestelRegel bestelregel = bestelregelDao.getBestelRegel(bestelregelId);           
		if(bestelregel == null){
			return "bestelregel niet gevonden";
		}
		
		Artikel artikel = artikelDao.getArtikel(bestelregel.getArtikel().getId());
		artikel.verhoogVoorraad(bestelregel.getAantal());
		artikelDao.updateArtikel(artikel);
		
		int bestellingId=bestelregel.getBestellingId();
		if (bestelregelDao.deleteBestelRegel(bestelregel)) {
			if (bestellingTotaalPrijsUpdate(bestelregel.getBestellingId())) {
				//update voorraad
			 return "bestelregel  verwijderd en bestellingprijs bijgewerkt";
			 }
			return "Bestelregel verwijderd bestellingprijs update mislukt";
		}
		return "Bestelregel verwijderen mislukt";
	}

	
	public String zoekBestelregel(int bestelregelId, int bestellingId){
		BestelRegel bestelregel = bestelregelDao.getBestelRegel(bestelregelId);          
		if(bestelregel == null){
			return null;
		}
		if (bestelregel.getBestellingId()!=bestellingId) {
			return null;
		}
		return (bestelregel.getId() + ": " + bestelregel.getAantal() + "‚ " + bestelregel.getPrijs() + ", " + bestelregel.getBestellingId()+ " " + bestelregel.getArtikel().getId());
		
	}
	
	public String[] getAlleBestelregels(){ 
		ArrayList<BestelRegel> bestelregels = bestelregelDao.getAlleBestelRegel();
		String[] returnArray = new String[bestelregels.size()];
		for(int i=0; i<bestelregels.size(); i++){
			BestelRegel bestelregel = bestelregels.get(i);	
			returnArray[i] = (bestelregel.getId() + ": " + bestelregel.getAantal() + "‚ " + bestelregel.getPrijs() + ", " + bestelregel.getBestellingId()+ " " + bestelregel.getArtikel().getId());
				}
		return returnArray;
	}
	public String[] zoekBestelregelsPerBestelling(int bestellingId){		
		ArrayList<BestelRegel> bestelregels = bestelregelDao.getAlleBestelregelsPerBestelling(bestellingId);
		String[] returnArray = new String[bestelregels.size()];
		for(int i=0; i<bestelregels.size(); i++){
			BestelRegel b = bestelregels.get(i);	
			returnArray[i] = ("id: " + b.getId() + " aantal: " + b.getAantal() + "prijs: euro " + b.getPrijs() + ",bestellingId: " + b.getBestellingId()+ ", artikelId: " + b.getArtikel().getId());
		}
		return returnArray;
	}	
	public BigDecimal bepaalBestelregelsTotaalprijs(int bestellingId){		
		ArrayList<BestelRegel> bestelregels = bestelregelDao.getAlleBestelregelsPerBestelling(bestellingId);
		BigDecimal totaalprijs = new BigDecimal ("0.00");
		for(int i=0; i<bestelregels.size(); i++){
			totaalprijs = totaalprijs.add(bestelregels.get(i).getPrijs());
			
		}
		return totaalprijs;
	}	
	
	public boolean voorraadVerlagen(Artikel artikel, int aantal) {
		if(artikelDao.updateArtikel(artikel)) {
				return true;
			};
		return false;
				
	}
	
	public boolean voorraadVerhogen(Artikel artikel, int aantal) {
		artikel.verhoogVoorraad(aantal);
		return(artikelDao.updateArtikel(artikel));
		
	}
	
}
