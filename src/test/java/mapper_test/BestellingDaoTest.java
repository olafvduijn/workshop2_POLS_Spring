package mapper_test;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import dataMySQL.AccountDaoImplement;
import dataMySQL.BestellingDaoImplement;
import dataMySQL.ConnectieDatabase;
import dataMySQL.KlantDaoImplement;
import domein.Account;
import domein.Klant;
import domein.Bestelling;

import static org.junit.Assert.*;
import org.junit.*;

public class BestellingDaoTest {

	Account nieuweAccount1=new Account ("klant 1", "simpel", Account.Rol.klant);
	Klant nieuweKlant1;

	
	Bestelling bestelling1 = new Bestelling ();
	Bestelling bestelling2 = new Bestelling ();
	Bestelling bestelling3 = new Bestelling ();

	
	// static ConnectieDatabase cdb=new ConnectieDatabase();
	AccountDaoImplement adao=new AccountDaoImplement();
	KlantDaoImplement kdao=new KlantDaoImplement();
	BestellingDaoImplement bdao = new BestellingDaoImplement();
	
	/*@BeforeClass
	public static void initialiseer() throws SQLException {
		cdb.maakVerbinding();
	}
	*/
		@Before
		public void setUp(){
			adao.createAccount(nieuweAccount1);
			nieuweKlant1=new Klant ("Jan", "der", "Boy",nieuweAccount1.getId());
			kdao.createKlant(nieuweKlant1);
			
			bestelling1 = new Bestelling (nieuweKlant1);
			bestelling2 = new Bestelling (nieuweKlant1);
			bestelling3 = new Bestelling (nieuweKlant1);
			
			bdao.createBestelling(bestelling1);
			bdao.createBestelling(bestelling2);
			bdao.createBestelling(bestelling3);
			
		}
		
		@After
		public void finish() {
			adao.deleteAccount(nieuweAccount1);
		}
		
/*		@AfterClass
		public static void close() {
			
			cdb.sluitAf();
		}
*/
	
	@Test
	public void testCreateBestelling() {
		Bestelling actueleBestelling1=bdao.getBestelling(bestelling1.getId());
		Bestelling actueleBestelling2=bdao.getBestelling(bestelling2.getId());
		Bestelling actueleBestelling3=bdao.getBestelling(bestelling3.getId());
		
		assertEquals("Bestelling1 totaalprijs niet juist opgeslagen", actueleBestelling1.getTotaalPrijs() ,bestelling1.getTotaalPrijs());
		assertEquals("Bestelling1 id niet juist opgeslagen", actueleBestelling1.getId(),bestelling1.getId());

		assertEquals("Bestelling2 totaalprijs niet juist opgeslagen", actueleBestelling2.getTotaalPrijs() ,bestelling2.getTotaalPrijs());
		assertEquals("Bestelling2 id niet juist opgeslagen", actueleBestelling2.getId(),bestelling2.getId());

		assertEquals("Bestelling3 totaalprijs niet juist opgeslagen", actueleBestelling3.getTotaalPrijs() ,bestelling3.getTotaalPrijs());
		assertEquals("Bestelling3 id niet juist opgeslagen", actueleBestelling3.getId(),bestelling3.getId());
	}

	@Test
	public void testGetAlleBestelling() {
		ArrayList<Bestelling> actueleWaarden = bdao.getAlleBestellingenPerKlant(nieuweKlant1);
		ArrayList <Bestelling> testlijst=new ArrayList <>();
		testlijst.add(bestelling1);
		testlijst.add(bestelling2);
		testlijst.add(bestelling3);
		
		for (int i=0; i<testlijst.size();i++) {	
			assertEquals("bestelling totaalprijs niet juist opgeslagen", actueleWaarden.get(i).getTotaalPrijs(),testlijst.get(i).getTotaalPrijs());
			assertEquals("bestelling id niet juist opgeslagen", actueleWaarden.get(i).getId(),testlijst.get(i).getId());

		}
		
	}

	@Test
	public void testUpdateBestellingenBigDecimalInt() {
boolean updatesucces = bdao.updateBestellingen(new BigDecimal ("999.99"), bestelling1.getId());
		assertTrue("klant niet ge-update", updatesucces);
	}

	@Test
	public void testUpdateBestellingenBestelling() {
		bestelling1.setTotaalPrijs(new BigDecimal ("888.88"));
		boolean updatesucces = bdao.updateBestellingen(bestelling1);
		assertTrue("klant niet ge-update", updatesucces);
	}

	@Test
	public void testDeleteBestellingenInt() {
		bdao.createBestelling(bestelling1);
		boolean deletesucces = bdao.deleteBestellingen(bestelling1.getId());

		 assertTrue("bestelling 1 niet deleted",deletesucces);
	}

	@Test
	public void testDeleteBestellingenBestelling() {
		bdao.createBestelling(bestelling1);
		boolean deletesucces = bdao.deleteBestellingen(bestelling1);
		 assertTrue("bestelling 1 niet deleted",deletesucces);
	}

}
