package dataMySQL;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import data.Connector;
import data.DOM;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HikariCpConnector implements Connector{
	private  HikariDataSource dataSource;
    private  String url;
    private  String username;
    private  String password;
    private  Logger log = LoggerFactory.getLogger(HikariCpConnector.class);
    
    
    public HikariCpConnector(){
       	DOM dom = new DOM();
        url = dom.getUrl();
        username = dom.getUsername();
        password = dom.getPassword();
    }
    
    public HikariCpConnector(String database){
        DOM dom = new DOM();
        url = dom.getUrl(database);
        username = dom.getUsername(database);
        password = dom.getPassword(database);
    } 
      
    private  HikariDataSource createDataSource(){
        if(dataSource == null){      
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(10);
            config.setAutoCommit(true);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
    
    public  Connection getConnection(){
        try {
            if(dataSource == null){
                return createDataSource().getConnection();
            }
            return dataSource.getConnection();
        }
        catch (SQLException ex) {
      //      log.error(ex.getMessage());
        }
        return null;
    }
    
    public  void close(){
        if(dataSource != null){
            dataSource.close();
        }
        dataSource = null;
    }

    public  void setDatabase(String database) {
        close();
        DOM dom = new DOM();
        url = dom.getUrl(database);
        username = dom.getUsername(database);
        password = dom.getPassword(database);
    //    log.info("Nieuwe database: " + database);
    }


    public  void setDatabase() {
        close();
        DOM dom = new DOM();
        url = dom.getUrl();
        username = dom.getUsername();
        password = dom.getPassword();
        log.info("Nieuwe database: default.");
    }
}
