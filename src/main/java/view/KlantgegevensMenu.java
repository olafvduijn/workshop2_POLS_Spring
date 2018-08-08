package view;
import java.util.Scanner;

import Controllers.AccountController;
import Controllers.KlantController;
import validator.Validator;



public class KlantgegevensMenu {
	private  Scanner input = new Scanner(System.in);
	private KlantController klantController;
	private AccountController accountController;
	private BestellingenMenu bestellingenMenu= new BestellingenMenu();
	
	
	public KlantgegevensMenu(){
		klantController = new KlantController();
		accountController = new AccountController();
	}	
	
	public void klantgegevensMenu() {   
	boolean logout = false;
	
	
	while(!logout) {
		printAlleKlanten();
		System.out.println("Kies en type in wat u wilt doen?");
		System.out.println( "1 :Voeg klantgegevens toe");
		System.out.println( "2 :Selecteer een klant");		
		System.out.println( "3 :Zoek een klant"); 
		System.out.println( "0 :Terug naar Hoofdmenu");
		
	   int actie = input.nextInt();
       switch(actie) {             
       case 1:   	   
    		voegKlantToe();
			break;
		case 2:
			selecteerKlant();
			break;
		case 3:
			zoekKlant();
			break;
		case 0:
			logout=true;
			break;
		default:
			System.out.println("Ongeldige keuze");
		
		}       	   
	}  	
 }
	
	public void selecteerKlant() {
		System.out.println("Welk klantnummer wilt u selecteren?");
		int klantId = input.nextInt();
		if (klantController.isBestaandeKlant(klantId)) {
			System.out.println("Kies en type in wat u wilt doen?");
			System.out.println( "1 :pas klantgegevens aan");
			System.out.println( "2 :deleteklant");		
			System.out.println( "3 :wijzig bestellingen van klant");
			System.out.println( "4 :ga naar adressen van klant"); 
			System.out.println( "0 :Terug naar Hoofdmenu");
			 int actie = input.nextInt();
		       switch(actie) {   
			case 1:
				pasKlantAan(klantId);
				break;
			case 2: 
				deleteKlant(klantId);
				break;
			case 3: 
				bestellingenMenu.bestellingMenu(klantId);
				break;
			case 4:
				AdresMenu adresmenu=new AdresMenu(klantId);
				break;
			case 0:
				break;
			default:
				break;
		       }
		}
		else {
			System.out.println("Opgegeven klantnummer bestaat niet.");
		}
		
	}
	
	public void voegKlantToe(){
		System.out.println("Alle klantinformatie: ");		
		for(String s : accountController.getBeschikbareKlantAccounts()){
			System.out.println(s);
		}
		System.out.println("Welk accountnummer moet een klant aan toegevoegd worden?");
		int accountId = input.nextInt();
		if(accountController.isBestaandAccountId(accountId)) {
			if (accountController.accountIsKlant(accountId)) {
				if (accountController.accountHeeftGeenKlant(accountId)) {
					input.nextLine();
					String voornaam="";
					while(true) {
						System.out.println("Wat is de voornaam van deze klant?");
						voornaam = input.nextLine();
						if ((Validator.isNaamValid(voornaam))) {
							break;
						}
					}	
					System.out.println("Heeft de klant een tussenvoegsel: toets 1");
					System.out.println("Heeft de klant geen tussenvoegsel: toets 0");
					String tussenvoegsel="";
					int tussenvoegselaanwezig = input.nextInt();
					input.nextLine();
					if (tussenvoegselaanwezig==1) {
						System.out.println("Wat is de tussenvoegsel van deze klant?"); 
				
						tussenvoegsel = input.nextLine();
					}
					String achternaam="";
					while(true) {
						System.out.println("Wat is de achternaam van deze klant?");
						achternaam = input.nextLine();
						if ((Validator.isNaamValid(achternaam))) {
							break;
					}
				}	
				String straatnaam="";
				while(true) {
					System.out.println("Wat is de straatnaam van het postadres van deze klant?");
					straatnaam = input.nextLine();
					if ((Validator.isNaamValid(straatnaam))) {
					break;
					}
				}
				System.out.println("Wat is het huisnummer van het postadres van deze klant?");
				int huisnummer=input.nextInt();
				input.nextLine();
				System.out.println("Heeft het postadres van deze klant een toevoeging op het huisnummer: toets 1");
				System.out.println("Heeft het postadres van deze klant geen toevoeging op het huisnummer: toets 0");
				String toevoeging=null;
				int toevoegingAanwezig = input.nextInt();
				input.nextLine();
				if (toevoegingAanwezig==1) {
					System.out.println("Wat is de toevoeging op het huisnummer?");
					toevoeging=input.nextLine();
				}
				String postcode="";
				while(true) {
					System.out.println("Geef de postcode van het adres op");
					postcode=input.nextLine();
					if ((Validator.postcodeIsValid(postcode))) {
					break;
					}
				}
				String woonplaats="";
				while(true) {
					System.out.println("Wat is de woonplaats van het postadres van deze klant?");
					woonplaats = input.nextLine();
					if ((Validator.isNaamValid(woonplaats))) {
					break;
					}
				}
				if(klantController.voegKlantToe(voornaam, tussenvoegsel, achternaam,accountId, straatnaam, huisnummer, toevoeging, postcode, woonplaats)){
					System.out.println("Klant toegevoegd!");
				}
				else{
					System.err.println("Kon klant niet toevoegen!");
				}
				
				}
			
			else
			{
				System.err.println("Account heeft al klantgegevens");
			}
		}
		else {
				System.err.println("Account is geen klant-account");
			}
		}
	}
	
