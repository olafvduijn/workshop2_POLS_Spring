package dataMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.AdresDao;
import data.ConnectieFactory;
import domein.Adres;
import domein.Adres.AdresType;

public class AdresDaoImplement implements AdresDao {

//	private  Connection con;
//	private  PreparedStatement pStatementVoegToe;
//	private  PreparedStatement pStatementGet;
//	private  PreparedStatement pStatementUpdate;
//	private  PreparedStatement pStatementDeleteAdres;

	
/*	private  void initialiseer() throws SQLException{
		ConnectieFactory.maakVerbinding();
		con=ConnectieFactory.getConnection();
		try {
			pStatementVoegToe=con.prepareStatement("INSERT INTO adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats, adrestype, Klant_idKlant) VALUES (?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
			pStatementGet=con.prepareStatement("SELECT * FROM adres WHERE id=?;");
			pStatementUpdate=con.prepareStatement("UPDATE adres SET straatnaam=?, huisnummer=?, toevoeging=?,postcode=?,woonplaats=?,adrestype=? WHERE id=?");
			pStatementDeleteAdres=con.prepareStatement("DELETE FROM adres where id=?");
	    }
	    catch (SQLException e){
	    	einde();
	    	throw new SQLException ("Fout bij formuleren van een SQL-opdracht.");
	    }
	}
	
	private  void einde() {
		ConnectieFactory.sluitAf();
	}
*/	
	
	
	public  boolean createAdres(Adres adres, int klantid) {
		boolean created=false;
		int insertId = -1;
	//	initialiseer();
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementVoegToe=con.prepareStatement("INSERT INTO adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats, adrestype, Klant_idKlant) VALUES (?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);){
			pStatementVoegToe.setObject(1, adres.getStraatnaam());
			pStatementVoegToe.setObject(2, adres.getHuisnummer());
			pStatementVoegToe.setObject(3, adres.getToevoeging());
			pStatementVoegToe.setObject(4, adres.getPostcode());
			pStatementVoegToe.setObject(5, adres.getWoonplaats());
			pStatementVoegToe.setObject(6, adres.getAdresType().toString());
			pStatementVoegToe.setObject(7, klantid);
			pStatementVoegToe.executeUpdate();
			ResultSet resultSet = pStatementVoegToe.getGeneratedKeys();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                insertId = resultSet.getInt(1);
                adres.setId(insertId);
            }
            created=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return created;
	}
	
	
	public  Adres getAdres(int klantid, AdresType adrestype) {
		Adres adres=null;
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementGet=con.prepareStatement("select * from adres where klant_idKlant=? && Adrestype=?");){
			pStatementGet.setObject(1, klantid);
			pStatementGet.setObject(2, adrestype.toString());

			ResultSet resultSet = pStatementGet.executeQuery();
			if (resultSet.isBeforeFirst()) {
                resultSet.next();

                int id1 = resultSet.getInt(1);
                String straatnaam =  resultSet.getString(2);
                int huisnummer =  resultSet.getInt(3);
                String toevoeging =  resultSet.getString(4);
                String postcode =  resultSet.getString(5);
                String woonplaats =  resultSet.getString(6);
                AdresType adresType =  Adres.AdresType.toAdresType(resultSet.getString(7));
                
                adres = new Adres (adresType,straatnaam,huisnummer,toevoeging, postcode, woonplaats, klantid);
                adres.setId(id1);
            }
            else{
            	throw new SQLException("Kon gevraagde adres niet vinden in database");
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adres;
	}
	
	
	public  boolean updateAdres(Adres gewijzigdAdres, int adresId) {
		boolean updated=false;
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementUpdate=con.prepareStatement("UPDATE adres SET straatnaam=?, huisnummer=?, toevoeging=?,postcode=?,woonplaats=?,adrestype=? WHERE id=?");){
			pStatementUpdate.setObject(1, gewijzigdAdres.getStraatnaam());
			pStatementUpdate.setObject(2, gewijzigdAdres.getHuisnummer());
			pStatementUpdate.setObject(3, gewijzigdAdres.getToevoeging());
			pStatementUpdate.setObject(4, gewijzigdAdres.getPostcode());
			pStatementUpdate.setObject(5, gewijzigdAdres.getWoonplaats());
			pStatementUpdate.setObject(6, gewijzigdAdres.getAdresType().toString());
			pStatementUpdate.setObject(7, adresId);
			pStatementUpdate.executeUpdate();
			updated=true;
			gewijzigdAdres.setId(adresId);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	
	
	public boolean deleteAdres(int id) {
		boolean deleted=false;
		try ( Connection con= ConnectieFactory.getConnection();
			PreparedStatement pStatementDeleteAdres=con.prepareStatement("DELETE FROM adres where id=?");){
			pStatementDeleteAdres.setObject(1, id);
			pStatementDeleteAdres.executeUpdate();
			deleted=true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return deleted;
	}
	
	public boolean deleteAdres(Adres teVerwijderenAdres) {
		return deleteAdres(teVerwijderenAdres.getId());
	}
	
	
	public boolean factuurAdresAanwezig(int klantid) {
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement pfactuurAdresAanwezig=con.prepareStatement("select * from adres where klant_idKlant= "+klantid+" && Adrestype=\"factuuradres\"");){
			ResultSet resultSet = pfactuurAdresAanwezig.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();;
		} 
		return false;
	}
	
	public boolean bezorgAdresAanwezig(int klantid) {
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement pbezorgAdresAanwezig=con.prepareStatement("select * from adres where klant_idKlant= "+klantid+" && Adrestype=\"bezorgadres\"");){
			ResultSet resultSet = pbezorgAdresAanwezig.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	public String toonPostadres(int klantId) {
		String adres="";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement ptoonadres=con.prepareStatement("select * from adres where klant_idKlant= "+klantId+" && Adrestype=\"postadres\"");){
			ResultSet resultSet=ptoonadres.executeQuery();
			if (resultSet.next()) {
				adres="straatnaam: "+resultSet.getString(2) + System.lineSeparator();
				adres=adres+"huisnummer: "+resultSet.getInt(3)+ System.lineSeparator();
				adres=adres+"toevoeging: "+resultSet.getString(4)+ System.lineSeparator();
				adres=adres+"postcode: "+resultSet.getString(5)+ System.lineSeparator();
				adres=adres+"plaats: "+resultSet.getString(6);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return adres;
	}
	
	public String toonFactuuradres(int klantId) {
		String adres="";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement ptoonadres=con.prepareStatement("select * from adres where klant_idKlant= "+klantId+" && Adrestype=\"factuuradres\"");){
			ResultSet resultSet=ptoonadres.executeQuery();
			if (resultSet.next()) {
				adres="straatnaam: "+resultSet.getString(2) + System.lineSeparator();
				adres=adres+"huisnummer: "+resultSet.getInt(3)+ System.lineSeparator();
				adres=adres+"toevoeging: "+resultSet.getString(4)+ System.lineSeparator();
				adres=adres+"postcode: "+resultSet.getString(5)+ System.lineSeparator();
				adres=adres+"plaats: "+resultSet.getString(6);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return adres;
	}

	public String toonBezorgadres(int klantId) {
		String adres="";
		try ( Connection con= ConnectieFactory.getConnection();
				PreparedStatement ptoonadres=con.prepareStatement("select * from adres where klant_idKlant= "+klantId+" && Adrestype=\"bezorgadres\"");){
			ResultSet resultSet=ptoonadres.executeQuery();
			if (resultSet.next()) {
				adres="straatnaam: "+resultSet.getString(2) + System.lineSeparator();
				adres=adres+"huisnummer: "+resultSet.getInt(3)+ System.lineSeparator();
				adres=adres+"toevoeging: "+resultSet.getString(4)+ System.lineSeparator();
				adres=adres+"postcode: "+resultSet.getString(5)+ System.lineSeparator();
				adres=adres+"plaats: "+resultSet.getString(6);
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return adres;
	}
	
}
