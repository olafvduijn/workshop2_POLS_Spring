package viewGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controllers.AccountController;
import Controllers.MenuController;
import domein.Account;
import domein.Account.Rol;

public class AccountPane {

	private AccountController accountController=new AccountController();
	private JPanel contentPane;
	JButton accountToevoegButton;
	JButton accountVerwijderOfWijzigButton;
	
	public AccountPane() {
		contentPane=BasisFrame.getCenterPane();
		setAccountPane();
	}
	
	private void accountToevoegen() {
		String accountnaam = JOptionPane.showInputDialog("Wat is de naam van de account");
		String wachtwoord = JOptionPane.showInputDialog("Wat is het wachtwoord van de account");
		Object[] rollen = { "Beheerder", "Medewerker", "klant" };
		Object selectedValue = JOptionPane.showInputDialog(contentPane, "Selecteer een rol", null,JOptionPane.INFORMATION_MESSAGE, null, rollen, rollen[0]);
		Rol rol = Account.Rol.toRol(selectedValue.toString());
		accountController.voegAccountToe(accountnaam, wachtwoord, rol);
	}
	
	private void accountVerwijderenOfWijzigen() {
		ArrayList <String> accounts = new ArrayList<String>();
		if (!BasisFrame.getIsKlant()) {
			for(String s : accountController.getAlleAccounts()) {
			accounts.add(s);
			}
		}
		else {
			accounts.add(accountController.zoekAccount(MenuController.getIngelogdeAccount()));
		}
		Object selectedValue = JOptionPane.showInputDialog(contentPane, "Selecteer een account", null,JOptionPane.INFORMATION_MESSAGE, null, accounts.toArray(), accounts.get(0));
		int accountId=BasisFrame.haalIdUitString(selectedValue.toString());
		Object[] wijzigOfVerwijder = { "Wijzig account", "Verwijder account" };
		Object selectedValueWijzigOfVerwijder = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, wijzigOfVerwijder, wijzigOfVerwijder[0]);
		if (selectedValueWijzigOfVerwijder.equals("Verwijder account")) {
			accountController.deleteAccount(accountId);
		}
		else {
			Object[] teWijzigenAccountWaarde = { "Pas de naam van de account aan", "Pas het wachtwoord van de account aan", "Pas de rol van de acccount aan" };
			Object selectedAccountWaarde = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, teWijzigenAccountWaarde, teWijzigenAccountWaarde[0]);
			String selectedAccountWaardeString=selectedAccountWaarde.toString();
			switch (selectedAccountWaardeString) {
				case "Pas de naam van de account aan":
					String userNaam=JOptionPane.showInputDialog("Geef de nieuwe accountnaam op");
					accountController.pasUserNaamAan(accountId, userNaam);
					break;
				case "Pas het wachtwoord van de account aan":
					String wachtwoord=JOptionPane.showInputDialog("Geef het nieuwe wachtwoord op");
					accountController.pasUserPasswordAan(accountId, wachtwoord);
					break;
				case "Pas de rol van de acccount aan":
					Object[] nieuweRollen = { "Beheerder", "Medewerker", "klant" };
					Object selectedValueRol = JOptionPane.showInputDialog(contentPane, "Selecteer een rol", null,JOptionPane.INFORMATION_MESSAGE, null, nieuweRollen, nieuweRollen[0]);
					Rol nieuweRol = Account.Rol.toRol(selectedValueRol.toString());
					accountController.pasRolAan(accountId, nieuweRol);
					break;
			}
		}

		
	}
	
	private void setAccountPane() {
		BasisFrame.reset();
		accountToevoegButton=new JButton("Voeg account toe");
		accountVerwijderOfWijzigButton=new JButton("Verwijder of wijzig een account");
		accountToevoegButton.setBounds(20,40,300,20);
		accountVerwijderOfWijzigButton.setBounds(20,80,300,20);
		if(BasisFrame.getIsBeheerder()) {
				contentPane.add(accountToevoegButton);
		}
		
		contentPane.add(accountVerwijderOfWijzigButton);
		BasisFrame.rebuild();
		accountToevoegButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accountToevoegen();
			}
		});
		accountVerwijderOfWijzigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accountVerwijderenOfWijzigen();
			}
		});

}


}
