// TO DO: BigDecimal
// TO DO: type verwijderen???
// TO DO: exception voor negatieve prijs

package domein;

import java.math.BigDecimal;

public class Artikel {
	private int id; 
	private String naam;
	private BigDecimal prijs;
	private int voorraad;

	
	
	/**
	 * Creëert een nieuw artikel op basis van opgegeven artikelnaam, het type van het artikel, de artikelprijs en de voorraad
	 * @param naam de naam voor het nieuwe artikel
	 * @param type de naam van het type van het artikel
	 * @param prijs de stukprijs van het artikel, die niet negatief mag zijn
	 * @param voorraad de hoeveelheid artikelen die op voorraad zijn, die niet negatief mag zijn
	 */
	public Artikel(String naam, BigDecimal prijs, int voorraad) {
		if (voorraad>=0) 
		// Hier moet nog aan toegevoegd worden dat prijs ook groter of gelijk aan 0 is
		{
			this.naam=naam;
			this.prijs=prijs;
			this.voorraad=voorraad;
		}
		
	}
	public Artikel(int id) {
		 
		this.id=id;
		
		}
	
	
	
	public void setNaam (String naam) {
		this.naam=naam;
	}
	
	
	public void setId(int id) {
		
		this.id=id;
	}
	
	
	/**
	 * Wijzigt de prijs naar de gewenste stuksprijs van het artikel
	 * @param prijs de nieuwe prijs, die minimaal 0 moet zijn
	 */
	public void setPrijs (BigDecimal prijs) {
		// hier moet nog een if formulering komen om af te dwingen dat de prijs minimaal 0 is
		this.prijs=prijs;
	}
	
	/**
	 * Wijzigt de voorraad naar de gewenste hoeveelheid artikelen
	 * @param voorraad de gewenste nieuwe hoeveelheid, die minimaal 0 moet zijn
	 */
	public void setVoorraad (int voorraad)  {
		this.voorraad=voorraad;
	}

	
	public int getId () {
		return this.id;
	}
	
	
	public String getNaam() {
		return this.naam;
	}
	
	public BigDecimal getPrijs() {
		return this.prijs;
	}
	public int getVoorraad() {
		return this.voorraad;
	}
	
	/**
	 * Verlaagt de voorraad mits de gewenste verlaging een positief aantal is en de verlaging niet groter is dan de huidige voorraad
	 * @param aantal de hoeveelheid waarmee de voorraad verlaagt moet worden
	 */
	public boolean verlaagVoorraad(int aantal)  {
		if (aantal>0) {
			if ((getVoorraad()-aantal)<0) {
				return false;
			}
			
			setVoorraad(getVoorraad()-aantal);
			return true;
		}
		return false;
	}
	
	public void verhoogVoorraad(int aantal)  {
		if (aantal>0) {
			setVoorraad(getVoorraad()+aantal);
		}
	}
	
	public boolean equals (Artikel artikel) {
		if (this.id!=artikel.getId()) {
			return false;
		}
		if (this.voorraad!=artikel.getVoorraad()) {
			return false;
		}
		if (!this.naam.equals(artikel.getNaam())) {
			return false;
		}
		if (!this.prijs.equals(artikel.getPrijs())) {
			return false;
		}
		return true;
		
		
	}

	
}
	
