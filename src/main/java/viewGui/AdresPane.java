package viewGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Controllers.AdresController;
import domein.Account;
import domein.Account.Rol;
import validator.Validator;


public class AdresPane {
	private JPanel contentPane;
	private JTextPane postadresgegevensLabel;
	private JTextPane factuuradresgegevensLabel;
	private JTextPane bezorgadresgegevensLabel;
	private AdresController adresController;
	private JButton wijzigPostadresButton;
	private JButton wijzigFactuuradresButton;
	private JButton wijzigBezorgadresButton;
	private JButton verwijderFactuuradresButton;
	private JButton createFactuuradresButton;
	private JButton verwijderBezorgadresButton;
	private JButton createBezorgadresButton;
	
	private String straatnaam;
	private int huisnummer;
	private String toevoeging;
	private String postcode;
	private String woonplaats;
	
	
	public AdresPane(int klantId) {
		adresController=new AdresController();
		adresController.setKlant(klantId);
		contentPane=BasisFrame.getCenterPane();
		setAdresPane();
	}
	
	private void setAdresPane() {
		BasisFrame.reset();
		resetAdreswaarden();
		postadresgegevensLabel=new JTextPane();
		postadresgegevensLabel.setText("POSTADRES:"+System.lineSeparator()+adresController.toonPostadres());
		wijzigPostadresButton=new JButton("Wijzig postadres");
		wijzigPostadresButton.setBounds(5, 180, 145, 20);
		contentPane.add(wijzigPostadresButton);
		
		wijzigPostadresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wijzigPostadres();
			}
		});
				
		factuuradresgegevensLabel=new JTextPane();
		if (adresController.factuurAdresAanwezig()) {
			factuuradresgegevensLabel.setText("FACTUURADRES:"+System.lineSeparator()+adresController.toonFactuuradres());
			wijzigFactuuradresButton=new JButton("Wijzig factuuradres");
			verwijderFactuuradresButton=new JButton("Verwijder factuuradres");
			
			wijzigFactuuradresButton.setBounds(151, 180, 145, 20);
			verwijderFactuuradresButton.setBounds(151, 220, 145, 20);
			
			contentPane.add(wijzigFactuuradresButton);
			contentPane.add(verwijderFactuuradresButton);
			
			wijzigFactuuradresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					wijzigFactuuradres();
				}
			});
			
			verwijderFactuuradresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					verwijderFactuuradres();
				}
			});
			
		}
		else {
			factuuradresgegevensLabel.setText("Geen factuuradres beschikbaar");
			
			createFactuuradresButton=new JButton("Maak factuuradres");
			createFactuuradresButton.setBounds(151, 180, 145, 20);
			contentPane.add(createFactuuradresButton);
			createFactuuradresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					createFactuuradres();
				}
			});
			
		}
		bezorgadresgegevensLabel=new JTextPane();
		if (adresController.BezorgAdresAanwezig()) {
			bezorgadresgegevensLabel.setText("BEZORGADRES:"+System.lineSeparator()+adresController.toonBezorgadres());
			wijzigBezorgadresButton=new JButton("Wijzig bezorgadres");
			verwijderBezorgadresButton=new JButton("Verwijder bezorgadres");
			
			wijzigBezorgadresButton.setBounds(297, 180, 145, 20);
			verwijderBezorgadresButton.setBounds(297, 220, 145, 20);
			
			contentPane.add(wijzigBezorgadresButton);
			contentPane.add(verwijderBezorgadresButton);
			
			wijzigBezorgadresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					wijzigBezorgAdres();
				}
			});
			
			verwijderBezorgadresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					verwijderBezorgAdres();
				}
			});
		}
		else {
			bezorgadresgegevensLabel.setText("Geen bezorgadres beschikbaar");
			
			createBezorgadresButton=new JButton("Maak bezorgadres");
			createBezorgadresButton.setBounds(297, 180, 145, 20);
			contentPane.add(createBezorgadresButton);
			
			createBezorgadresButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					createBezorgadres();
				}
			});
		}
		
		postadresgegevensLabel.setBounds(5,20,145,150);
		factuuradresgegevensLabel.setBounds(151,20,145,150);
		bezorgadresgegevensLabel.setBounds(297,20,145,150);
		
		contentPane.add(postadresgegevensLabel);
		contentPane.add(factuuradresgegevensLabel);
		contentPane.add(bezorgadresgegevensLabel);
		BasisFrame.rebuild();
	}
	
	private void createAdres(String adressoort) {
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
		
		JButton voegAdresToeButton=new JButton("Voeg adres toe");
		
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
		
		voegAdresToeButton.setBounds(150, 180, 200, 20);
		voegAdresToeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				straatnaam=straatnaamTextField.getText();
				huisnummer=Integer.parseInt(huisnummerTextField.getText());
				toevoeging=toevoegingTextField.getText();
				postcode=postcodeTextField.getText();
				woonplaats=plaatsTextField.getText();
				if (adressoort.equals("factuuradres")) {
					adresController.maakFactuurAdres(straatnaam, huisnummer, toevoeging, postcode, woonplaats);
				}
				else {
					adresController.maakBezorgAdres(straatnaam, huisnummer, toevoeging, postcode, woonplaats);
				}
				setAdresPane();
			}
		});
		
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
		contentPane.add(voegAdresToeButton);			
		BasisFrame.rebuild();
	}

	protected void createFactuuradres() {
		createAdres("factuuradres");
	}

	protected void createBezorgadres() {
		createAdres("bezorgadres");
	}

	protected void verwijderBezorgAdres() {
		adresController.setBezorgadres();
		verwijderAdres();
	}

	private void verwijderFactuuradres() {
		adresController.setFactuuradres();
		verwijderAdres();
	}
	
	private void verwijderAdres() {
		adresController.verwijderAdres();
		setAdresPane();
	}

	protected void wijzigBezorgAdres() {
		adresController.setBezorgadres();
		wijzigAdres();
	}

	protected void wijzigPostadres() {
		adresController.setPostadres();
		wijzigAdres();
	}

	private void wijzigFactuuradres() {
		adresController.setFactuuradres();
		wijzigAdres();
	}

	
	private void wijzigAdres() {
		Object[] teWijzigenAdresWaarden = { "Pas de straatnaam aan", "Pas het huisnummer aan", "Pas de toevoeging aan", "Pas de postcode aan", "Pas de plaats aan" };
		Object selectedAdresWaarde = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, teWijzigenAdresWaarden, teWijzigenAdresWaarden[0]);
		String selectedAdresWaardeString=selectedAdresWaarde.toString();
		switch (selectedAdresWaardeString) {
			case "Pas de straatnaam aan":
				straatnaam=JOptionPane.showInputDialog("Geef de nieuwe straatnaam op");
				adresController.wijzigStraat(straatnaam);
				break;
			case "Pas het huisnummer aan":
				huisnummer=Integer.parseInt(JOptionPane.showInputDialog("Geef het nieuwe huisnummer op"));
				adresController.wijzigHuisnummer(huisnummer);
				break;
			case "Pas de toevoeging aan":
				toevoeging=JOptionPane.showInputDialog("Geef de nieuwe toevoeging op");
				adresController.wijzigToevoeging(toevoeging);
				break;
			case "Pas de postcode aan":
				postcode=JOptionPane.showInputDialog("Geef de nieuwe postcode op");
				if (Validator.postcodeIsValid(postcode)) {
					adresController.wijzigPostcode(postcode);
				}
				else {
					JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Postcode voldoet niet aan format 1234AA" + System.lineSeparator() + "Niets is gewijzigd");
				}
				break;
			case "Pas de plaats aan":
				woonplaats=JOptionPane.showInputDialog("Geef de nieuwe plaats op");
				adresController.wijzigPlaats(woonplaats);
				break;
		}
		setAdresPane();
	}
	
	private void resetAdreswaarden() {
		straatnaam=null;
		huisnummer=Integer.MIN_VALUE;
		toevoeging=null;
		postcode=null;
		woonplaats=null;
	}
	

	
	
}
