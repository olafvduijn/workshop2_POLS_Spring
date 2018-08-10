package utility;

import Controllers.AccountController;
import Controllers.MenuController;
import domein.Account;

/**
 *
 * @author FeniksBV
 */
public class createAccountStef {
    public static void main(String[] args) {
        MenuController mc = new MenuController();
        mc.setDatabase(1); // mysql
        mc.setConnectionPool(1);
        
        AccountController ac = new AccountController();
        ac.voegAccountToe("stef", "stef", Account.Rol.beheerder);
    }
}
