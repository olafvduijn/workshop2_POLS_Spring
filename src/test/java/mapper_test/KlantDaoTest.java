package mapper_test;

import java.util.ArrayList;

import domein.Account;
import domein.Klant;
import dataMySQL.AccountDaoImplement;
import dataMySQL.KlantDaoImplement;

import static org.junit.Assert.*;

import org.junit.*;

public class KlantDaoTest {

    Account nieuweAccount1 = new Account("klant 1", "simpel", Account.Rol.klant);
    Klant nieuweKlant1 = new Klant("Jan", "der", "Boy", nieuweAccount1.getId());
    Klant nieuweKlant2 = new Klant("Piet", "van", "Smit", nieuweAccount1.getId());
    Klant nieuweKlant3 = new Klant("Joris", "de", "Ridder", nieuweAccount1.getId());
    Klant nieuweKlant4 = new Klant("Corneel", "Graaf", nieuweAccount1.getId());

//	static ConnectieDatabase cdb=new ConnectieDatabase();
    AccountDaoImplement adao = new AccountDaoImplement();
    KlantDaoImplement kdao = new KlantDaoImplement();

    /*	@BeforeClass
	public static void initialiseer() throws SQLException {
		cdb.maakVerbinding();
	
	}
     */
    @Before
    public void setUp() {
        adao.createAccount(nieuweAccount1);

        nieuweKlant1 = new Klant("Jan", "der", "Boy", nieuweAccount1.getId());
        nieuweKlant2 = new Klant("Piet", "van", "Smit", nieuweAccount1.getId());
        nieuweKlant3 = new Klant("Joris", "de", "Ridder", nieuweAccount1.getId());
        nieuweKlant4 = new Klant("Corneel", "Graaf", nieuweAccount1.getId());

        kdao.createKlant(nieuweKlant1);
        kdao.createKlant(nieuweKlant2);
        kdao.createKlant(nieuweKlant3);
        kdao.createKlant(nieuweKlant4);
    }

    @After
    public void finish() {
        adao.deleteAccount(nieuweAccount1);
        //kdao.deleteKlant(nieuweKlant1); hoeft niet want de database delete klanten als hun account verwijdert wordt
        //kdao.deleteKlant(nieuweKlant2);
        //kdao.deleteKlant(nieuweKlant3);
        //kdao.deleteKlant(nieuweKlant4);

    }

    /*	@AfterClass
	public static void close() {
		
		cdb.sluitAf();	
	}
     */
    @Test
    public void testCreateKlant() {
        Klant actueleKlant1 = kdao.getKlant(nieuweKlant1.getId());
        Klant actueleKlant2 = kdao.getKlant(nieuweKlant2.getId());
        Klant actueleKlant3 = kdao.getKlant(nieuweKlant3.getId());
        Klant actueleKlant4 = kdao.getKlant(nieuweKlant4.getId());

        assertEquals("Klant1 voornaam niet juist opgeslagen", actueleKlant1.getVoornaam(), nieuweKlant1.getVoornaam());
        assertEquals("Klant1 tussenvoegsel niet juist opgeslagen", actueleKlant1.getTussenvoegsel(), nieuweKlant1.getTussenvoegsel());
        assertEquals("Klant1 achternaam niet juist opgeslagen", actueleKlant1.getAchternaam(), nieuweKlant1.getAchternaam());
        assertEquals("Klant1 id niet juist opgeslagen", actueleKlant1.getId(), nieuweKlant1.getId());

        assertEquals("Klant2 voornaam niet juist opgeslagen", actueleKlant2.getVoornaam(), nieuweKlant2.getVoornaam());
        assertEquals("Klant2 tussenvoegsel niet juist opgeslagen", actueleKlant2.getTussenvoegsel(), nieuweKlant2.getTussenvoegsel());
        assertEquals("Klant2 achternaam niet juist opgeslagen", actueleKlant2.getAchternaam(), nieuweKlant2.getAchternaam());
        assertEquals("Klant2 id niet juist opgeslagen", actueleKlant2.getId(), nieuweKlant2.getId());

        assertEquals("Klant3 voornaam niet juist opgeslagen", actueleKlant3.getVoornaam(), nieuweKlant3.getVoornaam());
        assertEquals("Klant3 tussenvoegsel niet juist opgeslagen", actueleKlant3.getTussenvoegsel(), nieuweKlant3.getTussenvoegsel());
        assertEquals("Klant3 achternaam niet juist opgeslagen", actueleKlant3.getAchternaam(), nieuweKlant3.getAchternaam());
        assertEquals("Klant3 id niet juist opgeslagen", actueleKlant3.getId(), nieuweKlant3.getId());

        assertEquals("Klant4 voornaam niet juist opgeslagen", actueleKlant4.getVoornaam(), nieuweKlant4.getVoornaam());
        assertEquals("Klant4 tussenvoegsel niet juist opgeslagen", actueleKlant4.getTussenvoegsel(), nieuweKlant4.getTussenvoegsel());
        assertEquals("Klant4 achternaam niet juist opgeslagen", actueleKlant4.getAchternaam(), nieuweKlant4.getAchternaam());
        assertEquals("Klant4 id niet juist opgeslagen", actueleKlant4.getId(), nieuweKlant4.getId());
    }

    @Test
    public void testGetAlleKlanten() {
        ArrayList<Klant> actueleWaarden = kdao.getAlleKlanten();
        ArrayList<Klant> testlijst = new ArrayList<Klant>();
        testlijst.add(nieuweKlant1);
        testlijst.add(nieuweKlant2);
        testlijst.add(nieuweKlant3);

        for (int i = 0; i < testlijst.size(); i++) {
            assertEquals("Klant voornaam niet juist opgeslagen", actueleWaarden.get(i).getVoornaam(), testlijst.get(i).getVoornaam());
            assertEquals("Klant tussenvoegsel niet juist opgeslagen", actueleWaarden.get(i).getTussenvoegsel(), testlijst.get(i).getTussenvoegsel());
            assertEquals("Klant achternaam niet juist opgeslagen", actueleWaarden.get(i).getAchternaam(), testlijst.get(i).getAchternaam());
            assertEquals("Klant id niet juist opgeslagen", actueleWaarden.get(i).getId(), testlijst.get(i).getId());
            assertTrue("klantobjecten verschillen", testlijst.get(i).equals(actueleWaarden.get(i)));
        }

    }

    @Test
    public void testUpdateKlantStringStringStringIntInt() {
        boolean updatesucces = kdao.updateKlant("boris", "van der", "test-update", nieuweAccount1.getId(), nieuweKlant1.getId());
        assertTrue("klant niet ge-update", updatesucces);
    }

    @Test
    public void testUpdateKlantKlantInt() {
        nieuweKlant1.setAchternaam("update-test");
        boolean updatesucces = kdao.updateKlant(nieuweKlant1);
        assertTrue("klant niet ge-update", updatesucces);
    }

    @Test
    public void testDeleteKlantInt() {
        kdao.createKlant(nieuweKlant1);
        boolean deleteklantsucces = kdao.deleteKlant(nieuweKlant1.getId());
        assertTrue("klant 1 niet deleted", deleteklantsucces);
    }

    @Test
    public void testDeleteKlantKlant() {
        kdao.createKlant(nieuweKlant1);
        boolean deleteklantsucces = kdao.deleteKlant(nieuweKlant1);
        assertTrue("klant 1 niet deleted", deleteklantsucces);
    }

}
