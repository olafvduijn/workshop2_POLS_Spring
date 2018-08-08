package view;
import java.util.Scanner;

import Controllers.AccountController;
import domein.Account;
import domein.Account.Rol;
import validator.Validator;


public class AccountsMenu {
	private  Scanner input = new Scanner(System.in);
	private AccountController accountController;
	private KlantgegevensMenu klantgegevensMenu= new KlantgegevensMenu();
	
	
	public AccountsMenu(){
		accountController = new AccountController();
	}
	
	public void accountsMenu() {
	boolean logout = false;

	// this menu is meant for only the administrator 
	
	
	
	while(!logout) {
		toonAccounts();
		System.out.println("Kies en type in wat u wilt doen?");
		System.out.println( "1 :Maak nieuwe Accounts");
		System.out.println( "2 :Zoek een Account");
		System.out.println( "3 :Pas account aan");	
		System.out.println( "4 :delete account ");		
		System.out.println( "5 :Toon alle klanten ");
		System.out.println( "0 :Terug naar Hoofdmenu");
         
		int actie = input.nextInt();
		input.nextLine();
       switch(actie) {
       
       case 1:
    	   voegAccountToe();

			break;
        case 2:
        	zoekAccount();
        	break;
		case 3:
			System.out.println("Voer het nummer in van het Account dat u wilt aanpassen");
			pasAccountAan(input.nextInt());
			break;
	    case 4:
			System.out.println("Voer het nummer in van het Account dat u wilt Verwijderen");
			deleteAcount(input.nextInt());
				break;
	    case 5:
	    	klantgegevensMenu.klantgegevensMenu();
	    	break;
		case 0:
			logout=true;
			break;
		default:
			System.out.println( "Kies 1 t/m 4");           
       }   	   
	}  	
	}
	public void toonAccounts() {
		System.out.println("Alle accountinformatie: ");		
			for(String s : accountController.getAlleAccounts()){
				System.out.println(s);
			}
		}
	
	
	public void zoekAccount() {
		System.out.println("Vul  Account id  in om te zoeken");
		int id = input.nextInt();

			String accountInfo =accountController.zoekAccount(id);
			System.out.println( "Account gevonden: "+ accountInfo  );
	}
	
	public  void voegAccountToe(){
		String userNaam="";
		while(true) {
			System.out.println("Wat is de Account UserNaam van dit Account?");
			userNaam = input.nextLine();
			if ((Validator.isNaamValid(userNaam))) {
			break;
			}
		}	
		String userPassword="";
		while(true) {
			System.out.println("Wat is de  User Password van dit Account?");
			userPassword = input.nextLine();
			if ((Validator.isNaamValid(userPassword))) {
			break;
			}
		}		
		String roltemp="";
		while(true) {
			System.out.println("Wat is de  User rol van dit Account?");
			roltemp = input.nextLine();
			if (Validator.isRol(roltemp)) {
				break;
			}
		}
		Rol rol =Account.Rol.toRol(roltemp);                                       
		if(accountController.voegAccountToe(userNaam, userPassword, rol)){ 
			System.out.println("Account toegevoegd!");
		}
		else{
			System.err.println("Kon Account niet toevoegen!");
		}
		
	}
	
	public void pasAccountAan(int accountId){
		System.out.println("Wat wilt u aanpassen?");
		System.out.println("1: Account UserNaam aanpassen");
		System.out.println("2: User Paasword aanpassen");
		System.out.println("3: User rol aanpassen");
		System.out.println("4: Annuleer");
		int keuze = input.nextInt();
		switch(keuze){
		case 1:
			pasUserNaamAan(accountId);
			break;
		case 2:
			pasPasswordAan(accountId);
			break;
		case 3:
			pasRolAan(accountId);
			break;
		case 4:
			break;
		default:
			System.out.println("Ongeldige keuze");
			pasAccountAan(accountId);
		}
	}
	
	public void pasUserNaamAan(int Id){
		String userNaam="";
		while(true) {
			System.out.println("Vul nieuwe user naam in");
			userNaam = input.next();
			if ((Validator.isNaamValid(userNaam))) {
			break;
			}
		}	
		if(accountController.pasUserNaamAan(Id, userNaam)){
			System.out.println("UserNaam aangepast!");
		}
		else{
			System.err.println("UserNaam kon niet aangepast worden!");
		}
		
	}
	
	public void pasPasswordAan(int accountId){
		String userPassword="";
		while(true) {
			System.out.println("Voer het nieuwe password in.");
			userPassword = input.next();
			if ((Validator.isNaamValid(userPassword))) {
			break;
			}
		}		
		if(accountController.pasUserPasswordAan(accountId, userPassword)){
			System.out.println("Password aangepast!");
		}
		else{
			System.err.println("Password kon niet aangepast worden!");
		}
	
	}
	
	public void pasRolAan(int accountId){
		String roltemp="";
		while(true) {
			System.out.println("Vul nieuwe Rol in");
			roltemp = input.next();
			if (Validator.isRol(roltemp)) {
				break;
			}
		}
		Rol rol =Account.Rol.toRol(roltemp);   
		if(accountController.pasRolAan(accountId, rol)){
			System.out.println("Rol aangepast!");
		}
		else{
			System.err.println("Rol kon niet aangepast worden!");
		}
	
	}
	
	public void deleteAcount(int accountId){
		System.out.println("Wat is de Account id om te verwijderen?");
		int Id = input.nextInt();
		if(accountController.deleteAccount(Id)){ 
			System.out.println("Account deleted!");
		}
		else{
			System.err.println("Kon Account niet delete worden!");
		}
	
	}
		
		
	
	
}
