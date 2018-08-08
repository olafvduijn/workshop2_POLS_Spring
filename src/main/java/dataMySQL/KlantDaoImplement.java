package dataMySQL;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import data.ConnectieFactory;
import data.KlantDao;
import domein.Adres;
import domein.Klant;
import domein.Adres.AdresType;
import domein.Bestelling;

public class KlantDaoImplement implements KlantDao{
//private  static Connection con = ConnectieFactory.getConnection();
	
	
	public int createKlant(Klant klant){
		
		int insertId = -1;
		String sql = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, account_id) VALUES (?,?,?,?);";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
			stmt.setObject(1, klant.getVoornaam());
			stmt.setObject(2, klant.getTussenvoegsel());
			stmt.setObject(3, klant.getAchternaam());
			stmt.setObject(4, klant.getAccountId());
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                insertId = resultSet.getInt(1);
               // System.out.println("Id " + insertId + " voor klant " + klant.getVoornaam());
                klant.setId(insertId);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertId;
	}
	
	public Klant getKlant(int id){
		String sql = "SELECT * FROM klant WHERE id=?";
		Klant returnedKlant = null;
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, id);
			ResultSet resultSet = stmt.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();

                int id1 = resultSet.getInt(1);
                String voornaam =  resultSet.getString(2);
                String tussenvoegsel =  resultSet.getString(3);
                String achternaam =  resultSet.getString(4);
                int accountid = resultSet.getInt(5);
                returnedKlant = new Klant (voornaam,tussenvoegsel,achternaam, accountid);
                
                
                returnedKlant.setId(id1);
                
                //System.out.println("Klant gevonden: " + returnedKlant.getVoornaam());
            }
            else{
            	System.err.println("Geen Klant gevonden!");
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnedKlant;
	}
	
	public ArrayList<Klant> getAlleKlanten(){
		String sql = "SELECT * FROM klant;";
		ArrayList<Klant> returnedKlanten = new ArrayList<>();
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
            	
            	int id1 = resultSet.getInt(1);
            	 String voornaam =  resultSet.getString(2);
                 String tussenvoegsel =  resultSet.getString(3);
                 String achternaam =  resultSet.getString(4);
                 int accountId = resultSet.getInt(5);
                 
                Klant returnedKlant = new Klant (voornaam,tussenvoegsel,achternaam,accountId);
               
            	returnedKlant.setId(id1);
            	
            	//System.out.println("Klant gevonden: " + returnedKlant.getvoornaam());
            	returnedKlanten.add(returnedKlant);
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnedKlanten;
	}
	
	public boolean updateKlant(String voornaam, String tussenvoegsel, String achternaam, int accountId, int id){
		String sql = "UPDATE klant SET voornaam = ?, tussenvoegsel = ?, achternaam = ?, account_id = ? WHERE id = ?";
		int rows = -1;
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, voornaam);
			stmt.setObject(2, tussenvoegsel);
			stmt.setObject(3, achternaam);
			stmt.setObject(4, accountId);
			stmt.setObject(5, id);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			
		}
		return rows > 0;
	}
	
	public boolean updateKlant(Klant nieuwKlant){
		return updateKlant(nieuwKlant.getVoornaam(), nieuwKlant.getTussenvoegsel(), nieuwKlant.getAchternaam(), nieuwKlant.getAccountId() , nieuwKlant.getId());
	}
	
	public boolean deleteKlant(int id){
		String sql = "DELETE FROM klant WHERE id = ?";
		int rows = -1;
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, id);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0;
	}
	
	public boolean deleteKlant(Klant klant){
		return deleteKlant(klant.getId());
	}
