package Controllers;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import data.AccountDao;
import data.DaoFactory;
import data.KlantDao;
import dataMongoDB.AccountDaoMongoImplement;
import domein.Account;
import domein.Klant;
import domein.Account.Rol;

public class AccountController {

    private AccountDao accountDao;
    private KlantDao klantDao;
    private Account account;

    public AccountController() {
        accountDao = DaoFactory.getAccountDao();
        klantDao = DaoFactory.getKlantDao();
    }

    public AccountController(AccountDaoMongoImplement accountDao) {
        this.accountDao = accountDao;
    }

    public boolean voegAccountToe(String userNaam, String password, Rol rol) {   //https://medium.com/@mpreziuso/password-hashing-pbkdf2-scrypt-bcrypt-1ef4bb9c19b3
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));             // gensalt's log_rounds parameter determines the complexity
        Integer id = accountDao.createAccount(new Account(userNaam, hashed, rol));
        return id > 0;
    }

    public String zoekAccount(Account account) {
        return zoekAccount(account.getId());
    }

    public String zoekAccount(int accountId) {
        account = accountDao.getAccount(accountId);
        if (account == null) {
            return null;
        } else {
            String returnstring = ("" + account.getId() + " " + account.getUserNaam() + " " + account.getPassword() + " " + account.getRol().toString());
            return returnstring;
        }
    }

    public boolean checkcredentials(String username, String password) {
        account = accountDao.getAccountLogin(username);
        if (account == null) {
            return false;
        }
        Rol rol = account.getRol();
        MenuController.setRol(rol);
        MenuController.setIngelogdeAccount(account);
        return BCrypt.checkpw(password, account.getPassword());
    }

    public boolean pasUserNaamAan(int accountId, String userNaam) {
        account = accountDao.getAccount(accountId);
        if (account == null) {
            return false;
        }
        account.setUserNaam(userNaam);
        return accountDao.updateAccount(account);
    }

    public boolean pasUserPasswordAan(int accountId, String password) {
        account = accountDao.getAccount(accountId);
        if (account == null) {
            return false;
        }
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        account.setUserNaam(hashed);
        return accountDao.updateAccount(account);
    }

    public boolean pasRolAan(int accountId, Rol rol) {
        account = accountDao.getAccount(accountId);
        if (account == null) {
            return false;
        }
        account.setRol(rol);
        return accountDao.updateAccount(account);
    }

    public boolean deleteAccount(int accountId) {
        account = accountDao.getAccount(accountId);
        if (account == null) {
            return false;
        }
        account.setId(accountId);
        return accountDao.deleteAccount(account);
    }

    public String[] getAlleAccounts() {
        ArrayList<Account> accounts = accountDao.getAlleAccounts();
        String[] returnArray = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            returnArray[i] = a.getId() + ": " + a.getUserNaam() + " " + a.getRol().toString();
        }
        return returnArray;
    }

    public boolean accountIsKlant(int accountId) {
        account = accountDao.getAccount(accountId);
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
        ArrayList<Account> accounts = accountDao.getKlantAccountsZonderKlant();
        String[] returnArray = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            returnArray[i] = a.getId() + ": " + a.getUserNaam() + " " + a.getRol().toString();
        }
        return returnArray;
    }

    public boolean isBestaandAccountId(int accountId) {
        if (accountDao.getAccount(accountId) == null) {
            return false;
        } else {
            return true;
        }
    }

}
