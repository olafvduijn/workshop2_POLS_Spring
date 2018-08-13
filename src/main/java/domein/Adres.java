package domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import domein.*;

@Entity
@org.hibernate.annotations.DynamicInsert 
@org.hibernate.annotations.DynamicUpdate
@Table(name="Adres")
public class Adres {
	
	public enum AdresType {POSTADRES, FACTUURADRES, BEZORGADRES;
		public static AdresType toAdresType(String adrestype) {
			if (adrestype.equals("postadres")||adrestype.equals("POSTADRES")) {
				return AdresType.POSTADRES;
			}
			else if (adrestype.equals("bezorgadres")||adrestype.equals("BEZORGADRES")) {
				return AdresType.BEZORGADRES;
			}
			else if (adrestype.equals("factuuradres")||adrestype.equals("FACTUURADRES")) {
				return AdresType.FACTUURADRES;
			}
			else return null;
		}
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="Adres_Id")
	private int id;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
	private AdresType adresType;
	private String straatnaam;
	private int huisnummer;
	private String toevoeging;
	private String postcode;
	private String woonplaats;
	@ManyToOne
	private Klant klant;
	

	public Adres () {
		
	}
	
	public Adres (AdresType adresType, String straatnaam, int huisnummer, String postcode, String woonplaats, Klant klant) {
		this.adresType=adresType;
		this.straatnaam=straatnaam;
		this.huisnummer=huisnummer;
		this.toevoeging="";
		this.postcode=postcode;
		this.woonplaats=woonplaats;
		this.klant=klant;
				
	}
	
	public Adres (AdresType adresType, String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats, Klant klant) {
		this.adresType=adresType;
		this.straatnaam=straatnaam;
		this.huisnummer=huisnummer;
		this.toevoeging=toevoeging;
		this.postcode=postcode;
		this.woonplaats=woonplaats;
		this.klant=klant;
		
	}

	public void setStraatnaam(String straatnaam) {
		this.straatnaam=straatnaam;
	}
	public void sethuisnummer(int huisnummer) {
		this.huisnummer=huisnummer;
	}
	public void setToevoeging(String toevoeging) {
		this.toevoeging=toevoeging;
	}
	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}
	public void setWoonplaats(String woonplaats) {
		this.woonplaats=woonplaats;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}	
	
	public String getStraatnaam() {
		return this.straatnaam;
	}
	public int getHuisnummer() {
		return this.huisnummer;
	}
	public String getToevoeging() {
		return this.toevoeging;
	}
	public String getPostcode () {
		return this.postcode;
	}
	public String getWoonplaats() {
		return this.woonplaats;
	}
	
	public AdresType getAdresType() {
		return this.adresType;
	}
	
	public void setAdresType(AdresType adresType) {
		this.adresType=adresType;
	}
	
	public Klant getKlant() {
		return klant;
	}
	
	public void setKlant(Klant klant) {
		this.klant=klant;
	}
	
	public boolean equals(Adres adres) {
		if (this.id!=adres.getId()) {
			return false; 
		}
		if (!this.straatnaam.equals(adres.getStraatnaam())) {
			return false; 
		}
		if (this.huisnummer!=adres.getHuisnummer()) {
			return false; 
		}
		if (!this.toevoeging.equals(adres.getToevoeging())) {
			return false; 
		}
		if (!this.postcode.equals(adres.getPostcode())) {
			return false; 
		}
		if (!this.woonplaats.equals(adres.getWoonplaats())) {
			return false; 
		}
		if (!this.adresType.equals(adres.getAdresType())) {
			return false;
		}
		return true;
	}
}
