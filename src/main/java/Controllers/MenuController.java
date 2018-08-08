package Controllers;

import data.DaoFactory;
import domein.Account;
import domein.Account.Rol;

public final class MenuController {
	private static  Rol ingelogdeRol  = null ;
	private static Account ingelogdeAccount;
	
	public static void setDatabase(int database) {
		if (database==1) {
			DaoFactory.setDatabaseMYSQL(true);
		}
		else {
			DaoFactory.setDatabaseMYSQL(false);
		}
	}
	
	public static void setIngelogdeAccount(Account account) {
		ingelogdeAccount=account;
	}
	public static Account getIngelogdeAccount() {
		return ingelogdeAccount;
	}

	
	public  static void setRol (Rol rol) {
		ingelogdeRol=rol;
		
	}
	
	public  static boolean isBeheerder() {
		if(ingelogdeRol==Rol.beheerder) {
			return true;
		}
		else
		return false;
	}
	
	public static boolean isKlant() {
		if (ingelogdeRol==Rol.klant) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void setConnectionPool(int connectionPool) {
		if(connectionPool == 1) {
			data.ConnectieFactory.setConnectiePool(true);
		}
		else {
			data.ConnectieFactory.setConnectiePool(false);
		}
	}
	
	
}
