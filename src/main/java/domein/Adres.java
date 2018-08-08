package domein;

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
	
	private AdresType adresType;
	private String straatnaam;
	private int huisnummer;
	private String toevoeging;
	private String postcode;
	private String woonplaats;
	private int id;
	private int klantid;
	

	public Adres (AdresType adresType, String straatnaam, int huisnummer, String postcode, String woonplaats, int klantid) {
		this.adresType=adresType;
		this.straatnaam=straatnaam;
		this.huisnummer=huisnummer;
		this.toevoeging="";
		this.postcode=postcode;
		this.woonplaats=woonplaats;
		this.klantid=klantid;
				
	}
	
	public Adres (AdresType adresType, String straatnaam, int huisnummer, String toevoeging, String postcode, String woonplaats, int klantid) {
		this.adresType=adresType;
		this.straatnaam=straatnaam;
		this.huisnummer=huisnummer;
		this.toevoeging=toevoeging;
		this.postcode=postcode;
		this.woonplaats=woonplaats;
		this.klantid=klantid;
		
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
	
	public void setKlantid(int id) {
		this.klantid=id;
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
	
	public int getId() {
		return this.id;
	}
	
	public int getKlantId() {
		return this.klantid;
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
