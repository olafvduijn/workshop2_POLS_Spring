package dataMongoDB;


import com.mongodb.client.*;

import data.DOM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

public class ConnectieDatabaseMongoImplement {
	
	MongoClient mc;
	Logger logger = LoggerFactory.getLogger(Logger.class.getName());

   
	   public MongoDatabase getMongoDB() {
		   DOM dom = new DOM();
		   mc=new MongoClient(dom.getUrl("mongo"),dom.getPort("mongo"));
		   
		   MongoDatabase db = mc.getDatabase(dom.getDatabase("mongo"));
		   
		   
		   return db;
	   }
	   
	   public void close() {
		   mc.close();
	   }

	
}
