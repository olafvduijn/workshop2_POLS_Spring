package data;

import domein.Adres;
import domein.Adres.AdresType;

public interface AdresDao {
	
	public abstract  boolean createAdres(Adres adres, int klantid);
	public  abstract Adres getAdres(int klantid, AdresType adrestype);
	public abstract boolean updateAdres(Adres gewijzigdAdres, int adresId);
	public abstract boolean deleteAdres(int id);
	public abstract boolean deleteAdres(Adres teVerwijderenAdres);
	public abstract boolean factuurAdresAanwezig(int klantid);
	public abstract boolean bezorgAdresAanwezig(int klantid);
	public abstract String toonPostadres(int klantId);
	public abstract String toonFactuuradres(int klantId);
	public abstract String toonBezorgadres(int klantId);
}
