package domein;

import java.util.ArrayList;

import domein.Adres.AdresType;

public class Klant {
	
	private int id;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private int accountId; // foreign key opgenomen om het leven makkelijk te maken
	private ArrayList <Bestelling> bestellingen= new ArrayList <Bestelling>();
	private boolean postadres=false;
	private boolean factuuradres=false;
	private boolean bezorgadres=false;
	

	/**
	 * Creëert een nieuwe klant inclusief een nieuw adres van het adrestype postadres als tussenvoegsel is ingegeven
	 * @param voornaam de voornaam van de nieuwe klant
	 * @param tussenvoegsel het tussenvoegsel van de nieuwe klant
	 * @param achternaam de achternaam van de nieuwe klant
	 * @param postadres het vaste adres van de nieuwe klant
	 */
	public Klant(String voornaam, String tussenvoegsel, String achternaam, Adres postadres) {
		this.voornaam=voornaam;
		this.tussenvoegsel=tussenvoegsel;
		this.achternaam=achternaam;
		this.postadres=true;
	}
	
	/**
	 * Creëert een nieuwe klant inclusief een nieuw adres van het adrestype postadres als er geen tussenvoegsel is ingegeven
	 * @param voornaam de voornaam van de nieuwe klant
	 * @param achternaam de achternaam van de nieuwe klant
	 * @param postadres het vaste adres van de nieuwe klant
	 */
	public Klant(String voornaam, String achternaam, Adres postadres) {
		this.voornaam=voornaam;
		this.tussenvoegsel=null;
		this.achternaam=achternaam;
		this.postadres=true;
	}

	/**
	 * Creëert een nieuwe klant zonder een nieuw adres 
	 * @param voornaam de voornaam van de nieuwe klant
	 * @param tussenvoegsel
	 * @param achternaam de achternaam van de nieuwe klant
	 */
	public Klant(String voornaam, String tussenvoegsel, String achternaam, int accountid) {
		this.voornaam=voornaam;
		this.tussenvoegsel=tussenvoegsel;
		this.achternaam=achternaam;
		this.accountId=accountid;
	}
	
	public Klant(String voornaam, String achternaam, int accountid) {
		this.voornaam=voornaam;
		this.tussenvoegsel=null;
		this.achternaam=achternaam;
		this.accountId=accountid;

	}
	
	public void setId(int id) {
		this.id=id;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam=voornaam;
	}
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel=tussenvoegsel;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam=achternaam;
	}
	
	public void setPostadres (boolean heeftPostadres) {
		this.postadres=heeftPostadres;
	}
	
	public void setFactuuradres (boolean heeftFactuuradres) {
		this.factuuradres=heeftFactuuradres;
	}
	
	public void setBezorgadres (boolean heeftBezorgadres) {
		this.bezorgadres=heeftBezorgadres;
	}

	
	public int getId() {
		return this.id;
	}
	public String getVoornaam() {
		return this.voornaam;
	}
	public String getTussenvoegsel() {
		return this.tussenvoegsel;
	}
	public String getAchternaam() {
		return this.achternaam;
	}
	public int getAccountId() {
		return this.accountId;
	}
	
	public boolean getPostadres() {
		return this.postadres;
	}
	
	public boolean getFactuuradres() {
		return this.factuuradres;
	}
	
	public boolean getBezorgadres() {
		return this.bezorgadres;
	}
	

	
	

	

	

	
	public void maakBestellingAan() {
		Bestelling bestelling=new Bestelling();
		bestellingen.add(bestelling);
	}
	
	public void verwijderBestelling(int index) {
		bestellingen.remove(index);
	}
	
	// Moet hier nog een wijzigBestelling, waarschijnlijk niet?!?
	
	public ArrayList <Bestelling> leesAlleBestellingen() {
		return bestellingen;
	}
	
	public Bestelling getBestelling(int index) {
		return bestellingen.get(index);
	}

	
	
	
	public boolean equals(Klant klant) {
		if (this.id!=klant.getId()) {
			return false;
		}
		if (!this.voornaam.equals(klant.getVoornaam())){
			return false;
		}
		if (!this.tussenvoegsel.equals(klant.getTussenvoegsel())){
			return false;
		}
		if (!this.achternaam.equals(klant.getAchternaam())){
			return false;
		}
		if (this.postadres!=klant.getPostadres()) {
			return false;
		}
		if (this.factuuradres!=klant.getFactuuradres()) {
			return false;
		}
		if (this.bezorgadres!=klant.getBezorgadres()) {
			return false;
		}
		return true;
	}

	
}
