// TO DO: BigDecimal

package domein;

import java.math.BigDecimal;

public class BestelRegel {
	
	private int id;
	private Artikel artikel;
	
	private int aantal;
	private BigDecimal prijs;
//	private Bestelling bestelling;//bestelling updaten als bestelregel aangepast wordt of verwijdert wordt
	private int bestellingId;
	/**
	 * Creëert een extra bestelregel voor een bestelling op basis van het gewenste artikel en aantal.
	 * Berekent tevens de prijs voor deze bestelregel.
	 * @param artikel het artikel dat besteld moet worden
	 * @param aantal het aantal stuks van het artikel dat besteld moet worden
	 */
/*	public BestelRegel ( Artikel artikel, int aantal) {
		this.artikel=artikel;
		this.aantal=aantal;
		
	}
*/	
	public BestelRegel ( int aantal ,int bestellingId, Artikel artikel ) {
		this.artikel=artikel;
		this.setBestellingId(bestellingId);
		this.aantal=aantal;
		
	}
	public BestelRegel ( int aantal ,int bestellingId, Artikel artikel, BigDecimal totaalprijs ) {
		this.artikel=artikel;
		this.setBestellingId(bestellingId);
		this.aantal=aantal;
		this.prijs=totaalprijs;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setArtikel(Artikel artikel) {
		this.artikel=artikel;
	}

	
	
	// public void setBestelling(Bestelling bestelling) {
	// 	this.bestelling= bestelling;
	// }
	
	public void setAantal(int aantal) {
		this.aantal=aantal;
		
		
	}
	
	public void setPrijs(BigDecimal prijs) {
		this.prijs=prijs;
	}
	
	public int getId() {
		return this.id;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}
	

/*	 public Bestelling getBestelling() {
	 	return this.bestelling;
	 }
	*/
	public int getAantal() {
		return this.aantal;
	}
	
	/**
	 * Haalt de stukprijs op van het artikel en bepaalt de totaalprijs voor deze bestelregel op basis van het aantal
	 * @return de vermenigvuldiging van de stukprijs met het aantal bestelde artikelen
	 */
	public BigDecimal getPrijs() {
		
		return this.prijs;
	}
	
	public boolean equals (BestelRegel bestelregel) {
		if (this.id!=bestelregel.getId()) {
			return false;
		}
		if (!this.artikel.equals(bestelregel.getArtikel())) {
			return false;
		}
		if (this.aantal!=bestelregel.getAantal()) {
			return false;
		}
		if (!this.prijs.equals(bestelregel.getPrijs())) {
			return false;
		}
		return true;
	}

	public int getBestellingId() {
		return bestellingId;
	}

	public void setBestellingId(int bestellingId) {
		this.bestellingId = bestellingId;
	}
	
}
