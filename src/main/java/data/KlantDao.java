package data;

import java.util.ArrayList;

import domein.Klant;

public interface KlantDao {

	public abstract int createKlant(Klant klant);
	public abstract Klant getKlant(int id);
	public abstract ArrayList<Klant> getAlleKlanten();
	public abstract boolean updateKlant(String voornaam, String tussenvoegsel, String achternaam, int accountId, int id);
	public abstract boolean updateKlant(Klant nieuwKlant);
	public abstract boolean deleteKlant(int id);
	public abstract boolean deleteKlant(Klant klant);
	public abstract Klant getAlleKlantenPerAccount(int accountId);
}
