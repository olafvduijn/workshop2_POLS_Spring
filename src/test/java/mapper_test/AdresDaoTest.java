package mapper_test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import data.*;
import dataMySQL.AccountDaoImplement;
import dataMySQL.AdresDaoImplement;
import dataMySQL.ConnectieDatabase;
import dataMySQL.KlantDaoImplement;
import domein.*;
import domein.Account.Rol;
import domein.Adres.AdresType;

public class AdresDaoTest {

//	static Connection con;
	static int klantId=-1;
	static int accountId=-1;
	static AccountDaoImplement accountDao;
	static KlantDaoImplement klantDao;
	static AdresDaoImplement adresMapper;
	int adresId1=-1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
		//	ConnectieDatabase.maakVerbinding();
		//	con=ConnectieFactory.getConnection();
			
			Account nieuweAccount=new Account("testaccount","mijnWW", Rol.klant);
			accountDao=new AccountDaoImplement();
			accountDao.createAccount(nieuweAccount);
			accountId=nieuweAccount.getId();
			
			Klant nieuweKlant=new Klant("MijnVoornaam", "mijntussenvoegsel", "MijnAchternaam", nieuweAccount.getId());
			klantDao=new KlantDaoImplement();
			klantDao.createKlant(nieuweKlant);
			klantId=nieuweKlant.getId();
			
