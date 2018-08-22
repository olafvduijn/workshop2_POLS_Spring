package Controllers;

import domein.Account;
import domein.Account.Rol;

public final class MenuController {

    private static Rol ingelogdeRol = null;
    private static Account ingelogdeAccount;

    public static void setIngelogdeAccount(Account account) {
        ingelogdeAccount = account;
    }

    public static Account getIngelogdeAccount() {
        return ingelogdeAccount;
    }

    public static void setRol(Rol rol) {
        ingelogdeRol = rol;

    }

    public static boolean isBeheerder() {
        if (ingelogdeRol == Rol.beheerder) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isKlant() {
        if (ingelogdeRol == Rol.klant) {
            return true;
        } else {
            return false;
        }
    }


}
