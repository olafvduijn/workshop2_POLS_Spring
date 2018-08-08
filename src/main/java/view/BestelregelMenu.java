package view;

import java.math.BigDecimal;
import java.util.Scanner;

import Controllers.ArtikelController;
import Controllers.BestelregelController;
import validator.Validator;

public class BestelregelMenu {
	private   Scanner input = new Scanner(System.in);
	private  BestelregelController bestelregelController;
	private ArtikelController artikelController;

	
	
	public BestelregelMenu(){
		bestelregelController = new BestelregelController();
		artikelController = new ArtikelController();
	}	
		
	
	public  void bestelregelMenu(int bestellingId) {
	boolean logout = false;	
	while(!logout) {	
			zoekBestelregelsPerBestelling(bestellingId);
			System.out.println("Kies en type in wat u wilt doen?");
			System.out.println( "1 :Voeg Nieuwe bestelregel toe");
			System.out.println( "2 :Selecteer een bestelregel");
			System.out.println( "0 :Terug naar Bestellingenmenu");
			int actie = input.nextInt();
		       switch(actie) {             
		       case 1:   	   
		    		voegBestelregelToe(bestellingId);
					break;
				case 2:
					selecteerBestelregel(bestellingId);
					break;
				
				case 0:
					logout=true;
					break;
				default:
					System.out.println("Ongeldige keuze");
					
				}       	   
			}  	
		 }
	public void zoekBestelregelsPerBestelling(int bestellingId) {
		for(String s : bestelregelController.zoekBestelregelsPerBestelling(bestellingId)){
			System.out.println(s);
		}
	}
	
	public void selecteerBestelregel(int bestellingId){
		System.out.println("Vul Bestelregel Id in");
		int bestelregelId = input.nextInt();
		// zorgen dat het alleen bij deze bestelling mag horen
		String bestelregelInfo = bestelregelController.zoekBestelregel(bestelregelId, bestellingId);
		
			if (bestelregelInfo!=null) {
			System.out.println("Bestelregel gevonden: " + bestelregelInfo);
			System.out.println("Wat wilt u doen?");
			System.out.println( "1 :Pas bestelregel aantal aan");		
			System.out.println( "2 :Verwijder bestelregel"); 
			System.out.println( "3 :terug naar bestelregel menu"); 
			int actie = input.nextInt();
		       switch(actie) {	       
		       case 1:
		    		pasBestelregelAan(bestelregelId);
		    		break;
		    		
		    	case 2:
		    		deleteBestelregel(bestelregelId);
		    		break;
		    	case 3:
		    		break;
		    	default:
					System.out.println( "Kies 1 t/m 3");
					selecteerBestelregel(bestellingId); 
		       }
			}
		    else {
		    	System.out.println("Bestelregel niet gevonden in huidige bestelling");
		       }
		}
		
	
	
	public  void voegBestelregelToe(int bestellingId){
		
		for(String s : artikelController.getAlleArtikelen()){
			System.out.println(s);
		}
		System.out.println("Voer artikel nummer in ");
		int artikelIndex = input.nextInt();
		if (artikelController.isBestaandArtikelnr(artikelIndex)) {
			int aantal=0;
			while(true) {
			System.out.println("Voer het aantal in.");
			aantal = input.nextInt();
			if (Validator.aantalIsPositief(aantal)) {
				break;
				}
			}
			System.out.println(bestelregelController.voegBestelregelToe(bestellingId, artikelIndex, aantal));
		}
		
		
			
		
		}
		
	
	
	
	
	public void pasBestelregelAan(int bestelregelId){
		for(String s : artikelController.getAlleArtikelen()){
			System.out.println(s);
		}
		System.out.println("Voer artikel nummer in "); 
		int artikelIndex = input.nextInt();
		int aantal=0;
		while(true) {
		System.out.println("Voer het nieuwe aantal in.");
		aantal = input.nextInt();
		if (Validator.aantalIsPositief(aantal)) {
			break;
			}
		}
		System.out.println(bestelregelController.pasBestelregelAan(bestelregelId, aantal,artikelIndex));
				
		}
	
	
	
/*	public void pasPrijsAan(int bestellingId){
		System.out.println("Vul nieuwe prijs in");
		BigDecimal prijs = input.nextBigDecimal();
		if(bestellingController.pasPrijsAan(bestellingId, prijs)){
			System.out.println("Prijs aangepast!");
		}
		else{
			System.err.println("Prijs kon niet aangepast worden!");
		}
		zoekBestelling();
	}
*/	
	public void deleteBestelregel(int bestelregelId){

		System.out.println(bestelregelController.deleteBestelregel(bestelregelId));
		
	}
	
	public void printAlleBestelregels(){
		for(String s : bestelregelController.getAlleBestelregels()){
			System.out.println(s);
		}
	}
}
