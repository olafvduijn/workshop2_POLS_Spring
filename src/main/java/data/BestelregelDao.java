package data;

import java.util.ArrayList;

import domein.BestelRegel;

public interface BestelregelDao {

	public abstract int createBestelregel(BestelRegel bestelregel);
	public abstract BestelRegel getBestelRegel(int id);
	public abstract ArrayList<BestelRegel> getAlleBestelRegel();
	public abstract boolean updateBestelRegel(BestelRegel bestelregel);
	public abstract boolean deleteBestelRegel(int id);
	public abstract boolean deleteBestelRegel(BestelRegel bestelregel);
	public abstract ArrayList<BestelRegel> getAlleBestelregelsPerBestelling(int bestellingId);
}
