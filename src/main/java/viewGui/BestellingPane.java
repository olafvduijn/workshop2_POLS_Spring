package viewGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controllers.ArtikelController;
import Controllers.BestellingController;
import Controllers.BestelregelController;
import validator.Validator;

public class BestellingPane {

    private JComboBox bestellingenbox;
    private JPanel contentPane;
    private JComboBox bestelregelbox;
    private BestellingController bestelContr;
    private BestelregelController bestelrglContr;
    private ArtikelController artContr;
    private int klantId = -1;
    private JButton voegBestellingToeButton;
    private JButton wijzigBestellingButton;
    private JButton verwijderBestellingButton;
    private JButton voegBestellingregelToeButton;
    private JButton wijzigBestellingregelButton;
    private JButton verwijderBestellingregelButton;
    private JLabel bestellingLabel;
    private JLabel bestellingregelLabel;
    private int bestellingId;
    private int bestelregelId;

    public BestellingPane(int klantId) {
        this.klantId = klantId;
        bestelContr = new BestellingController();
        bestelrglContr = new BestelregelController();
        artContr = new ArtikelController();
        contentPane = BasisFrame.getCenterPane();

        setBestellingPane();
    }

    public BestellingPane() {
        bestelContr = new BestellingController();
        bestelrglContr = new BestelregelController();
        artContr = new ArtikelController();
        contentPane = BasisFrame.getCenterPane();

        setBestellingPane();
    }

