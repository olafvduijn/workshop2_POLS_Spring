package dataMongoDB;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import data.BestellingDao;
import domein.Account;
import domein.Bestelling;
import domein.Klant;

public class BestellingDaoMongoImplement implements BestellingDao{
	
private ConnectieDatabaseMongoImplement mongoConnector;
	
	public BestellingDaoMongoImplement() {
        mongoConnector = new ConnectieDatabaseMongoImplement();
    }
	
	private Bestelling convertDocumentToBestelling(Document doc){
		if (doc!=null) {
			Bestelling bestelling= new Bestelling(doc.getInteger("id"),new BigDecimal ( (doc.getString("totaalprijs"))), (doc.getInteger("klantid")));
	        bestelling.setId(doc.getInteger("id"));
	        return bestelling;
		}
		else {
			return null;
		}
    }		
	
	 private Document convertBestellingToDocument(Bestelling bestelling){
	        Document document = new Document();
	        document.append("id", bestelling.getId());
	        document.append("totaalprijs", bestelling.getTotaalPrijs().toPlainString());
	        document.append("klantid", bestelling.GetKlantId());
	        return document;
	    }
	
	  private Integer getNextId(){
	        int id = 0;
	        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
	        if(collection.countDocuments() > 0){
	            Document highestId = collection.find().sort(orderBy(descending("id"))).first();
	            id = highestId.getInteger("id") + 1;
	        }
	        else {
	            id = 1;
	        }
	        mongoConnector.close();
	        return id;
	    }

	@Override
	public int createBestelling(Bestelling bestelling) {
		bestelling.setId(getNextId());
	     MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
	     collection.insertOne(convertBestellingToDocument(bestelling));
	     mongoConnector.close();
	     return bestelling.getId();
	}

	@Override
	public Bestelling getBestelling(int bestellingId) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
        Document doc = collection.find(eq("id", bestellingId)).first();
        mongoConnector.close();
        return convertDocumentToBestelling(doc);
	}

	@Override
	public boolean updateBestellingen(BigDecimal totaalprijs, int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
		
		Document doc = collection.find(eq("id", id)).first();
		
		Bestelling bestelling =convertDocumentToBestelling(doc);
		bestelling.setTotaalPrijs(totaalprijs);
		collection.findOneAndReplace(eq("id", bestelling.getId()), convertBestellingToDocument(bestelling));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean updateBestellingen(Bestelling bestelling) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
        collection.findOneAndReplace(eq("id", bestelling.getId()), convertBestellingToDocument(bestelling));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteBestellingen(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
        collection.findOneAndDelete(eq("id", id));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteBestellingen(Bestelling bestellingen) {
		 return deleteBestellingen(bestellingen.getId());
	}

	@Override
	public ArrayList<Bestelling> getAlleBestellingenPerKlant(Klant klant) {
		ArrayList<Bestelling> bestellingen = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
        FindIterable<Document> allDocuments = collection.find(eq("klantid", klant.getId()));
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            bestellingen.add(convertDocumentToBestelling(doc));
        }
        mongoConnector.close();
        return bestellingen;
	}

	@Override
	public ArrayList<Bestelling> getAlleBestelling() {
		ArrayList<Bestelling> bestellingen = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelling");
        FindIterable<Document> allDocuments = collection.find();
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            bestellingen.add(convertDocumentToBestelling(doc));
        }
        mongoConnector.close();
        return bestellingen;
	}

}
