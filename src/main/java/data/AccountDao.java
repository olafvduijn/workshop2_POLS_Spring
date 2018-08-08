package data;


import java.util.ArrayList;

import domein.Account;
import domein.Account.Rol;


public interface AccountDao {
	public abstract Integer createAccount(Account account);	
	public abstract Account getAccount(int id);
	public abstract Account getAccountLogin(String username);
	public abstract boolean updateAccount(int id, String userNaam, String password,Rol rol );
	public abstract boolean updateAccount(Account nieuwAccount);
	public abstract boolean deleteAccount(int id);
	public abstract ArrayList<Account> getAlleAccounts();
	public abstract boolean deleteAccount(Account account);
	public abstract ArrayList<Account> getKlantAccountsZonderKlant();



}

