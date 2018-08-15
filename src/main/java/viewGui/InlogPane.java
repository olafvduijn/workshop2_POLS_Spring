package viewGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Controllers.AccountController;
import Controllers.MenuController;

public class InlogPane extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JPanel contentPane;
    private JTextField gebruikersnaamTextField;
    private JTextField wachtwoordTextField;
    private JCheckBox connectiepoolCheckBox;
    private AccountController accountController;
    private MenuController menuController;

    private JLabel wachtwoordLabel;

    public InlogPane() {
        contentPane = BasisFrame.getCenterPane();
        accountController = new AccountController();
        menuController = new MenuController();
        setInlogPane();

    }

    private void setInlogPane() {
        wachtwoordLabel = new JLabel("wachtwoord");
        wachtwoordLabel.setBounds(33, 55, 126, 20);
        contentPane.add(wachtwoordLabel);

        JLabel gebruikersnaamLabel = new JLabel("gebruikersnaam");
        gebruikersnaamLabel.setBounds(33, 24, 126, 20);
        BasisFrame.getCenterPane().add(gebruikersnaamLabel);

        gebruikersnaamTextField = new JTextField();
        gebruikersnaamTextField.setBounds(169, 27, 180, 20);
        BasisFrame.getCenterPane().add(gebruikersnaamTextField);
        gebruikersnaamTextField.setColumns(10);

        wachtwoordTextField = new JTextField();
        wachtwoordTextField.setToolTipText("");
        wachtwoordTextField.setColumns(10);
        wachtwoordTextField.setBounds(169, 58, 180, 20);
        BasisFrame.getCenterPane().add(wachtwoordTextField);

        JRadioButton mySQLDbRadioButton = new JRadioButton("MySQL database");
        mySQLDbRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setConnnectiepoolCheckboxZichtbaarheid(true);
//                menuController.setDatabase(1);
            }
        });
        mySQLDbRadioButton.setSelected(true);
        mySQLDbRadioButton.setBounds(6, 165, 202, 23);
        BasisFrame.getCenterPane().add(mySQLDbRadioButton);

        JRadioButton MongoDbRadioButton = new JRadioButton("Mongo database");
        MongoDbRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setConnnectiepoolCheckboxZichtbaarheid(false);
//                MenuController.setDatabase(2);
            }
        });
        MongoDbRadioButton.setBounds(6, 191, 202, 23);
        BasisFrame.getCenterPane().add(MongoDbRadioButton);

        connectiepoolCheckBox = new JCheckBox("Connectiepool in gebruik");
        connectiepoolCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!connectiepoolCheckBox.isSelected()) {
//                    MenuController.setConnectionPool(0);
                } else {
//                    MenuController.setConnectionPool(1);
                }

            }
        });
        connectiepoolCheckBox.setSelected(true);
        connectiepoolCheckBox.setBounds(226, 165, 202, 23);
        BasisFrame.getCenterPane().add(connectiepoolCheckBox);

        JButton loginButton = new JButton("log in");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (accountController.checkcredentials(gebruikersnaamTextField.getText(), wachtwoordTextField.getText())) {
                    BasisFrame.setIsBeheerder(MenuController.isBeheerder());
                    BasisFrame.setIsKlant(MenuController.isKlant());
                    BasisFrame.setInlogStatus(true);
                    BasisFrame.reset();
                } else {
                    JOptionPane.showMessageDialog(BasisFrame.getCenterPane(), "Onjuiste gebruikersnaam en/of wachtwoord." + System.lineSeparator() + "Probeer het opnieuw.");
                    gebruikersnaamTextField.setText("");
                    wachtwoordTextField.setText("");
                }
            }
        });
        loginButton.setBounds(169, 89, 89, 23);
        BasisFrame.getCenterPane().add(loginButton);

        ButtonGroup buttongroupRadioButtons = new ButtonGroup();
        buttongroupRadioButtons.add(mySQLDbRadioButton);
        buttongroupRadioButtons.add(MongoDbRadioButton);
    }

    private void setConnnectiepoolCheckboxZichtbaarheid(boolean connectiepoolCheckBoxWelZichtbaar) {
        if (connectiepoolCheckBoxWelZichtbaar) {
            connectiepoolCheckBox.setVisible(true);
        } else {
            connectiepoolCheckBox.setVisible(false);
        }
    }

}
