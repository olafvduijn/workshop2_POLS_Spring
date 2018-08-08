package data;

import java.math.BigDecimal;
import java.util.ArrayList;

import domein.Bestelling;
import domein.Klant;

public interface BestellingDao {

	public abstract int createBestelling(Bestelling bestelling);
	public abstract Bestelling getBestelling(int bestellingId);
	public abstract boolean updateBestellingen(BigDecimal totaalprijs, int id);
	public abstract boolean updateBestellingen(Bestelling bestelling);
	public abstract boolean deleteBestellingen(int id);
	public abstract boolean deleteBestellingen(Bestelling bestellingen);
	public abstract ArrayList<Bestelling> getAlleBestellingenPerKlant(Klant klant);
	public abstract ArrayList<Bestelling> getAlleBestelling();
}
