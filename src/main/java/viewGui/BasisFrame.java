package viewGui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BasisFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JMenu bestandMenu;
	private static JMenu gaNaarMenu;
	private static JMenuItem uitlogMenuItem;
	private static JRadioButtonMenuItem gaNaarAccountItem;
	private static JRadioButtonMenuItem gaNaarKlantItem;
	private static JRadioButtonMenuItem gaNaarArtikelItem;
	private static JRadioButtonMenuItem gaNaarBestellingItem;
	private static ButtonGroup gaNaarGroep;
	private static boolean isIngelogd=false;
	private static JMenuBar menubalk;
	private static boolean isKlant=false;
	private static boolean isBeheerder=false;
	
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasisFrame frame = new BasisFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BasisFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menubalk = new JMenuBar();
		setJMenuBar(menubalk);
		creeerMenubalk();

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InlogPane inlogPane = new InlogPane();
		
		bestandMenu.setEnabled(false);
		gaNaarMenu.setEnabled(false);
	}
	
	

	
	public static void reset() {
		contentPane.removeAll();
		rebuild();
	}
	
	public static void rebuild() {
		wijzigMenuBeschikbaarheid(isIngelogd);
		menubalk.revalidate();
		contentPane.revalidate();
		contentPane.repaint();
	}
	

	
	public static void wijzigMenuBeschikbaarheid(boolean menuIsBeschikbaar) {
		bestandMenu.setEnabled(menuIsBeschikbaar);
		gaNaarMenu.setEnabled(menuIsBeschikbaar);
	}
		
		
		
		public static int haalIdUitString(String s) {
			int index=s.indexOf(':');
			String nieuweString=s.substring(0, index);
			return Integer.parseInt(nieuweString);
		}
		
		
		
		public static JPanel getCenterPane() {
			return contentPane;
		}
		
		public static void setInlogStatus(boolean isIngelogdStatus) {
			isIngelogd=isIngelogdStatus;
		}
		
		private void creeerMenubalk() {
			bestandMenu=new JMenu("Bestand");
			menubalk.add(bestandMenu);
			gaNaarMenu=new JMenu("Ga naar...");
			menubalk.add(gaNaarMenu);
			
			uitlogMenuItem=new JMenuItem("Uitloggen");
			bestandMenu.add(uitlogMenuItem);
			
			gaNaarGroep= new ButtonGroup();
			gaNaarAccountItem=new JRadioButtonMenuItem("Ga naar het accountmenu");
			gaNaarGroep.add(gaNaarAccountItem);
			gaNaarMenu.add(gaNaarAccountItem);
			gaNaarKlantItem=new JRadioButtonMenuItem("Ga naar het klantmenu");
			gaNaarGroep.add(gaNaarKlantItem);
			gaNaarMenu.add(gaNaarKlantItem);
			if(!isKlant) {
				gaNaarArtikelItem=new JRadioButtonMenuItem("Ga naar het artikelmenu");
				gaNaarGroep.add(gaNaarArtikelItem);
				gaNaarMenu.add(gaNaarArtikelItem);
			}
			if(!isKlant) {
				gaNaarBestellingItem=new JRadioButtonMenuItem("Ga naar het bestellingmenu");
				gaNaarGroep.add(gaNaarBestellingItem);
				gaNaarMenu.add(gaNaarBestellingItem);
			}
			menubalk.add(bestandMenu);
			menubalk.add(gaNaarMenu);
			
			gaNaarAccountItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AccountPane accountPane=new AccountPane();
				}
			});
			
			gaNaarKlantItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					KlantPane klantPane=new KlantPane();
				}
			});
			
			gaNaarArtikelItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ArtikelPane artikelPane=new ArtikelPane();
				}
			});
			
			gaNaarBestellingItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					BestellingPane bestellingPane=new BestellingPane();
				}
			});
			uitlogMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					reset();
					setInlogStatus(false);
					rebuild();
					InlogPane inlogPane=new InlogPane();
					
				}
			});
		}
		
		public static void setIsKlant(boolean isWelKlant) {
			isKlant=isWelKlant;
		}
		
		public static void setIsBeheerder (boolean isWelBeheerder) {
			isBeheerder=isWelBeheerder;
		}
		
		public static boolean getIsBeheerder () {
			return isBeheerder;
		}
		
		public static boolean getIsKlant() {
			return isKlant;
		}
		
		

		

	

	
}
