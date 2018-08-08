package data;

import java.math.BigDecimal;
import java.util.ArrayList;

import domein.Artikel;

public interface ArtikelDao {

	public abstract int createArtikel(Artikel artikel);
	public abstract Artikel getArtikel(int id);
	public abstract ArrayList<Artikel> getAlleArtikelen();
	public abstract boolean updateArtikel(String naam, BigDecimal prijs , int voorraad, int id);
	public abstract boolean updateArtikel(Artikel nieuwArtikel);
	public abstract boolean deleteArtikel(int id);
	public abstract boolean deleteArtikel(Artikel artikel);
}