			adresMapper=new AdresDaoImplement();
		}
		catch (Exception e){
			throw new Exception("Kon de startwaarden niet aanmaken voor de AdresMapperTest");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			accountDao.deleteAccount(accountId);	
		}
		catch (Exception e){
			throw new Exception("Kon de startwaarden niet verwijderen voor de AdresMapperTest");
		}
		
	}

	@Before
	public void setUp() throws Exception {
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementCreateAdres1=con.prepareStatement("INSERT INTO adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats, adrestype, Klant_idKlant) VALUES (\"mijnstraat\",33,\"1 hoog\",\"9784RT\",\"Mijn Dorp\",\"factuuradres\"," + klantId + ")" );){
			pStatementCreateAdres1.execute();
			
			PreparedStatement pStatementGetIdAdres1=con.prepareStatement("select id from adres where straatnaam=\"mijnstraat\" && huisnummer=33 && toevoeging=\"1 hoog\" && postcode=\"9784RT\" && woonplaats=\"Mijn Dorp\" && Adrestype=\"factuuradres\" && Klant_idKlant=" + klantId);
			ResultSet resultSetIdAdres1 = pStatementGetIdAdres1.executeQuery();
			if (resultSetIdAdres1.isBeforeFirst()) {
				resultSetIdAdres1.next();

				adresId1 = resultSetIdAdres1.getInt(1);
			}
			
		}
	
		catch (Exception e){
			e.printStackTrace();
			throw new Exception("Kon de startwaarden voor de individuele tests voor de AdresMapperTest niet aanmaken");
			
		}
	}

	@After
	public void tearDown() throws Exception {
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementDeleteAdressen=con.prepareStatement("delete from adres where Klant_idKlant=" + klantId);){
			pStatementDeleteAdressen.execute();
		}
		catch (Exception e){
			throw new Exception("Kon de startwaarden voor de individuele tests voor de AdresMapperTest niet verwijderen");
		}
	}

	@Test
	public void testCreateAdres() throws SQLException {
		AdresType adresType=AdresType.FACTUURADRES;
		String straatnaam="nieuwe straat";
		int huisnummer=11;
		String postcode="5678FG";
		String plaats="De stad";
		Adres nieuwAdres=new Adres (adresType, straatnaam , huisnummer, postcode, plaats, klantId);
		
		assertTrue("Er is helemaal geen nieuw adres gecreëerd", adresMapper.createAdres(nieuwAdres, klantId));
		try ( Connection con= ConnectieFactory.getConnection();
		PreparedStatement pStatementGetAdresWaarden=con.prepareStatement("SELECT * FROM adres WHERE straatnaam=\"" + straatnaam + "\" && Klant_idKlant=" + klantId);){
		ResultSet resultSetAdresWaarden = pStatementGetAdresWaarden.executeQuery();
		Adres actueelAdres=null;
		int actueleId;
		String actueleStraatnaam;
		int actuelehuisnummer;
		String actueleToevoeging;
		String actuelePostcode;
		String actueleWoonplaats;
		AdresType actueleAdresType;
		if (resultSetAdresWaarden.isBeforeFirst()) {
			resultSetAdresWaarden.next();
			actueleId=resultSetAdresWaarden.getInt(1);
			actueleStraatnaam=resultSetAdresWaarden.getString(2);
			actuelehuisnummer=resultSetAdresWaarden.getInt(3);
			actueleToevoeging=resultSetAdresWaarden.getString(4);
			actuelePostcode=resultSetAdresWaarden.getString(5);
			actueleWoonplaats=resultSetAdresWaarden.getString(6);
			if (resultSetAdresWaarden.getString(6).equals("postadres")) {
				actueleAdresType=AdresType.POSTADRES;
			}
			else if (resultSetAdresWaarden.getString(6).equals("bezorgadres")) {
				actueleAdresType=AdresType.BEZORGADRES;
			}
			else {
				actueleAdresType=AdresType.FACTUURADRES;
			}
			actueelAdres=new Adres(actueleAdresType, actueleStraatnaam , actuelehuisnummer , actueleToevoeging , actuelePostcode , actueleWoonplaats, klantId);
			actueelAdres.setId(actueleId);
		}
		assertTrue("nieuw adres wordt niet juist opgeslagen", nieuwAdres.equals(actueelAdres));
		
		
		
		}
		
	}

	@Test
	public void testGetAdres() throws SQLException {
		Adres verwachtAdres= new Adres(AdresType.FACTUURADRES, "mijnstraat",33,"1 hoog","9784RT","Mijn Dorp", klantId);
		verwachtAdres.setId(adresId1);
		assertTrue("Het ophalen van het adres leverde ongelijke waarden voor adres op", adresMapper.getAdres(klantId, AdresType.FACTUURADRES).equals(verwachtAdres));
	}

	@Test
	public void testUpdateAdres() throws SQLException {
		String updatedStraatNaam="updated straatnaam";
		Adres verwachtAdres=new Adres (AdresType.FACTUURADRES, updatedStraatNaam , 666, "0101GH", "updated plaats", klantId);

		assertTrue("Het gewijzigde adres wordt niet opgeslagen", adresMapper.updateAdres(verwachtAdres, adresId1));
		try ( Connection con= ConnectieFactory.getConnection();
		PreparedStatement pStatementGetAdresWaarden=con.prepareStatement("SELECT * FROM adres WHERE straatnaam=\"" + updatedStraatNaam + "\" && Klant_idKlant=" + klantId);){
		ResultSet resultSetAdresWaarden = pStatementGetAdresWaarden.executeQuery();
		Adres actueelAdres=null;
		int actueleId;
		String actueleStraatnaam;
		int actuelehuisnummer;
		String actueleToevoeging;
		String actuelePostcode;
		String actueleWoonplaats;
		AdresType actueleAdresType;
		if (resultSetAdresWaarden.isBeforeFirst()) {
			resultSetAdresWaarden.next();
			actueleId=resultSetAdresWaarden.getInt(1);
			actueleStraatnaam=resultSetAdresWaarden.getString(2);
			actuelehuisnummer=resultSetAdresWaarden.getInt(3);
			actueleToevoeging=resultSetAdresWaarden.getString(4);
			actuelePostcode=resultSetAdresWaarden.getString(5);
			actueleWoonplaats=resultSetAdresWaarden.getString(6);
			if (resultSetAdresWaarden.getString(6).equals("postadres")) {
				actueleAdresType=AdresType.POSTADRES;
			}
			else if (resultSetAdresWaarden.getString(6).equals("bezorgadres")) {
				actueleAdresType=AdresType.BEZORGADRES;
			}
			else {
				actueleAdresType=AdresType.FACTUURADRES;
			}
			actueelAdres=new Adres(actueleAdresType, actueleStraatnaam , actuelehuisnummer , actueleToevoeging , actuelePostcode , actueleWoonplaats, klantId);
			actueelAdres.setId(actueleId);
		}
		assertTrue("Het gewijzigde adres wordt niet juist opgeslagen", verwachtAdres.equals(actueelAdres));
		}
	}

	@Test
	public void testDeleteAdres() throws SQLException {
		assertTrue("Het verwijderen van het adres kon niet worden opgeslagen", adresMapper.deleteAdres(adresId1));
		try ( Connection con= ConnectieFactory.getConnection();
		PreparedStatement pStatementGetAdresWaarden=con.prepareStatement("SELECT * FROM adres WHERE id="+adresId1);){
		ResultSet resultSetAdresWaarden = pStatementGetAdresWaarden.executeQuery();
		if (resultSetAdresWaarden.isBeforeFirst()) {
			resultSetAdresWaarden.next();
			assertNull("Het verwijderde adres kon toch worden teruggevonden", resultSetAdresWaarden.getObject(1));
		}
	}
	}
}
