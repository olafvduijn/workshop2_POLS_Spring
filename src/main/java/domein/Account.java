package domein;

import domein.Account.Rol;

public class Account {
	
	public enum Rol {klant, medewerker, beheerder;
		public static Rol toRol (String rol) {
			String rollowercase= rol.toLowerCase()	;
		if (rollowercase.equals("beheerder")) {	
			return Account.Rol.beheerder;
		}
		
		if (rollowercase.equals("medewerker")){
			return Account.Rol.medewerker;
		}
		else return Account.Rol.klant;
	}}
	
	private int id;
	private String userNaam;
	private String password;
	private Rol rol;
	private Klant klant;
	
	/**
	 * Creëert een nieuwe account
	 * @param userNaam de gewenste gebruikersnaam om mee in te loggen
	 * @param password het gewenste wachtwoord om mee in te loggen
	 * @param rol de rol die de betreffende gebruiker krijgt, op basis waarvan diens rechten in de app worden bepaald
	 */

	
	public Account( String userNaam, String password, Rol rol) {
		this.userNaam=userNaam;
		this.password=password;
		this.rol=rol;  
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setUserNaam(String userNaam) {
		this.userNaam=userNaam;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public void setRol(Rol rol) {
		this.rol=rol;
	}
	
	
	
	public int getId() {
		return this.id;
	}
	
	
	public String getUserNaam() {
		return this.userNaam;
	}
	public String getPassword() {
		return this.password;
	}
	public Rol getRol() {
		return this.rol;
	}
	public Klant getKlant() {
		return this.klant;
	}
	
	public void voegKlantToe(Klant klant) {
		this.klant=klant;
	}
	
	public void wijzigKlant(String voornaam, String tussenvoegsel, String achternaam) {
		this.klant.setVoornaam(voornaam);
		this.klant.setTussenvoegsel(tussenvoegsel);
		this.klant.setAchternaam(achternaam); 
	}
	
	public void wijzigKlant(String voornaam, String achternaam) {
		this.klant.setVoornaam(voornaam);
		this.klant.setTussenvoegsel(null);
		this.klant.setAchternaam(achternaam);
	}
	
	public void verwijderKlant() {
		this.klant=null;
	}
	
	public boolean equals (Account account) {
		if (this.id!=account.getId()) {
			return false; 
		}
		if (!this.userNaam.equals(account.getUserNaam())) {
			return false; 
		}
		if (!this.password.equals(account.getPassword())) {
			return false; 
		}
		if (this.rol!=account.getRol()) {
			return false; 
		}
		if (!this.klant.equals(account.getKlant())) {
			return false; 
		}
		return true;
	}


	
}
