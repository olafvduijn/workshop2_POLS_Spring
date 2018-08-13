package dataOld;

import dataMongoDB.AccountDaoMongoImplement;
import dataMongoDB.AdresDaoMongoImplement;
import dataMongoDB.ArtikelDaoMongoImplement;
import dataMongoDB.BestellingDaoMongoImplement;
import dataMongoDB.BestelregelDaoMongoImplement;
import dataMongoDB.KlantDaoMongoImplement;
import dataMySQL.AccountDaoImplement;
import dataMySQL.AdresDaoImplement;
import dataMySQL.ArtikelDaoImplement;
import dataMySQL.BestellingDaoImplement;
import dataMySQL.BestelregelDaoImplement;
import dataMySQL.KlantDaoImplement;

public class DaoFactory {
	
	static boolean databaseMYSQLGebruikt=true;
	
	public static void setDatabaseMYSQL(boolean databaseMYSQLWordtGebruikt) {
		databaseMYSQLGebruikt=databaseMYSQLWordtGebruikt;
	}
	
	
	public static AccountDao getAccountDao() {
		AccountDao accountDao;
		if (databaseMYSQLGebruikt) {
			accountDao= new AccountDaoImplement();
		}
		else {
			accountDao = new AccountDaoMongoImplement();
		}
		return accountDao;
	}
	
	public static AdresDao getAdresDao() {
		AdresDao adresDao;
		if (databaseMYSQLGebruikt) {
			adresDao= new AdresDaoImplement();
		}
		else {
			adresDao = new AdresDaoMongoImplement();
		}
		return adresDao;
	}
	
	public static ArtikelDao getArtikelDao() {
		ArtikelDao artikelDao;
		if (databaseMYSQLGebruikt) {
			artikelDao= new ArtikelDaoImplement();
		}
		else {
			artikelDao = new ArtikelDaoMongoImplement();
		}
		return artikelDao;
	}
	
	public static BestellingDao getBestellingDao() {
		BestellingDao bestellingDao;
		if (databaseMYSQLGebruikt) {
			bestellingDao= new BestellingDaoImplement();
		}
		else {
			bestellingDao = new BestellingDaoMongoImplement();
		}
		return bestellingDao;
	}
	
	public static BestelregelDao getBestelregelDao() {
		BestelregelDao bestelregelDao;
		if (databaseMYSQLGebruikt) {
			bestelregelDao= new BestelregelDaoImplement();
		}
		else {
			bestelregelDao = new BestelregelDaoMongoImplement();
		}
		return bestelregelDao;
	}
	
	public static KlantDao getKlantDao() {
		KlantDao klantDao;
		if (databaseMYSQLGebruikt) {
			klantDao= new KlantDaoImplement();
		}
		else {
			klantDao = new KlantDaoMongoImplement();
		}
		return klantDao;
	}
	
	
	
}
