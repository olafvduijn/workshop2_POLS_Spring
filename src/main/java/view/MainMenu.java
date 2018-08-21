package view;
import Controllers.AccountController;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import Controllers.MenuController;


import utility.EntityManagerPols;
import utility.HibernateEntityManagerFactory;
import utility.Slf4j;
import view.AccountsMenu;
import view.ArtikelMenu;
import view.KlantgegevensMenu;

@Configuration
@ComponentScan(basePackages = { "Controllers", "view" })
public class MainMenu {

	// Verkrijg een entityManager
	// public static EntityManager em =
	// HibernateEntityManagerFactory.getEntityManager();
	EntityManagerPols emp = new EntityManagerPols();
	private static Scanner input = new Scanner(System.in);

	@Autowired
	private AccountController accountController;
	@Autowired
	private AccountsMenu accountMenu;
	@Autowired
	private ArtikelMenu artikelmenu;
	
	
	public static void main(String[] args) {
		Locale local = new Locale("nl_NL");
		Locale.setDefault(local);

		ApplicationContext context = new AnnotationConfigApplicationContext(MainMenu.class);
		MainMenu menu = context.getBean(MainMenu.class);

		// Slf4j Logger
		Slf4j.getLogger().info("Workshop2 (POLS) started");

		// Menu menu = new Menu();
		menu.inloggen();

	}

	protected int inlogKeuze() {
		while (true) {
			System.out.println("Hallo!  dit is Boer Piet kaas winkel");
			System.out.println("Wilt u 1: inloggen of 2: afsluiten");
			String userChoice = input.nextLine();
			if (userChoice.length() == 1) {
				switch (userChoice) {
				case "1":
					return 1;
				case "2":
					return 2;
				}
			}
			System.out.println("Incorrecte keuze. Probeer opnieuw.");
		}

	}

	public void inloggen() {
		while (true) {

			// System.out.println("Hallo! dit is Boer Piet kaas winkel");
			// System.out.println("Wilt u 1: inloggen of 2: afsluiten");
			// int keus = input.nextInt();
			// input.nextLine();
			int keus = inlogKeuze();
			if (keus == 2) {
				Slf4j.getLogger().info("Workshop2 (POLS) ended");

				// Close entityManager
				HibernateEntityManagerFactory.closeEntityManagerFactory();

				System.out.println("Bedankt tot ziens");
				System.exit(0);

			} else {

				System.out.println("Log in om verder te gaan");
				System.out.println("Usernaam?");
				String user = input.nextLine();
				System.out.println("Password?");
				String password = input.nextLine();

				// String dbUser = "User";
				// String dbPassword = "Password"; // credentials from the data source
				// if (dbUser.equals(user) && dbPassword.equals(password)) {

				// AccountController accountController = new AccountController();
				if (accountController.checkcredentials(user, password)) {

					System.out.println("U bent succesvol ingelogd ");

					actie();

				} else {

					System.out.println("Onjuiste credentials! Probeer het opnieuw");
					input.nextLine();
				}

				// }
			}
		}
	}

	public void actie() {
		boolean logout = false;
		while (!logout) {
			System.out.println("Kies en type in wat u wilt doen:  1 :Klanten bekijken");
			System.out.println("Kies en type in wat u wilt doen:  2 :Artikelen bekijken");
			if (MenuController.isBeheerder()) {
				System.out.println("Kies en type in wat u wilt doen:  3 :Accounts bekijken");
			}
			System.out.println("Kies en type in wat u wilt doen:  0 :Uitloggen");

			int actie = input.nextInt();
			switch (actie) {
			case 1:
				KlantgegevensMenu klantgegevensMenu = new KlantgegevensMenu();
				klantgegevensMenu.klantgegevensMenu();
				break;
			case 2:
				artikelmenu.artikelMenu();
				break;
			case 3:
				if (MenuController.isBeheerder()) {
				accountMenu.accountsMenu();
				}
				break;
			case 0: {
				System.out.println("Uitloggen");
				logout = true;
				break;
			}
			default:
				System.out.println("Kies 1 t/m 3");
			}

		}
	}

}
