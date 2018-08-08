package data;
import java.sql.*;
import data.Connector;
import dataMySQL.ConnectieDatabase;
import dataMySQL.HikariCpConnector;

public class ConnectieFactory {
	
	private static Connector connector;
//	DatabaseSoort ds = DatabaseSoort.MYSQLDB;
	private static boolean connectionPoolAan = true;
	
	public static Connection getConnection() {
		if(connector ==  null) {
			setConnector();
		}
		return connector.getConnection();
	}
	public static void setConnectiePool(boolean connectionAan) {
		connectionPoolAan = connectionAan;
		setConnector();
	}
	

	private static void setConnector() {
		if(connectionPoolAan) {
			connector = new HikariCpConnector();
		}
		else
			connector = new ConnectieDatabase();
	}
}
