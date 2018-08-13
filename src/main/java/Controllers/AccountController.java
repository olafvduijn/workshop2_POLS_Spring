package Controllers;

import data.AccountDAOImpl;
import data.KlantDAOImpl;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import dataOld.DaoFactory;
import dataOld.KlantDao;
import dataMongoDB.AccountDaoMongoImplement;
import domein.Account;
import domein.Klant;
import domein.Account.Rol;
import java.util.List;
import view.Menu;

public class AccountController {

    private AccountDAOImpl accountDao;
    private KlantDAOImpl klantDao;
    private Account account;

    public AccountController() {
        accountDao = new AccountDAOImpl(Menu.em, Account.class);
        klantDao = new KlantDAOImpl(Menu.em, Klant.class);
    }

    public AccountController(AccountDaoMongoImplement accountDao) {
        //this.accountDao = accountDao;
    }

    public boolean voegAccountToe(String userNaam, String password, Rol rol) {   //https://medium.com/@mpreziuso/password-hashing-pbkdf2-scrypt-bcrypt-1ef4bb9c19b3
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));             // gensalt's log_rounds parameter determines the complexity
        Account newAccount = accountDao.create(new Account(userNaam, hashed, rol));
        return newAccount != null;
    }

    public String zoekAccount(Account account) {
        return zoekAccount(account.getId());
    }

    public String zoekAccount(int accountId) {
        account = accountDao.findById(accountId);
        if (account == null) {
            return null;
        } else {
            String returnstring = ("" + account.getId() + " " + account.getUserNaam() + " " + account.getPassword() + " " + account.getRol().toString());
            return returnstring;
        }
    }

    public boolean checkcredentials(String username, String password) {
        account = accountDao.findByName(username);
        if (account == null) {
            return false;
        }
        Rol rol = account.getRol();
        MenuController.setRol(rol);
        MenuController.setIngelogdeAccount(account);
        return BCrypt.checkpw(password, account.getPassword());
    }

    public boolean pasUserNaamAan(int accountId, String userNaam) {
        account = accountDao.findById(accountId);
        if (account == null) {
            return false;
        }
        account.setUserNaam(userNaam);
        accountDao.update(account);
        return account.getId() > 0;
    }

    public boolean pasUserPasswordAan(int accountId, String password) {
        account = accountDao.findById(accountId);
        if (account == null) {
            return false;
        }
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        account.setUserNaam(hashed);
        accountDao.update(account);
        return account.getId() > 0;
    }

    public boolean pasRolAan(int accountId, Rol rol) {
        account = accountDao.findById(accountId);
        if (account == null) {
            return false;
        }
        account.setRol(rol);
        accountDao.update(account);
        return account.getId() > 0;
    }

    public boolean deleteAccount(int accountId) {
        account = accountDao.findById(accountId);
        if (account == null) {
            return false;
        }
        account.setId(accountId);
        accountDao.delete(account);
        return true;
    }

    public String[] getAlleAccounts() {
        ArrayList<Account> accounts = accountDao.findAll();
        String[] returnArray = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            returnArray[i] = a.getId() + ": " + a.getUserNaam() + " " + a.getRol().toString();
        }
        return returnArray;
    }

    public boolean accountIsKlant(int accountId) {
        account = accountDao.findById(accountId);
        return (account.getRol() == Account.Rol.klant);
    }

    public boolean accountHeeftGeenKlant(int accountId) {
        Klant klanten = klantDao.getAlleKlantenPerAccount(accountId);
        return (klanten == null);
    }

    public boolean accountHeeftGeenKlant(Account account) {
        return accountHeeftGeenKlant(account.getId());
    }

    public String[] getBeschikbareKlantAccounts() {
        List<Account> accounts = accountDao.getKlantAccountsZonderKlant();
        String[] returnArray = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            returnArray[i] = a.getId() + ": " + a.getUserNaam() + " " + a.getRol().toString();
        }
        return returnArray;
    }

    public boolean isBestaandAccountId(int accountId) {
        if (accountDao.findById(accountId) == null) {
            return false;
        } else {
            return true;
        }
    }

}