	public void pasKlantAan(int klantId){
		   
		System.out.println("Wat wilt u aanpassen?");
		System.out.println("1: voornaam aanpassen");
		System.out.println("2: tussenvoegsel aanpassen");
		System.out.println("3: achternaam aanpassen");		
		System.out.println("4: Annuleer");
		int keuze = input.nextInt();
		switch(keuze){
		case 1:
			pasVoornaamAan(klantId);
			break;
		case 2:
			pasTussenvoegselAan(klantId);
			break;
		case 3:
			pasAchternaamAan(klantId);
			break;
		
		default:
			System.out.println("Ongeldige keuze");
			pasKlantAan(klantId);
		}
	}
	
	public void pasVoornaamAan(int klantid){
		String voornaam="";
		while(true) {
			System.out.println("Vul nieuwe naam in");
			voornaam=input.nextLine();
			if ((Validator.isNaamValid(voornaam))) {
			break;
			}
		}
		if(klantController.pasVoornaamAan(klantid, voornaam)){ 
			System.out.println("VoorNaam aangepast!");
		}
		else{
			System.err.println("voornaam kon niet aangepast worden!"); 
		}
		
	}
	
	public void pasTussenvoegselAan(int id){
		System.out.println("Vul nieuwe tussenvoegsel in");
		String tussenvoegsel = input.next();
		if(klantController.pasTussenvoegselAan(id, tussenvoegsel)){ 
			System.out.println("Tussenvoegsel aangepast!");
		}
		else{
			System.err.println("Tussenvoegsel kon niet aangepast worden!"); 
		}
	
	}
	
	public void pasAchternaamAan(int id){
		String achternaam="";
		while(true) {
			System.out.println("Vul nieuwe achternaam  in");
			achternaam=input.nextLine();
			if ((Validator.postcodeIsValid(achternaam))) {
			break;
			}
		}
		if(klantController.pasAchternaamAan(id, achternaam)){ 
			System.out.println("achternaam aangepast!");
		}
		else{
			System.err.println("achternaam kon niet aangepast worden!"); 
		}
	
	}
	
	public void zoekKlant(){
		System.out.println("Vul  klant id in te zoeken");
		int klantid = input.nextInt();
		String klantInfo =klantController.zoekKlant(klantid);
		if (klantInfo!=null) {
			System.out.println( "klant gevonden: "+ klantInfo  );
		}
		else {
			System.out.println("Klant niet gevonden");
		};
		
	}
		
	public void deleteKlant(int klantId){
		
		if(klantController.deleteKlant(klantId)){ 
			System.out.println("klant deleted!");
		}
		else{
			System.err.println("Kon Klant niet deleten!");
		}
		
	}
	
	public void printAlleKlanten(){
		for(String s : klantController.getAlleKlanten()){
			System.out.println(s);
		}
	}

}
