package viewGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.AccountController;
import Controllers.KlantController;
import Controllers.MenuController;
import validator.Validator;

public class KlantPane {

	private JPanel contentPane;
	private AccountController accountController;
	private KlantController klantController;
	private JButton klantToevoegButton;
	private JButton kiesKlantButton;
	
	private String voornaam;
	private String tussenvoegsel=null;
	private String achternaam;
	private int accountId=Integer.MIN_VALUE;
	int klantId=Integer.MIN_VALUE;
	

	public KlantPane() {
		contentPane=BasisFrame.getCenterPane();
		accountController=new AccountController();
		klantController=new KlantController();
		setKlantPane();
	}
	
	private void setKlantPane() {
		if (BasisFrame.getIsKlant()) {
			accountId=MenuController.getIngelogdeAccount().getId();
		}
		BasisFrame.reset();
		klantToevoegButton=new JButton("Voeg klant toe");
		kiesKlantButton=new JButton("Kies een bestaande klant");
		klantToevoegButton.setBounds(20,40,300,20);
		kiesKlantButton.setBounds(20,80,300,20);
		
		if (!BasisFrame.getIsKlant()) {
			contentPane.add(klantToevoegButton);
			contentPane.add(kiesKlantButton);
		}
		else {
			if(accountController.accountHeeftGeenKlant(MenuController.getIngelogdeAccount())) {
				contentPane.add(klantToevoegButton);
//				contentPane.add(kiesKlantButton);

			}
			else {
//				contentPane.add(klantToevoegButton);
				contentPane.add(kiesKlantButton);
			}
		}
		
		
		BasisFrame.rebuild();
		
		klantToevoegButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				VoegKlantToePane();
			}
		});
		kiesKlantButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				selecteerKlant();
			}
		});		
	}
	
	private void VoegKlantToePane() {
		BasisFrame.reset();
		JLabel accountLabel=new JLabel ("Account");
		JLabel voornaamLabel=new JLabel ("Voornaam");
		JLabel tussenvoegselLabel=new JLabel ("Tussenvoegsel");
		JLabel achternaamLabel=new JLabel ("Achternaam");
		
		JTextField voornaamTextfield=new JTextField();
		JTextField tussenvoegselTextfield=new JTextField();
		JTextField achternaamTextfield=new JTextField();
		JComboBox accountkeuzeComboBox=new JComboBox(accountController.getBeschikbareKlantAccounts());
		JLabel accountkeuzeLabel=new JLabel();
		

		if (BasisFrame.getIsKlant()) {
			accountkeuzeLabel.setText(accountController.zoekAccount(MenuController.getIngelogdeAccount()));
			accountkeuzeLabel.setBounds(150, 20, 250, 20);
			contentPane.add(accountkeuzeLabel);
		}
		else {
			accountkeuzeComboBox.setBounds(150, 20, 250, 20);
			contentPane.add(accountkeuzeComboBox);
		}
		
		JButton volgendeButton=new JButton("Ga door naar volgende stap");
		
		accountLabel.setBounds(20, 20, 120, 20);
		voornaamLabel.setBounds(20, 50, 120, 20);
		tussenvoegselLabel.setBounds(20, 80, 120, 20);
		achternaamLabel.setBounds(20, 110, 120, 20);
		
		voornaamTextfield.setBounds(150, 50, 250, 20);
		tussenvoegselTextfield.setBounds(150, 80, 250, 20);
		achternaamTextfield.setBounds(150, 110, 250, 20);
		volgendeButton.setBounds(150,150, 200,20);
		
		contentPane.add(accountLabel);
		contentPane.add(voornaamLabel);
		contentPane.add(tussenvoegselLabel);
		contentPane.add(achternaamLabel);
		contentPane.add(voornaamTextfield);
		contentPane.add(tussenvoegselTextfield);
		contentPane.add(achternaamTextfield);
		
		contentPane.add(volgendeButton);
		
		volgendeButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				voornaam=voornaamTextfield.getText();
				tussenvoegsel=tussenvoegselTextfield.getText();
				achternaam=achternaamTextfield.getText();
				if (!BasisFrame.getIsKlant()) {
					accountId=BasisFrame.haalIdUitString(accountkeuzeComboBox.getSelectedItem().toString());
				}
				voegKlantPostAdresToe();
			}
		});
		BasisFrame.rebuild();		
		
	}
	
	
	
	private void voegKlantPostAdresToe() {
		BasisFrame.reset();
		JLabel straatnaamLabel=new JLabel ("Straatnaam");
		JLabel huisnummerLabel=new JLabel ("Huisnummer");
		JLabel toevoegingLabel=new JLabel ("Toevoeging");
		JLabel postcodeLabel=new JLabel ("Postcode");
		JLabel plaatsLabel=new JLabel ("Woonplaats");
		
		JTextField straatnaamTextField=new JTextField ();
		JTextField huisnummerTextField=new JTextField ();
		JTextField toevoegingTextField=new JTextField ();
		JTextField postcodeTextField=new JTextField ();
		JTextField plaatsTextField=new JTextField ();
		
		JButton voegKlantToeButton=new JButton("Voeg klant toe");
		
		straatnaamLabel.setBounds(20, 20, 120, 20);
		huisnummerLabel.setBounds(20, 50, 120, 20);
		toevoegingLabel.setBounds(20, 80, 120, 20);
		postcodeLabel.setBounds(20, 110, 120, 20);
		plaatsLabel.setBounds(20, 140, 120, 20);
		
		straatnaamTextField.setBounds(150, 20, 250, 20);
		huisnummerTextField.setBounds(150, 50, 250, 20);
		toevoegingTextField.setBounds(150, 80, 250, 20);
		postcodeTextField.setBounds(150, 110, 250, 20);
		plaatsTextField.setBounds(150, 140, 250, 20);
		
		voegKlantToeButton.setBounds(150, 180, 200, 20);
		
		contentPane.add(straatnaamLabel);
		contentPane.add(huisnummerLabel);
		contentPane.add(toevoegingLabel);
		contentPane.add(postcodeLabel);
		contentPane.add(plaatsLabel);
		contentPane.add(straatnaamTextField);
		contentPane.add(huisnummerTextField);
		contentPane.add(toevoegingTextField);
		contentPane.add(postcodeTextField);
		contentPane.add(plaatsTextField);
		contentPane.add(voegKlantToeButton);			
		BasisFrame.rebuild();
		
		voegKlantToeButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				klantController.voegKlantToe(voornaam, tussenvoegsel, achternaam, accountId, straatnaamTextField.getText(), Integer.parseInt(huisnummerTextField.getText()), toevoegingTextField.getText(), postcodeTextField.getText(), plaatsTextField.getText());
				setKlantPane();
			}
		});
		postcodeTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(!Validator.postcodeIsValid(postcodeTextField.getText())) {
					JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Postcode voldoet niet aan format 1234AA" + System.lineSeparator() + "Probeer het opnieuw.");
					postcodeTextField.setText("");
				}
				
			}
			
		});
	}
	
	private void selecteerKlant() {
		ArrayList <String> klanten = new ArrayList<String>();
		for(String s : klantController.getAlleKlanten()) {
			klanten.add(s);
		}
		
		Object[] klantenlijst = klanten.toArray();
		if (!BasisFrame.getIsKlant()) {
			Object selectedKlantValue = JOptionPane.showInputDialog(contentPane, "Selecteer een klant", null,JOptionPane.INFORMATION_MESSAGE, null, klantenlijst, klantenlijst[0]);
			klantId=BasisFrame.haalIdUitString(selectedKlantValue.toString());
		}
		else {
			klantId=klantController.getKlantIdvanAccount(MenuController.getIngelogdeAccount());
		}
		
		
			
		Object[] opties = { "Wijzig klant" , "Verwijder klant" , "Ga naar adressen van deze klant" , "Ga naar bestellingen van deze klant"};
		Object selectedOptieValue = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, opties, opties[0]);
		if (selectedOptieValue.equals("Wijzig klant")) {
			wijzigKlant(klantId);
		}
		else if (selectedOptieValue.equals("Verwijder klant")) {
			verwijderKlant(klantId);
		}
		else if (selectedOptieValue.equals("Ga naar adressen van deze klant")) {
			AdresPane adresPane=new AdresPane(klantId);
		}
		else {
			BestellingPane bestellingPane=new BestellingPane(klantId);
		}
	}
	
	private void verwijderKlant(int klantId) {
		klantController.deleteKlant(klantId);
		setKlantPane();
	}
	
	private void wijzigKlant(int klantId) {
		Object[] opties = { "Wijzig voornaam" , "Wijzig tussenvoegsel" , "Wijzig achternaam" };
		Object selectedOptieValue = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, opties, opties[0]);
		if (selectedOptieValue.equals("Wijzig voornaam")) {
			String voornaam = JOptionPane.showInputDialog("Wat is de nieuwe voornaam");
			klantController.pasVoornaamAan(klantId, voornaam);
		}
		else if (selectedOptieValue.equals("Wijzig tussenvoegsel")) {
			String tussenvoegsel = JOptionPane.showInputDialog("Wat is het nieuwe tussenvoegsel");
			klantController.pasTussenvoegselAan(klantId, tussenvoegsel);
		}
		else {
			String achternaam = JOptionPane.showInputDialog("Wat is de nieuwe achternaam");
			klantController.pasAchternaamAan(klantId, achternaam);
		}
		setKlantPane();
	}


}
