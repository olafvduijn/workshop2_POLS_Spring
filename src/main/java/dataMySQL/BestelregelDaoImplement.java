package dataMySQL;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import data.BestelregelDao;
import data.ConnectieFactory;
import domein.BestelRegel;
import domein.Bestelling;
import domein.Artikel;

public class BestelregelDaoImplement implements BestelregelDao{
//private  static Connection con = ConnectieFactory.getConnection();
	
	public int createBestelregel(BestelRegel bestelregel){		
		int insertId = -1;
		String sql = "INSERT INTO bestelregel (aantal,prijs,Bestelling_idBestelling,Artikel_idArtikel) VALUES (?,?,?,?);";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
			stmt.setObject(1, bestelregel.getAantal());
			stmt.setObject(2, bestelregel.getPrijs());
			stmt.setObject(3, bestelregel.getBestellingId());
			stmt.setObject(4, bestelregel.getArtikel().getId());		// of moeten we hier werken met artikelnummer in de methode?
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                insertId = resultSet.getInt(1);
                 bestelregel.setId(insertId);
               
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertId;
	}
	
	public BestelRegel getBestelRegel(int id){
		String sql = "SELECT * FROM bestelregel WHERE id=?";
		BestelRegel returnedBestelRegel = null;
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, id);
			ResultSet resultSet = stmt.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();

                int id1 = resultSet.getInt(1);
                int aantal =  resultSet.getInt(2);
                BigDecimal totaalPrijs =  resultSet.getBigDecimal(3);
                int bestellingnummer = resultSet.getInt(4);
                int artikelnummer = resultSet.getInt(5);
                Artikel artikel =new Artikel(artikelnummer);
               returnedBestelRegel = new BestelRegel (aantal, bestellingnummer, artikel, totaalPrijs);
           //ToDo     nieuwe constructor met artikelnummer of artikel behorende bij artikelnummer
                //hier opvragen met artikeldao??
                
                returnedBestelRegel.setId(id1);
                
            //    System.out.println("BestelRegel gevonden: " + returnedBestelRegel.getId());
            }
            else{
            	System.err.println("Geen Bestelregel gevonden!");
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnedBestelRegel;
	}
	
	public ArrayList<BestelRegel> getAlleBestelRegel(){
		String sql = "SELECT * FROM bestelregel;";
		ArrayList<BestelRegel> returnedBestelRegel = new ArrayList<>();
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
            	

                int id1 = resultSet.getInt(1);
                int aantal =  resultSet.getInt(2);
                BigDecimal totaalPrijs =  resultSet.getBigDecimal(3);
                int bestellingnummer = resultSet.getInt(4);
                int artikelnummer = resultSet.getInt(5);
                Artikel artikel = new Artikel (artikelnummer);
            	BestelRegel bestelregel = new BestelRegel (aantal, bestellingnummer,artikel,totaalPrijs );
                bestelregel.setId(id1);
            	
            	//System.out.println("Bestellingen gevonden: " + bestellingen.getBestellingNummer());
            	returnedBestelRegel.add(bestelregel);
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnedBestelRegel;
	}
	
	public boolean updateBestelRegel(BestelRegel bestelregel){
		String sql = "UPDATE bestelregel SET aantal = ?, prijs = ?, Artikel_idArtikel = ? WHERE id = ?";
		int rows = -1;
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, bestelregel.getAantal());
			stmt.setObject(2, bestelregel.getPrijs());
			stmt.setObject(3, bestelregel.getArtikel().getId());
			stmt.setObject(4, bestelregel.getId());
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			
		}
		return rows > 0;
	}
	
/*	public boolean updateBestelRegel(BestelRegel bestelregel){
		return updateBestelRegel(bestelregel.getAantal(), bestelregel.getId());
	}
*/	
	public boolean deleteBestelRegel(int id){
		String sql = "DELETE FROM bestelregel WHERE id = ?";
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
	
	public boolean deleteBestelRegel(BestelRegel bestelregel){
		return deleteBestelRegel(bestelregel.getId());
	}
	public ArrayList<BestelRegel> getAlleBestelregelsPerBestelling(int bestellingId){
		String sql = "SELECT * FROM bestelregel WHERE Bestelling_idBestelling=?";
		ArrayList<BestelRegel> returnedBestelregel = new ArrayList<>();
		try (Connection con= ConnectieFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setObject(1, bestellingId);
			ResultSet resultSet = stmt.executeQuery();
			 while(resultSet.next()){
	            	
	            	int bestelregelId = resultSet.getInt(1);
	            	int aantal =  resultSet.getInt(2);
	                BigDecimal totaalPrijs =  resultSet.getBigDecimal(3);
	                int bestellingnummer = resultSet.getInt(4);
	                int artikelnummer = resultSet.getInt(5);
	                Artikel artikel =new Artikel(artikelnummer);
	              BestelRegel returnedBestelRegel = new BestelRegel (aantal,bestellingId,artikel,totaalPrijs);
	              returnedBestelRegel.setId(bestelregelId);
	            	returnedBestelregel.add(returnedBestelRegel);
	            }
	            
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnedBestelregel;
		}
}
