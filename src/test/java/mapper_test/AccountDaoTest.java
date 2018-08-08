package mapper_test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import data.AccountDao;
import data.DaoFactory;
import dataMySQL.AccountDaoImplement;
import dataMySQL.ConnectieDatabase;
import domein.Account;
import domein.Account.Rol;

import org.junit.*;

public class AccountDaoTest {
	
	ArrayList <Account> testlijst;
	ArrayList <Account> actueleWaarden;
	AccountDao adao;
	Account nieuweAccount1=new Account ("Boer Piet", "simpel", Account.Rol.beheerder);
	Account nieuweAccount2=new Account ("medewerker 1", "simpel", Account.Rol.medewerker);
	Account nieuweAccount3=new Account ("klant 1", "simpel", Account.Rol.klant);
	Account nieuweAccount4=new Account ("klant 2", "simpel", Account.Rol.klant);
	//Account nieuweAccount5=new Account ("klant 3", "moeilijk", Account.Rol.medewerker);
	Account nieuweAccount7=new Account ("klant 4", "easy", Account.Rol.medewerker);
	Account nieuweAccount6;
	Account nieuweAccount8=new Account ("klant 5", "easy", Account.Rol.medewerker);
	//static ConnectieDatabase cdb=new ConnectieDatabase();
//	AccountDao adao=DaoFactory.getAccountDao();

	/*
	@BeforeClass
	public static void initialiseer() throws SQLException {
		try (Connection con = ConnectieDatabase.getConnection();){
			
		}
			
		
	}
	*/
	
	
	@Before
	public void setUp(){
		//testlijst=new ArrayList <Account>();
		//actueleWaarden=new ArrayList <Account>();
		//testlijst.add(nieuweAccount1);
		//testlijst.add(nieuweAccount2);
		//testlijst.add(nieuweAccount3);
		DaoFactory.setDatabaseMYSQL(false); //deze regel al dan niet activeren voor Mongo test
		adao=DaoFactory.getAccountDao();
		adao.createAccount(nieuweAccount1);
		adao.createAccount(nieuweAccount2);
		adao.createAccount(nieuweAccount3);
		adao.createAccount(nieuweAccount4);
		adao.createAccount(nieuweAccount7);
		//adao.createAccount(nieuweAccount8);
	}
	
	@After
	public void finish() {
		adao.deleteAccount(nieuweAccount1);
		adao.deleteAccount(nieuweAccount2);
		adao.deleteAccount(nieuweAccount3);
		adao.deleteAccount(nieuweAccount4);
		//adao.deleteAccount(nieuweAccount5);
		adao.deleteAccount(nieuweAccount7);
	//	adao.deleteAccount(nieuweAccount8);
		

	}
	
	

	@Test
	public void testCreateAccount() {
		//Account[]actueleWaarden= {nieuweAccount1,nieuweAccount2,nieuweAccount3};
		//Account[]testlijst={nieuweAccount1,nieuweAccount2,nieuweAccount3};
		//actueleWaarden.add(adao.getAccount(nieuweAccount1.getId()));
		//actueleWaarden.add(adao.getAccount(nieuweAccount2.getId()));
		//actueleWaarden.add(adao.getAccount(nieuweAccount3.getId()));
		//assertArrayEquals("Kon geen nieuwe accounts opslaan", actueleWaarden.toArray(), testlijst.toArray());
		Account actueleAccount1=adao.getAccount(nieuweAccount1.getId());
		Account actueleAccount2=adao.getAccount(nieuweAccount2.getId());
		Account actueleAccount3=adao.getAccount(nieuweAccount3.getId());
		
		assertEquals("boer Piet gebruikersnaam niet juist opgeslagen", actueleAccount1.getUserNaam(),nieuweAccount1.getUserNaam());
		assertEquals("boer Piet id niet juist opgeslagen", actueleAccount1.getId(),nieuweAccount1.getId());
		assertEquals("boer Piet password niet juist opgeslagen", actueleAccount1.getPassword() ,nieuweAccount1.getPassword());
		assertEquals("boer Piet rol niet juist opgeslagen", actueleAccount1.getRol(),nieuweAccount1.getRol());
		
		assertEquals("medewerker gebruikersnaam niet juist opgeslagen", actueleAccount2.getUserNaam(),nieuweAccount2.getUserNaam());
		assertEquals("medewerker id niet juist opgeslagen", actueleAccount2.getId(),nieuweAccount2.getId());
		assertEquals("medewerker password niet juist opgeslagen", actueleAccount2.getPassword() ,nieuweAccount2.getPassword());
		assertEquals("medewerker rol niet juist opgeslagen", actueleAccount2.getRol(),nieuweAccount2.getRol());
		
		assertEquals("medewerker gebruikersnaam niet juist opgeslagen", actueleAccount3.getUserNaam(),nieuweAccount3.getUserNaam());
		assertEquals("medewerker id niet juist opgeslagen", actueleAccount3.getId(),nieuweAccount3.getId());
		assertEquals("medewerker password niet juist opgeslagen", actueleAccount3.getPassword() ,nieuweAccount3.getPassword());
		assertEquals("medewerker rol niet juist opgeslagen", actueleAccount3.getRol(),nieuweAccount3.getRol());
	}
	

	@Test
	public void testUpdateAccountIntStringStringRol() {
		adao.updateAccount(nieuweAccount4.getId(),"nieuwe usernaam","hallo", Account.Rol.medewerker);
		assertEquals("klant 2 gebruikersnaam niet gewijzigd",adao.getAccount(nieuweAccount4.getId()).getUserNaam(),"nieuwe usernaam");
		assertEquals("klant 2 password niet gewijzigd",adao.getAccount(nieuweAccount4.getId()).getPassword(),"hallo");
		assertEquals("klant 2 rol niet gewijzigd",adao.getAccount(nieuweAccount4.getId()).getRol(),Account.Rol.medewerker);
	}

	@Test
	public void testUpdateAccountAccount() {
		boolean updatesucces = adao.updateAccount(nieuweAccount7.getId(),"nieuwe usernaam2","meme", Account.Rol.medewerker);
		assertTrue("klant 4 gebruikersnaam niet gewijzigd",updatesucces);	
		
	}

	@Test
	public void testDeleteAccountInt() {
		adao.createAccount(nieuweAccount8);
		boolean deleteaccount = adao.deleteAccount(nieuweAccount8.getId());
		 assertTrue("klant 5 gebruikersnaam niet deleted",deleteaccount);	
		
	}

	@Test
	public void testDeleteAccountAccount() {
		nieuweAccount6=new Account ("klant 6", "moeilijk", Account.Rol.klant);
		adao.createAccount(nieuweAccount6);
		boolean deleteaccount = adao.deleteAccount(nieuweAccount6);
		 assertTrue("klant 10 gebruikersnaam niet deleted",deleteaccount);	
	}

}
