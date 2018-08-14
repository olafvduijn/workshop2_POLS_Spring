package genericDAO;

import data.ArtikelDAOImpl;
import data.BestellingDAOImpl;
import data.BestelregelDAOImpl;
import data.KlantDAOImpl;
import data.AccountDAOImpl;
import data.AdresDAOImpl;
import domein.Account;
import domein.Adres;
import domein.Artikel;
import domein.BestelRegel;
import domein.Bestelling;
import domein.Klant;
import domein.Adres.AdresType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;

import utility.HibernateEntityManagerFactory;
import utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class testGenericDAO {

	public static void main(String[] args) {

		Slf4j.getLogger().info("testGenericDAO started");

		// Verkrijg een entityManager
		EntityManager em = HibernateEntityManagerFactory.getEntityManager();
		// Verkrijg de DAO
		ArtikelDAOImpl artikelDAO = new ArtikelDAOImpl(em, Artikel.class);
		AccountDAOImpl accountDAO = new AccountDAOImpl(em, Account.class);
		KlantDAOImpl klantDAO = new KlantDAOImpl(em, Klant.class);
		AdresDAOImpl adresDAO = new AdresDAOImpl(em, Adres.class);
		BestellingDAOImpl bestellingDAO = new BestellingDAOImpl(em, Bestelling.class);
		BestelregelDAOImpl bestelregelDAO = new BestelregelDAOImpl(em, BestelRegel.class);

		//
		// Eerst iets met accounts
		//
		Account acc1 = accountDAO.findByName("steff");
		if (acc1 != null) {
			System.out.println("Ja, gevonden, dus nu eerst delete!");
			accountDAO.delete(acc1);
		}

		em.clear();
		String pwd = BCrypt.hashpw("steff", BCrypt.gensalt(12));
		Account account = new Account("steff", pwd, Account.Rol.beheerder);
		account = accountDAO.create(account);

		String pwd2 = BCrypt.hashpw("liz", BCrypt.gensalt(12));
		Account account2 = new Account("liz", pwd2, Account.Rol.klant);
		account2 = accountDAO.create(account2);

		//
		// Artikelen: Eerst maar eens alle records uit de gehele tabel verwijderen
		//
		final ArrayList<Artikel> artikelList = artikelDAO.findAll();
		artikelList.forEach(instance -> {
			System.out.println("Deleting: " + instance.getNaam());
			artikelDAO.delete(instance);
		});
		em.clear();

		// Een artikel toevoegen
		Artikel artikel1 = new Artikel();
		artikel1.setNaam("Eerste test");
		artikel1.setPrijs(new BigDecimal(123.45));
		artikel1.setVoorraad(120);
		artikel1 = artikelDAO.create(artikel1);
		int insertedId = artikel1.getId();

		// Nog een artikel toevoegen
		Artikel artikel2 = new Artikel();
		artikel2.setNaam("Tweede test");
		artikel2.setPrijs(new BigDecimal(11.99));
		artikel2.setVoorraad(30);
		artikel2 = artikelDAO.create(artikel2);

		// Lees het eerst toegevoegde artikel
		em.clear();
		artikel1 = artikelDAO.findById(insertedId);
		if (artikel1 != null) {
			System.out.println("Gelezen artikel1 = " + artikel1.getId() + "  " + artikel1.getNaam());
		}

		List<Account> accountList = accountDAO.getKlantAccountsZonderKlant();
		for (int i = 0; i < accountList.size(); i++) {
			System.out.println("Accountnaam = " + accountList.get(i).getId() + " " + accountList.get(i).getUserNaam());
		}

		Klant klant = new Klant();
		klant.setAccount(account);
		klant.setAchternaam("liz");
		klant.setVoornaam("nat");
		klant.setTussenvoegsel("van");

		klant = klantDAO.create(klant);

		Klant klant2 = new Klant();
		klant2.setAccount(account2);
		klant2.setAchternaam("beth");
		klant2.setVoornaam("nat2");
		klant2.setTussenvoegsel("van2");

		klant2 = klantDAO.create(klant2);
		em.clear();

		List<Klant> klantList = klantDAO.findAll();
		for (int i = 0; i < klantList.size(); i++) {
			System.out.println("Klantnaam = " + klantList.get(i).getId() + " " + klantList.get(i).getAchternaam()
					+ " account_Id " + klantList.get(i).getAccount().getId());
		}
		Klant klant3 = klantDAO.getAlleKlantenPerAccount(account2.getId());
		System.out.println("Klantnaam = " + klant3.getId() + " " + klant3.getAchternaam() + " accountID "
				+ klant3.getAccount().getId());

		Adres adres = new Adres();
		adres.setKlant(klant3);
		adres.setAdresType(AdresType.POSTADRES);
		adres.setPostcode("1044AN");
		adres.sethuisnummer(22);
		adres.setStraatnaam("straat");
		adres.setToevoeging("1222ad");
		adres.setWoonplaats("woonplaats");

		adresDAO.create(adres);
		em.clear();

		System.out.println(adresDAO.toonPostadres(klant3.getId()));

		Adres adres2 = adresDAO.findByName(Adres.class, "1044AN");
		System.out.println(adres2.getStraatnaam() + " " + adres2.getHuisnummer() + " " + adres2.getToevoeging() + " "
				+ adres2.getPostcode() + " " + adres2.getWoonplaats());
		
		Bestelling bestelling1=new Bestelling(klant2);
		bestellingDAO.create(bestelling1);
		bestelling1.settotaalprijs(new BigDecimal("1.20"));
		bestellingDAO.update(bestelling1);
		
		bestellingDAO.create(new Bestelling(klant2));
		bestellingDAO.create(new Bestelling(klant2));
		
		Bestelling bestelling2=new Bestelling(klant);
		bestellingDAO.create(bestelling2);
		ArrayList <Bestelling> bestellingen=bestellingDAO.getAlleBestellingenPerKlant(klant2);
		System.out.println("aantal bestellingen klant 2 moet 3 zijn, is: "+bestellingen.size());
		System.out.println("aantal bestellingen klant 1 moet 1 zijn, is: "+bestellingDAO.getAlleBestellingenPerKlant(klant).size());
		
		bestellingDAO.delete(bestelling1);
		
		BestelRegel regel1=new BestelRegel(1, bestelling1.getId(),artikel2);
		bestelregelDAO.create(regel1);
		regel1.setPrijs(artikel2.getPrijs());
		bestelregelDAO.update(regel1);
		
		bestelregelDAO.create(new BestelRegel(2,bestelling1.getId(),artikel1));
		bestelregelDAO.create(new BestelRegel(10,bestelling2.getId(),artikel1));
		System.out.println("aantal bestelregels besteling 1 moet 2 zijn, het is: "+bestelregelDAO.getAlleBestelregelsPerBestelling(bestelling1.getId()).size());
		System.out.println("aantal bestelregels besteling 2 moet 1 zijn, het is: "+bestelregelDAO.getAlleBestelregelsPerBestelling(bestelling2.getId()).size());

		bestelregelDAO.delete(regel1);
		
		
		// Close EntityManager (als alle acties uitgevoerd)
		em.close();

		// Close entityManager (bij Exit van de applicatie)
		HibernateEntityManagerFactory.closeEntityManagerFactory();

		Slf4j.getLogger().info("testGenericDAO ended");

	}

}
