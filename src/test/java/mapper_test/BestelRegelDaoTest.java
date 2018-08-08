package mapper_test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import dataMySQL.AccountDaoImplement;
import dataMySQL.ArtikelDaoImplement;
import dataMySQL.BestellingDaoImplement;
import dataMySQL.BestelregelDaoImplement;
import dataMySQL.KlantDaoImplement;
import domein.Account;
import domein.Klant;
import domein.Bestelling;
import domein.BestelRegel;
import domein.Artikel;

import static org.junit.Assert.*;
import org.junit.*;

public class BestelRegelDaoTest {

	
	Account nieuweAccount1=new Account ("klant 1", "simpel", Account.Rol.klant);
	Klant nieuweKlant1;
	Bestelling bestelling1 = new Bestelling ();
	Artikel artikel1 = new Artikel ("oude kaas", new BigDecimal("10.10"), 100);
	BestelRegel bestelregel1;
	BestelRegel bestelregel2;
	BestelRegel bestelregel3;
	

	AccountDaoImplement accountdao=new AccountDaoImplement();
	KlantDaoImplement klantdao=new KlantDaoImplement();
	BestellingDaoImplement bestellingdao = new BestellingDaoImplement();
	ArtikelDaoImplement artikeldao = new ArtikelDaoImplement();
	BestelregelDaoImplement bestelregeldao = new BestelregelDaoImplement();
	
	
	
	@Before
	public void setUp(){
		accountdao.createAccount(nieuweAccount1);
		nieuweKlant1=new Klant ("Jan", "der", "Boy",nieuweAccount1.getId());
		klantdao.createKlant(nieuweKlant1);
		
		
		bestelling1 = new Bestelling (nieuweKlant1);
		bestellingdao.createBestelling(bestelling1);
		artikeldao.createArtikel(artikel1);
		bestelregel1=new BestelRegel(10,bestelling1.getId(), artikel1);
		bestelregel2=new BestelRegel(20,bestelling1.getId(), artikel1);
		bestelregel3=new BestelRegel(30,bestelling1.getId(), artikel1);
		bestelregeldao.createBestelregel(bestelregel1);
		//bestelregel1.setArtikel(artikel1);
		bestelregeldao.createBestelregel(bestelregel2);
		//bestelregel2.setArtikel(artikel1);
		bestelregeldao.createBestelregel(bestelregel3);
		//bestelregel3.setArtikel(artikel1);
		
	}
	@After
	public void finish() {
		artikeldao.deleteArtikel(artikel1);
		accountdao.deleteAccount(nieuweAccount1);
		//klantdao.deleteKlant(nieuweKlant1);
		//accountdao.deleteAccount(nieuweAccount1);
		
	}
	
	
	@Test
	public void testCreateBestelregel() {
		BestelRegel actueleBestelRegel1=bestelregeldao.getBestelRegel(bestelregel1.getId());	
		BestelRegel actueleBestelRegel2=bestelregeldao.getBestelRegel(bestelregel2.getId());
		BestelRegel actueleBestelRegel3=bestelregeldao.getBestelRegel(bestelregel3.getId());
		
		
		assertEquals("Bestelregel1 artikelid niet juist opgeslagen", actueleBestelRegel1.getArtikel().getId() ,bestelregel1.getArtikel().getId());
		assertEquals("Bestelregel1 aantal niet juist opgeslagen", actueleBestelRegel1.getAantal(),bestelregel1.getAantal());

		assertEquals("Bestelregel2 artikelid niet juist opgeslagen", actueleBestelRegel2.getArtikel().getId() ,bestelregel2.getArtikel().getId());
		assertEquals("Bestelregel2 aantal niet juist opgeslagen", actueleBestelRegel2.getAantal(),bestelregel2.getAantal());

		assertEquals("Bestelregel3 artikelid niet juist opgeslagen", actueleBestelRegel3.getArtikel().getId() ,bestelregel3.getArtikel().getId());
		assertEquals("Bestelregel3 aantal niet juist opgeslagen", actueleBestelRegel3.getAantal(),bestelregel3.getAantal());
		
		//bestelregeldao.deleteBestelRegel(bestelregel1);
		//bestelregeldao.deleteBestelRegel(bestelregel2);
		//bestelregeldao.deleteBestelRegel(bestelregel3);
	}

	
	@Test
	public void testGetAlleBestelRegel() {
		ArrayList<BestelRegel> actueleWaarden = bestelregeldao.getAlleBestelRegel();
		ArrayList <BestelRegel> testlijst=new ArrayList <>();
		testlijst.add(bestelregel1);
		testlijst.add(bestelregel2);
		testlijst.add(bestelregel3);
		
		for (int i=0; i<testlijst.size();i++) {	
			assertEquals("bestelregel id niet juist opgeslagen", actueleWaarden.get(i).getArtikel().getId(),testlijst.get(i).getArtikel().getId());
			assertEquals("bestelregel aantal niet juist opgeslagen", actueleWaarden.get(i).getAantal(),testlijst.get(i).getAantal());
		}
		
	}

	@Test
	public void testUpdateBestelRegelIntInt() {
		boolean updatesucces = bestelregeldao.updateBestelRegel(bestelregel2);
		assertTrue("klant niet ge-update", updatesucces);
	}

	@Test
	public void testUpdateBestelRegelBestelRegel() {
		bestelregel1.setAantal(50);
		boolean updatesucces = bestelregeldao.updateBestelRegel(bestelregel1);
		assertTrue("bestelregel niet ge-update", updatesucces);
	}

	@Test
	public void testDeleteBestelRegelInt() {
		bestelregeldao.createBestelregel(bestelregel1);
		boolean deletesucces = bestelregeldao.deleteBestelRegel(bestelregel1.getId());
		 assertTrue("bestelling 1 niet deleted",deletesucces);
	}

	@Test
	public void testDeleteBestelRegel() {
		bestelregeldao.createBestelregel(bestelregel1);
		boolean deletesucces = bestelregeldao.deleteBestelRegel(bestelregel1);
		 assertTrue("bestelling 1 niet deleted",deletesucces);
	}

}