    private void setBestellingPane() {
        BasisFrame.reset();
        if (klantId != -1) {
            bestellingenbox = new JComboBox(bestelContr.zoekBestellingenPerKlant(klantId));
        } else {
            bestellingenbox = new JComboBox(bestelContr.getAlleBestelling());
        }

        //bestellingenbox=new JComboBox(bestelContr.zoekBestellingenPerKlant(klantId));
        bestellingenbox.setBounds(10, 40, 200, 20);
        contentPane.add(bestellingenbox);

        bestellingLabel = new JLabel("Selecteer een bestelling:");
        bestellingLabel.setBounds(10, 10, 200, 20);
        contentPane.add(bestellingLabel);

        voegBestellingToeButton = new JButton("Cre�er nieuwe bestelling");
        voegBestellingToeButton.setBounds(10, 100, 200, 20);
        contentPane.add(voegBestellingToeButton);

        bestelregelbox = new JComboBox();
        bestelregelbox.setVisible(false);
        contentPane.add(bestelregelbox);

        voegBestellingToeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                voegBestellingToeActie();
            }
        });
        bestellingenbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                geselecteerdeBestellingActie();
            }
        });

        BasisFrame.rebuild();
    }

    private void geselecteerdeBestellingActie() {
        contentPane.remove(bestelregelbox);
        String bestellingString = bestellingenbox.getSelectedItem().toString();

        int beginIndex = bestellingString.indexOf(' ') + 1;
        int endIndex = bestellingString.indexOf(' ', beginIndex);
        bestellingId = Integer.parseInt(bestellingString.substring(beginIndex, endIndex));

        if (klantId == -1) {
            int beginIndexKlantId = bestellingString.lastIndexOf(' ') + 1;
            klantId = Integer.parseInt(bestellingString.substring(beginIndexKlantId));
        }

        bestelregelbox = new JComboBox(bestelrglContr.zoekBestelregelsPerBestelling(bestellingId));
        bestelregelbox.setBounds(230, 40, 200, 20);
        contentPane.add(bestelregelbox);

//		wijzigBestellingButton=new JButton("Wijzig deze bestelling");
        verwijderBestellingButton = new JButton("Verwijder deze bestelling");
        voegBestellingregelToeButton = new JButton("Voeg bestelregel toe");

//		wijzigBestellingButton.setBounds(10, 150, 200, 20);
        verwijderBestellingButton.setBounds(10, 200, 200, 20);
        voegBestellingregelToeButton.setBounds(230, 100, 200, 20);

        bestellingregelLabel = new JLabel("Selecteer een bestellingregel:");
        bestellingregelLabel.setBounds(230, 10, 200, 20);
        contentPane.add(bestellingregelLabel);

//		contentPane.add(wijzigBestellingButton);
        contentPane.add(verwijderBestellingButton);
        contentPane.add(voegBestellingregelToeButton);

        BasisFrame.rebuild();

        /*		wijzigBestellingButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				wijzigBestellingActie();
			}
		}); */
        verwijderBestellingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                verwijderBestellingActie();
            }
        });
        voegBestellingregelToeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                voegBestelregelToeActie();
            }
        });
        bestelregelbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                geselecteerdeBestelregelActie();
            }
        });
    }

    private void geselecteerdeBestelregelActie() {
        String bestelregelString = bestelregelbox.getSelectedItem().toString();
        int beginIndex = bestelregelString.indexOf(' ') + 1;
        int endIndex = bestelregelString.indexOf(' ', beginIndex);
        bestelregelId = Integer.parseInt(bestelregelString.substring(beginIndex, endIndex));

        wijzigBestellingregelButton = new JButton("Wijzig deze bestelregel");
        verwijderBestellingregelButton = new JButton("Verwijder deze bestelregel");

        wijzigBestellingregelButton.setBounds(230, 150, 200, 20);
        verwijderBestellingregelButton.setBounds(230, 200, 200, 20);

        contentPane.add(wijzigBestellingregelButton);
        contentPane.add(verwijderBestellingregelButton);
        BasisFrame.rebuild();

        wijzigBestellingregelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                wijzigBestelregelActie();
            }
        });
        verwijderBestellingregelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                verwijderBestelregelActie();
            }
        });

    }

    private void wijzigBestellingActie() {
        // TODO Auto-generated method stub

    }

    private void wijzigBestelregelActie() {
        ArrayList<String> artikelen = new ArrayList<String>();
        for (String s : artContr.getAlleArtikelen()) {
            artikelen.add(s);
        }
        Object[] artikelenlijst = artikelen.toArray();
        Object selectedValue = JOptionPane.showInputDialog(contentPane, "Selecteer een artikel", null, JOptionPane.INFORMATION_MESSAGE, null, artikelenlijst, artikelenlijst[0]);

        String artikelString = selectedValue.toString();
        int beginIndex = artikelString.indexOf(' ') + 1;
        int endIndex = artikelString.indexOf(' ', beginIndex);
        int artikelIndex = Integer.parseInt(artikelString.substring(beginIndex, endIndex));

        int aantal = Integer.parseInt(JOptionPane.showInputDialog("Hoeveel wilt u er bestellen?"));

        if (Validator.aantalIsPositief(aantal)) {
            bestelrglContr.pasBestelregelAan(bestelregelId, aantal, artikelIndex);
        } else {
            JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Onjuist aantal. Aantal moet positief zijn" + System.lineSeparator() + "Bestelregelregel niet toegevoegd, probeer het opnieuw.");

        }

        setBestellingPane();
    }

    private void voegBestelregelToeActie() {
        ArrayList<String> artikelen = new ArrayList<String>();
        for (String s : artContr.getAlleArtikelen()) {
            artikelen.add(s);
        }
        Object[] artikelenlijst = artikelen.toArray();
        Object selectedValue = JOptionPane.showInputDialog(contentPane, "Selecteer een artikel", null, JOptionPane.INFORMATION_MESSAGE, null, artikelenlijst, artikelenlijst[0]);

        String artikelString = selectedValue.toString();
        int beginIndex = artikelString.indexOf(' ') + 1;
        int endIndex = artikelString.indexOf(' ', beginIndex);
        int artikelIndex = Integer.parseInt(artikelString.substring(beginIndex, endIndex));

        int aantal = Integer.parseInt(JOptionPane.showInputDialog("Hoeveel wilt u er bestellen?"));
        if (Validator.aantalIsPositief(aantal)) {
            bestelrglContr.voegBestelregelToe(bestellingId, artikelIndex, aantal);
        } else {
            JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Onjuist aantal. Aantal moet positief zijn" + System.lineSeparator() + "Bestelregelregel niet toegevoegd, probeer het opnieuw.");

        }

        setBestellingPane();
    }

    private void voegBestellingToeActie() {
        bestellingId = bestelContr.voegBestellingToe(klantId);
        voegBestelregelToeActie();
    }

    private void verwijderBestelregelActie() {
        bestelrglContr.deleteBestelregel(bestelregelId);
        setBestellingPane();
    }

    private void verwijderBestellingActie() {
        //System.out.println(bestellingId+" "+klantId);
        bestelContr.deleteBestelling(bestellingId, klantId);
        setBestellingPane();
    }
}
