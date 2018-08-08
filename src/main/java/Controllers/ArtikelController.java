package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import domein.Artikel;
import data.ArtikelDao;
import data.DaoFactory;

public class ArtikelController {
private ArtikelDao artikelDao;
private ArrayList<Artikel>artikelen;
	
	public ArtikelController(){
		artikelDao = DaoFactory.getArtikelDao();
		artikelen = artikelDao.getAlleArtikelen();
	}
	
	
	
	public ArtikelController(ArtikelDao artikelDao){
		this.artikelDao=artikelDao;
		
		}  
	
	public String[] getAlleArtikelen(){
		ArrayList<Artikel> artikelen = artikelDao.getAlleArtikelen();
		String[] returnArray = new String[artikelen.size()];
		for(int i=0; i<artikelen.size(); i++){
			Artikel a = artikelen.get(i);
			String inVoorraad = a.getVoorraad() > 0 ? a.getVoorraad() + " in voorraad." : "UITVERKOCHT!";
			returnArray[i] = "nummer: " + i + " naam: " + a.getNaam() + " euro " + a.getPrijs().toPlainString() + " voorraad: " + inVoorraad;
		}
		return returnArray;
	}
	
	public boolean voegArtikelToe2(Artikel artikel){
		Integer id = artikelDao.createArtikel(artikel);	
		return id > 0;
	} 
	
	public boolean voegArtikelToe(String naam, BigDecimal prijs, int voorraad){
		Integer id = artikelDao.createArtikel(new Artikel(naam, prijs, voorraad));
		artikelen = artikelDao.getAlleArtikelen();
		return id > 0;
	}
	
	public boolean pasNaamAan(int index, String naam){
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel = artikelen.get(index);
		artikel = artikelDao.getArtikel(artikel.getId());
		if(artikel == null){
			return false;
		}
		artikel.setNaam(naam);
		return artikelDao.updateArtikel(artikel);
	}
	
	public boolean pasPrijsAan(int index, BigDecimal prijs){
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel = artikelen.get(index);
		artikel = artikelDao.getArtikel(artikel.getId());
		if(artikel == null){
			return false;
		}
		artikel.setPrijs(prijs);
		return artikelDao.updateArtikel(artikel);
	}
	
	public boolean pasVoorraadAan(int index, int aantal){
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel = artikelen.get(index);
		artikel = artikelDao.getArtikel(artikel.getId());
		if(artikel == null){
			return false;
		}
		artikel.setVoorraad(aantal);
		return artikelDao.updateArtikel(artikel);
	}


	public String zoekArtikel(int index) {
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel = artikelen.get(index);
		artikel = artikelDao.getArtikel(artikel.getId());
		if (artikel==null) {
			return null;
		}
		else {
		String  returnstring = ("" + index +" "+ artikel.getNaam()+" " + artikel.getPrijs()+" " + artikel.getVoorraad());
		return returnstring;
		}
	}

	public  boolean deleteArtikel(int index){
		artikelen = artikelDao.getAlleArtikelen();
		Artikel artikel = artikelen.get(index);
		artikel = artikelDao.getArtikel(artikel.getId());
		if(artikel == null){
			return false;
		}
		if( artikelDao.deleteArtikel(artikel)) {
			artikelen = artikelDao.getAlleArtikelen();
			return true; 
		}
		return false;
	}



	public boolean isBestaandArtikelnr(int artikelIndex) {
		if (artikelIndex<artikelen.size()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public BigDecimal textToBigDecimal(String text) {
		String nieuweText=text.replace(',','.');
		BigDecimal prijs=new BigDecimal(nieuweText);
		return prijs;
	}
	
	
}
