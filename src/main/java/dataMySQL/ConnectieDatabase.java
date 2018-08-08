package dataMySQL;
import java.sql.*;

import data.Connector;
import data.DOM;

public  class ConnectieDatabase implements Connector{
	
	 private  Connection con=null;
	 
	 public ConnectieDatabase() {
		 
	 }
	 
	  /**
	   * Maakt verbinding met de database op basis van de waarden via DOM parser
	   */
	 public  void maakVerbinding() throws SQLException {
		 DOM parser = new DOM();
	     String url = parser.getUrl("mysql");
	     String username = parser.getUsername("mysql");
	     String wachtwoord = parser.getPassword("mysql");
		 try {
		     con=DriverManager.getConnection(url, username, wachtwoord);
		 }
		 catch (SQLException e) {
			 throw new SQLException("Verbinding maken mislukt");
		 }
		 // check is de connectie nog actief !null of zelfde connectie teruggeven//
	 }
	 
	 
	/*  public void sluitAf() {
	    if(con!=null){
	      try{
	        con.close();
	      }
	      catch (SQLException e){
	        
	      }
	    }
	    
	  }
	  */
	  public  Connection getConnection(){
		  try {
	            if (con == null || con.isClosed()) {
	                maakVerbinding();
	            }
	        } catch (SQLException sqlEx) {
	            sqlEx.printStackTrace();
	        }
	        return con;
		  
		 
	  }
	 
	  

	
}
