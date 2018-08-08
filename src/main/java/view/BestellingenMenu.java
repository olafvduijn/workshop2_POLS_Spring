package view;

import java.util.Scanner;
import Controllers.BestellingController;
import Controllers.BestelregelController;
import domein.Klant;

import java.math.BigDecimal;


public class BestellingenMenu {
	
	private   Scanner input = new Scanner(System.in);
	private  BestellingController bestellingController;
	private  BestelregelMenu bestelregelMenu = new BestelregelMenu();
	private BestelregelController bestelregelController;
	
	public BestellingenMenu(){
		bestellingController = new BestellingController();
		bestelregelController = new BestelregelController();
	}	
		
	
	public  void bestellingMenu( int klantId) {
	boolean logout = false;	
	while(!logout) {	
			zoekBestellingenPerKlant(klantId);
			System.out.println("Kies en type in wat u wilt doen?");
			System.out.println( "1 :Voeg Nieuwe bestelling toe");
			System.out.println( "2 :Zoek bestelling per bestelling_id");
			System.out.println( "3 :selecteer een bestelling");
			System.out.println( "0 :Terug naar Klantmenu");
			int actie = input.nextInt();
		       switch(actie) {             
		       case 1:   	   
		    		voegBestellingToe(klantId);
					break;
			
				case 2:
					zoekBestelling(klantId);
					break;
				case 3:
					selecteerBestelling(klantId);
					break;
				case 0:
					logout=true;
					break;
				default:
					System.out.println("Ongeldige keuze");
					
				}       	   
			}  	
		 }
	
	public void selecteerBestelling(int klantId) {
		System.out.println("Welk bestellingnummer wilt u selecteren?");
		int bestellingId = input.nextInt();
		if (bestellingController.isBestaandeBestelling(bestellingId)) {
			for(String s : bestelregelController.zoekBestelregelsPerBestelling(bestellingId)){
				System.out.println(s);
			}
			System.out.println("Kies en type in wat u wilt doen?");
			System.out.println( "1 :pas bestelling aan");
			System.out.println( "2 :deletebestelling");		
			System.out.println( "0 :Terug naar bestellingmenu");
			 int actie = input.nextInt();
		       switch(actie) {   
			case 1:
				bestelregelMenu.bestelregelMenu(bestellingId);
				break;
			case 2: 
				deleteBestelling(bestellingId, klantId);
			case 0: 
				break;
		       }
		}
		else {
			System.out.println("Opgegeven bestellingnummer is geen bestaande bestelling.");
		}
		
	}
	public void zoekBestellingenPerKlant(int klantId) {
			for(String s : bestellingController.zoekBestellingenPerKlant(klantId)){
			System.out.println(s);
		}
	}
	
	public void zoekBestelling(int klantId){
		System.out.println("Vul  Bestelling Id in om te zoeken");
		int bestellingId = input.nextInt();
		String bestellingInfo = bestellingController.zoekBestelling(bestellingId, klantId);
		if (bestellingInfo!=null) {
		System.out.println("Bestelling gevonden: " + bestellingInfo);
		}		
	}
	
	public  void voegBestellingToe(int klantId){
		int id=bestellingController.voegBestellingToe(klantId);
		if(id>0){
			System.out.println("Bestelling toegevoegd!");
			BestelregelMenu bestelregelmenu=new BestelregelMenu();
			bestelregelmenu.bestelregelMenu(id);
		}
		else{
			System.err.println("Kon Bestelling niet toevoegen!");
		}
		
	}
	
/*	public void pasBestellingAan(int bestellingId){
		System.out.println("Wat wilt u doen?");
		System.out.println("1: totaal prijs aanpassen");	
		System.out.println("2: Annuleer");
		int keuze = input.nextInt();
		switch(keuze){
		case 1:
			pasPrijsAan(bestellingId);
			break;
		case 2:
			zoekBestelling();
			break;
		default:
			System.out.println("Ongeldige keuze");
			pasBestellingAan(bestellingId);
		}
	}	
	
	public void pasPrijsAan(int bestellingId){
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

	public void deleteBestelling(int bestellingId, int klantId){
		if(bestellingController.deleteBestelling(bestellingId, klantId)){ 
			System.out.println("Bestelling deleted!");
		}
		else{
			System.err.println("Kon Bestelling niet deleten!");
		}
		
	}
	
/*	public void printAlleBestellingen(){
		for(String s : bestellingController.getAlleBestelling()){
			System.out.println(s);
		}
	}
	*/
}