/*	
	public ArrayList <Adres> getAdressen(int klantId){
		Klant klant=getKlant(klantId); // Is dit niet verkeerd, omdat ik een nieuw klant object aanmaak, waardoor er nu 2 gelijke zijn?
		String sql = "SELECT * FROM Adres WHERE Klant_idKlant = ? && Adrestype=\"postadres\"";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt=con.prepareStatement(sql);){
			stmt.setObject(1, klantId);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.isBeforeFirst()) {
                resultSet.next();
                int id = resultSet.getInt(1);
                String straatnaam = resultSet.getString(2);
                int huisnummer=resultSet.getInt(3);
                String toevoeging=resultSet.getString(4);
                String postcode=resultSet.getString(5);
                String woonplaats=resultSet.getString(6);
                klant.maakAdresAan(AdresType.POSTADRES, straatnaam, huisnummer, toevoeging, postcode, woonplaats);
                Adres adres=klant.getAdres(AdresType.POSTADRES);
                adres.setId(id);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			}
		sql = "SELECT * FROM Adres WHERE Klant_idKlant = ? && Adrestype=\"factuuradres\"";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt=con.prepareStatement(sql);){
			stmt.setObject(1, klantId);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.isBeforeFirst()) {
                resultSet.next();
                int id = resultSet.getInt(1);
                String straatnaam = resultSet.getString(2);
                int huisnummer=resultSet.getInt(3);
                String toevoeging=resultSet.getString(4);
                String postcode=resultSet.getString(5);
                String woonplaats=resultSet.getString(6);
                klant.maakAdresAan(AdresType.FACTUURADRES, straatnaam, huisnummer, toevoeging, postcode, woonplaats);
                Adres adres=klant.getAdres(AdresType.FACTUURADRES);
                adres.setId(id);
			}
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
		sql = "SELECT * FROM Adres WHERE Klant_idKlant = ? && Adrestype=\"bezorgadres\"";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt=con.prepareStatement(sql);){
			stmt.setObject(1, klantId);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.isBeforeFirst()) {
                resultSet.next();
                int id = resultSet.getInt(1);
                String straatnaam = resultSet.getString(2);
                int huisnummer=resultSet.getInt(3);
                String toevoeging=resultSet.getString(4);
                String postcode=resultSet.getString(5);
                String woonplaats=resultSet.getString(6);
                klant.maakAdresAan(AdresType.BEZORGADRES, straatnaam, huisnummer, toevoeging, postcode, woonplaats);
                Adres adres=klant.getAdres(AdresType.BEZORGADRES);
                adres.setId(id);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			}
		
		return klant.leesAlleAdressen();
	}
*/	
	
/*	public ArrayList <Bestelling> getBestellingen(int klantId){
		Klant klant=getKlant(klantId); // Is dit niet verkeerd, omdat ik een nieuw klant object aanmaak, waardoor er nu 2 gelijke zijn?
		String sql = "SELECT * FROM Bestelling WHERE Klant_idKlant = ?";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt=con.prepareStatement(sql);){
			stmt.setObject(1, klantId);
			ResultSet resultSet = stmt.executeQuery();
			int index=0;
			while (resultSet.next()) {
                int id = resultSet.getInt(1);
                double totaalPrijs=resultSet.getDouble(2);
                BigDecimal totPrijs=new BigDecimal(""+totaalPrijs);
                klant.maakBestellingAan();
                Bestelling bestelling=klant.getBestelling(index);
                bestelling.setId(id);
                bestelling.setTotaalPrijs(totPrijs);
                index++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return klant.leesAlleBestellingen();
	
	}*/
	public Klant getAlleKlantenPerAccount(int accountId){
		String sql = "SELECT * FROM klant WHERE account_id=?";
		Klant klanten = null;
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, accountId);
			ResultSet resultSet = stmt.executeQuery();
			
			 while(resultSet.next()){
	            	
				 int id1 = resultSet.getInt(1);
	                String voornaam =  resultSet.getString(2);
	                String tussenvoegsel =  resultSet.getString(3);
	                String achternaam =  resultSet.getString(4);
	                int accountid = resultSet.getInt(5);
	            	klanten = new Klant (voornaam,tussenvoegsel,achternaam, accountid);
	            	klanten.setId(id1);

	            	
	            }
	            
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			//System.out.println("klantID KlantDaoImpl: "+returnedKlant.get(0).getId()+System.lineSeparator()+"accountId: "+accountId+System.lineSeparator()+"klantvoornaam: "+returnedKlant.get(0).getVoornaam());
		return klanten;
		}
	
}
