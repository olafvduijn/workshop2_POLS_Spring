package mapper_test;

import java.util.ArrayList;

import domein.Artikel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.*;

import dataOld.ArtikelDao;
import dataOld.DaoFactory;

public class ArtikelDaoTest {

	
	Artikel nieuweArtikel1=new Artikel ("Oude kaas", new BigDecimal ("7.20"), 100);
	Artikel nieuweArtikel2=new Artikel ("Belegen kaas", new BigDecimal ("6.20"), 100);
	Artikel nieuweArtikel3=new Artikel ("Jonge kaas", new BigDecimal ("5.20"), 100);
	Artikel nieuweArtikel4=new Artikel ("brie", new BigDecimal("7.35"), 500);
	
		
	ArtikelDao adao;
	
	//static ConnectieDatabase cdb=new ConnectieDatabase();
	
	
	//@BeforeClass
	//public static void initialiseer() throws SQLException {
		//cdb.maakVerbinding();
		//database clean? truncate  of set foreign_key_checks = 0 
	//}
	
	@Before
	public void setUp(){
		DaoFactory.setDatabaseMYSQL(false); //deze regel al dan niet activeren voor Mongo test
		adao=DaoFactory.getArtikelDao();
		adao.createArtikel(nieuweArtikel1);
		adao.createArtikel(nieuweArtikel2);
		adao.createArtikel(nieuweArtikel3);

	}
	

	@After
	public void finish() {
		adao.deleteArtikel(nieuweArtikel1);
		adao.deleteArtikel(nieuweArtikel2);
		adao.deleteArtikel(nieuweArtikel3);
		
	}
	
	//@AfterClass
	//public static void close() {
	//	cdb.sluitAf();
		
	//}
	
	@Test
	public void testCreateArtikel() {
		
		Artikel actueleArtikel1=adao.getArtikel(nieuweArtikel1.getId());
		Artikel actueleArtikel2=adao.getArtikel(nieuweArtikel2.getId());
		Artikel actueleArtikel3=adao.getArtikel(nieuweArtikel3.getId());
		
		assertEquals("Artikel1 naam niet juist opgeslagen", actueleArtikel1.getNaam(),nieuweArtikel1.getNaam());
		assertEquals("Artikel1 id niet juist opgeslagen", actueleArtikel1.getId(),nieuweArtikel1.getId());
		assertEquals("Artikel1 prijs niet juist opgeslagen", actueleArtikel1.getPrijs() ,nieuweArtikel1.getPrijs());
		assertEquals("Artikel1 voorraad niet juist opgeslagen", actueleArtikel1.getVoorraad(),nieuweArtikel1.getVoorraad());
		
		assertEquals("Artikel2 naam niet juist opgeslagen", actueleArtikel2.getNaam(),nieuweArtikel2.getNaam());
		assertEquals("Artikel2 id niet juist opgeslagen", actueleArtikel2.getId(),nieuweArtikel2.getId());
		assertEquals("Artikel2 prijs niet juist opgeslagen", actueleArtikel2.getPrijs() ,nieuweArtikel2.getPrijs());
		assertEquals("Artikel2 voorraad niet juist opgeslagen", actueleArtikel2.getVoorraad(),nieuweArtikel2.getVoorraad());
		
		assertEquals("Artikel3 naam niet juist opgeslagen", actueleArtikel3.getNaam(),nieuweArtikel3.getNaam());
		assertEquals("Artikel3 id niet juist opgeslagen", actueleArtikel3.getId(),nieuweArtikel3.getId());
		assertEquals("Artikel3 prijs niet juist opgeslagen", actueleArtikel3.getPrijs() ,nieuweArtikel3.getPrijs());
		assertEquals("Artikel3 voorraad niet juist opgeslagen", actueleArtikel3.getVoorraad(),nieuweArtikel3.getVoorraad());
		
	}
	@Test
	public void testGetArtikel() {
		;
	}
	@Test
	public void testGetAlleArtikelen() {
		ArrayList<Artikel> actueleWaarden = adao.getAlleArtikelen();
		ArrayList <Artikel> testlijst=new ArrayList <Artikel>();
		testlijst.add(nieuweArtikel1);
		testlijst.add(nieuweArtikel2);
		testlijst.add(nieuweArtikel3);
		
		for (int i=0; i<testlijst.size();i++) {		
			assertEquals("Artikel naam niet juist opgeslagen",actueleWaarden.get(i).getNaam(),testlijst.get(i).getNaam());
			assertEquals("Artikel id niet juist opgeslagen", actueleWaarden.get(i).getId(),testlijst.get(i).getId());
			assertEquals("Artikel prijs niet juist opgeslagen", actueleWaarden.get(i).getPrijs() ,testlijst.get(i).getPrijs());
			assertEquals("Artikel voorraad niet juist opgeslagen", actueleWaarden.get(i).getVoorraad(),testlijst.get(i).getVoorraad());		
		}
		
	}
	@Test
	public void testUpdateArtikelStringBigDecimalIntInt() {
		adao.updateArtikel("nieuwe kaasnaam", new BigDecimal ("9.99"),20,nieuweArtikel1.getId());
		Artikel actueleArtikel1=adao.getArtikel(nieuweArtikel1.getId());
		assertEquals("Artikel naam niet juist opgeslagen",actueleArtikel1.getNaam(),"nieuwe kaasnaam");
		assertEquals("Artikel prijs niet juist opgeslagen", actueleArtikel1.getPrijs() ,new BigDecimal ("9.99"));
		assertEquals("Artikel voorraad niet juist opgeslagen", actueleArtikel1.getVoorraad(),20);
		
	}
	@Test
	public void testUpdateArtikelArtikel() {
		nieuweArtikel1.setNaam("testkaas");
		nieuweArtikel1.setPrijs(new BigDecimal ("8.88"));
		nieuweArtikel1.setVoorraad(10);
		
		adao.updateArtikel(nieuweArtikel1);
		Artikel actueleArtikel1=adao.getArtikel(nieuweArtikel1.getId());
		
		assertEquals("Artikel naam niet juist opgeslagen",actueleArtikel1.getNaam(),"testkaas");
		assertEquals("Artikel prijs niet juist opgeslagen", actueleArtikel1.getPrijs() ,new BigDecimal ("8.88"));
		assertEquals("Artikel voorraad niet juist opgeslagen", actueleArtikel1.getVoorraad(),10);
		
	}
	@Test
	public void testDeleteArtikelInt() {
		adao.createArtikel(nieuweArtikel4);
		adao.deleteArtikel(nieuweArtikel4.getId());
//		Artikel actueleArtikel4=adao.getArtikel(nieuweArtikel4.getId());
//		assertEquals("Artikel wordt niet verwijderd", actueleArtikel4, null);
	}
	@Test
	public void testDeleteArtikelArtikel() {
		adao.createArtikel(nieuweArtikel4);
		adao.deleteArtikel(nieuweArtikel4);
//		Artikel actueleArtikel4=adao.getArtikel(nieuweArtikel4.getId());
//		assertEquals("Artikel wordt niet verwijderd", actueleArtikel4, null);
		
	}

}
