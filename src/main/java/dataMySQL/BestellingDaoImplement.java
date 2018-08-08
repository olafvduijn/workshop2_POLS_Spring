package dataMySQL;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import data.BestellingDao;
import data.ConnectieFactory;
import domein.Bestelling;
import domein.Klant;


public class BestellingDaoImplement implements BestellingDao{
	
//	private  static Connection con = ConnectieFactory.getConnection();
	
	public int createBestelling(Bestelling bestelling){		
		int insertId = -1;
		String sql = "INSERT INTO bestelling (totaalPrijs,Klant_idKlant) VALUES (?,?);";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
			stmt.setObject(1, bestelling.getTotaalPrijs());
			stmt.setObject(2, bestelling.GetKlantId());    
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                insertId = resultSet.getInt(1);
                bestelling.setId(insertId);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertId;
	}
	
	public Bestelling getBestelling(int bestellingId){
		String sql = "SELECT * FROM bestelling WHERE id=?";
		Bestelling returnedBestelling = null;
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, bestellingId);
			ResultSet resultSet = stmt.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();

                int id1 = resultSet.getInt(1);
                 BigDecimal totaalprijs =  resultSet.getBigDecimal(2);
               int klant_id =  resultSet.getInt(3);             
                returnedBestelling = new Bestelling (id1, totaalprijs, klant_id);
                
                
            //    returnedBestelling.setId(id1);
                
            //    System.out.println("Bestelling gevonden: " + returnedBestelling.getBestellingNummer());
            }
            else{
            	System.err.println("Geen Bestelling gevonden!");
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnedBestelling;
	}
	
	public ArrayList<Bestelling> getAlleBestelling(){
		String sql = "SELECT * FROM bestelling;";
		ArrayList<Bestelling> returnedBestelling = new ArrayList<>();
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
            	
            	int bestellingNummer = resultSet.getInt(1);
            	 BigDecimal totaalprijs =  resultSet.getBigDecimal(2);
                int klantid =  resultSet.getInt(3);
                Bestelling bestellingen = new Bestelling (bestellingNummer,totaalprijs,klantid);
            	//Bestelling bestellingen = new Bestelling ();
                //bestellingen.setId(bestellingNummer);
            	
            	//System.out.println("Bestellingen gevonden: " + bestellingen.getBestellingNummer());
            	returnedBestelling.add(bestellingen);
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnedBestelling;
	}
	
	public boolean updateBestellingen(BigDecimal totaalprijs, int id){
		String sql = "UPDATE bestelling SET totaalprijs = ? WHERE id = ?";
		int rows = -1;
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, totaalprijs);
			stmt.setObject(2, id);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			
		}
		return rows > 0;
	}
	
	public boolean updateBestellingen(Bestelling bestelling){
		return updateBestellingen(bestelling.getTotaalPrijs(), bestelling.getId());
	}
	
	public boolean deleteBestellingen(int id){
		String sql = "DELETE FROM bestelling WHERE id = ?";
		int rows = -1;
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, id);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0;
	}
	
	public boolean deleteBestellingen(Bestelling bestellingen){
		return deleteBestellingen(bestellingen.getId());
	}
	
	public ArrayList<Bestelling> getAlleBestellingenPerKlant(Klant klant){
		String sql = "SELECT * FROM bestelling WHERE Klant_idKlant=?";
		ArrayList<Bestelling> returnedBestelling = new ArrayList<>();
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, klant.getId());
			ResultSet resultSet = stmt.executeQuery();
			 while(resultSet.next()){
	            	
	            	int bestellingId = resultSet.getInt(1);
	            	 BigDecimal totaalprijs =  resultSet.getBigDecimal(2);
	               int klantid =  resultSet.getInt(3);
	               // Bestellingen bestellingen = new Bestellingen (bestellingNummer,totaalprijs,klantid);
	            	Bestelling bestellingen = new Bestelling (bestellingId, totaalprijs, klant.getId());
	               // bestellingen.setId(bestellingId);
	            	
	            	//System.out.println("Bestellingen gevonden: " + bestellingen.getBestellingNummer());
	            	returnedBestelling.add(bestellingen);
	            }
	            
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnedBestelling;
		}
}

