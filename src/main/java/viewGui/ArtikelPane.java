package viewGui;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import Controllers.ArtikelController;
import validator.Validator;


public class ArtikelPane {
	private ArtikelController artController=new ArtikelController();
	private JPanel contentPane;
	JButton artikelToevoegButton;
	JButton artikelVerwijderOfWijzigButton;

	public ArtikelPane() {
		contentPane=BasisFrame.getCenterPane();
		setArtikelPane();
	}

	private void setArtikelPane() {
		BasisFrame.reset();
		artikelToevoegButton=new JButton("Voeg artikel toe");
		artikelVerwijderOfWijzigButton=new JButton("Verwijder of wijzig een artikel");
		artikelToevoegButton.setBounds(20,40,300,20);
		artikelVerwijderOfWijzigButton.setBounds(20,80,300,20);
		contentPane.add(artikelToevoegButton);
		contentPane.add(artikelVerwijderOfWijzigButton);
		BasisFrame.rebuild();
		artikelToevoegButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				artikelToevoegen();
			}
		});
		artikelVerwijderOfWijzigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				artikelVerwijderenOfWijzigen();
			}
		});
		
	}
	
	private void artikelToevoegen() {
		String naam = JOptionPane.showInputDialog("Wat is de naam van het nieuwe artikel");
		BigDecimal prijs =artController.textToBigDecimal(JOptionPane.showInputDialog("Wat is de prijs van het nieuwe artikel"));
		if(Validator.validPrijs(prijs)) {
			int voorraad=Integer.parseInt(JOptionPane.showInputDialog("Wat is de voorraad van het nieuwe artikel"));
			artController.voegArtikelToe(naam, prijs, voorraad);
		}
		else {
			JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Onjuiste prijs: prijs mag niet negatief zijn." + System.lineSeparator() + "Probeer het opnieuw.");

		}
	}
	
	private void artikelVerwijderenOfWijzigen() {
		ArrayList <String> artikelen = new ArrayList<String>();
		for(String s : artController.getAlleArtikelen()) {
			artikelen.add(s);
		}
		Object selectedValue = JOptionPane.showInputDialog(contentPane, "Selecteer een artikel", null,JOptionPane.INFORMATION_MESSAGE, null, artikelen.toArray(), artikelen.get(0));
		int indexStartCijferreeks=selectedValue.toString().indexOf(':')+2;
		int indexEindeCijferreeks=selectedValue.toString().indexOf(' ' , indexStartCijferreeks);
		String selectedValueArtikelId=selectedValue.toString().substring(indexStartCijferreeks,indexEindeCijferreeks);
		int artikelId=Integer.parseInt(selectedValueArtikelId);
		System.out.println(artikelId);
		Object[] wijzigOfVerwijder = { "Wijzig artikel", "Verwijder artikel" };
		Object selectedValueWijzigOfVerwijder = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, wijzigOfVerwijder, wijzigOfVerwijder[0]);
		if (selectedValueWijzigOfVerwijder.equals("Verwijder artikel")) {
			artController.deleteArtikel(artikelId);
		}
		else {
			Object[] teWijzigenArtikelWaarde = { "Pas de naam van het artikel aan", "Pas de prijs van het artikel aan", "Pas de voorraad van het artikel aan" };
			Object selectedArtikelWaarde = JOptionPane.showInputDialog(contentPane, "Selecteer een optie", null,JOptionPane.INFORMATION_MESSAGE, null, teWijzigenArtikelWaarde, teWijzigenArtikelWaarde[0]);
			String selectedArtikelWaardeString=selectedArtikelWaarde.toString();
			switch (selectedArtikelWaardeString) {
				case "Pas de naam van het artikel aan":
					String artikelNaam=JOptionPane.showInputDialog("Geef de nieuwe artikelnaam op");
					artController.pasNaamAan(artikelId, artikelNaam);
					break;
				case "Pas de prijs van het artikel aan":
					BigDecimal prijs =artController.textToBigDecimal(JOptionPane.showInputDialog("Wat is de nieuwe prijs van het artikel"));
/*					if(Validator.validPrijs(prijs)) {
						artController.pasPrijsAan(artikelId, prijs);
					}
					else {
						JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Onjuiste prijs: prijs mag niet negatief zijn." + System.lineSeparator() + "Probeer het opnieuw.");

					}
*/					
					break;
				case "Pas de voorraad van het artikel aan":
					int voorraad=Integer.parseInt(JOptionPane.showInputDialog("Wat is de voorraad van het nieuwe artikel"));
					artController.pasVoorraadAan(artikelId, voorraad);
					break;
			}
		}

		
	}
}
