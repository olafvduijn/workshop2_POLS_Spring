package domein;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Bestelling {
	
	private int id;
	private BigDecimal totaalPrijs;
//	private Klant klant;	// misschien moet hier Klant klant m waarvan je alleen de Id set zodat later de klant ingevuld kan worden
//	private ArrayList<BestelRegel> bestelregels=new ArrayList<BestelRegel>();
	private int klantId;
	
	
	public Bestelling(int id, BigDecimal totaalPrijs, int  klantId) {
		this.id=id;
		this.totaalPrijs=totaalPrijs;
		this.klantId=klantId;
		
	}	
	
/*	public Bestelling(int id, BigDecimal totaalPrijs, Klant klant) {
		this.id=id;
		this.totaalPrijs=totaalPrijs;
		this.klant=klant;
		
	}	*/
public Bestelling(Klant klant) {
		this.totaalPrijs=new BigDecimal("0.00");
		//this.klant.setId(klantId);
		this.klantId=klant.getId();
	}
	
	
	public Bestelling() {
		
		this.totaalPrijs=new BigDecimal("0.00");
	}
	
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setTotaalPrijs(BigDecimal totaalPrijs) {
		this.totaalPrijs= totaalPrijs;
	}

	
/*	public void bepaalTotaalPrijs() {
		BigDecimal voorlopigePrijs=new BigDecimal("0.00");
		for (int i=0;i<bestelregels.size();i++) {
			BestelRegel huidigeBestelregel=bestelregels.get(i);
			BigDecimal BestelregelPrijs=huidigeBestelregel.getPrijs();
			voorlopigePrijs.add(BestelregelPrijs);
		}
		totaalPrijs=voorlopigePrijs;
	}
*/	
	
	public int getId() {
		return this.id;
	}

	
	public BigDecimal getTotaalPrijs() {
		return this.totaalPrijs;
	}
	
	public int GetKlantId() {
		return this.klantId;
	}
	/**
	 * Voegt aan de bestelling nog een extra regel toe
	 * @param artikel het te bestellen artikel
	 * @param aantal de gewenste hoeveelheid van het te bestellen artikel
	 */
/*	public void voegBestelRegelToe(Artikel artikel, int aantal) {
		BestelRegel nieuweBestelRegel=new BestelRegel (artikel, aantal);
		bestelregels.add(nieuweBestelRegel);
		// als er een extra bestelregel is, levert dat ook een nieuwe totaalprijs op, dus die moet ook opnieuw ingesteld worden
		bepaalTotaalPrijs();
		//artikel.pasvoorraadaan();
	}
*/	
/*	public void verwijderBestelRegel(int index) {
		bestelregels.remove(index);
		// als een bestelregel verwijderd is, levert dat ook een nieuwe totaalprijs op, dus die moet ook opnieuw ingesteld worden
		bepaalTotaalPrijs();
		//artikel.pasvoorraadaan()
	}
*/
/*
	public void wijzigBestelRegel(int index, Artikel artikel, int aantal) {
		BestelRegel aanTePassenBestelRegel=bestelregels.get(index);
		aanTePassenBestelRegel.setAantal(aantal);
		aanTePassenBestelRegel.setArtikel(artikel);
		// als een artikel en/of aantal gewijzigd is, levert dat ook een nieuwe totaalprijs op, dus die moet ook opnieuw ingesteld worden
		bepaalTotaalPrijs();
		//artikel.pasvoorraadaan();
		//artikel.setvoorraad():
	}
*/

	/**
	 * Geeft een totaaloverzicht van alle BestelRegels
	 * @return de ArrayList met alle BestelRegels daarin
	 */
/*	public ArrayList<BestelRegel> leesAlleBestelRegels() {
		return bestelregels;
	}
*/	
	public boolean equals (Bestelling bestelling) {
		if (!this.totaalPrijs.equals(bestelling.getTotaalPrijs())) {
			return false;
		}
		if (this.id!=bestelling.getId()) {
			return false;
		}
/*		for (int index=0; index<bestelregels.size();index++) {
			if (!bestelregels.get(index).equals(bestelling.leesAlleBestelRegels().get(index))) {
				return false;
			}
		}*/
		return true;
	}
	

	
}

