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

import data.BestelregelDao;
import domein.Artikel;
import domein.BestelRegel;

public class BestelregelDaoMongoImplement implements BestelregelDao{

	
private ConnectieDatabaseMongoImplement mongoConnector;
	
	public BestelregelDaoMongoImplement() {
        mongoConnector = new ConnectieDatabaseMongoImplement();
    }
	
	private BestelRegel convertDocumentToBestelregel(Document doc){
		Artikel artikel = new Artikel (doc.getInteger("artikelid"));
		System.out.println(artikel.getId());
		System.out.println(new BigDecimal ( (doc.getString("prijs"))));
		System.out.println(new BigDecimal (doc.getInteger("bestellingid")));
		BestelRegel bestelregel= new BestelRegel(doc.getInteger("aantal"),(doc.getInteger("bestellingid")), artikel , new BigDecimal ( (doc.getString("prijs"))));
		bestelregel.setId(doc.getInteger("id"));
        return bestelregel;
    }		
	
	 private Document convertBestelregelToDocument(BestelRegel bestelregel){
	        Document document = new Document();
	        document.append("id", bestelregel.getId());
	        document.append("aantal", bestelregel.getAantal());
	        document.append("prijs", bestelregel.getPrijs().toPlainString());
	        document.append("bestellingid", bestelregel.getBestellingId());
	        document.append("artikelid", bestelregel.getArtikel().getId());
	        return document;
	    }
	 
	 private Integer getNextId(){
	        int id = 0;
	        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
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
	public int createBestelregel(BestelRegel bestelregel) {
		 bestelregel.setId(getNextId());
	     MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
	     collection.insertOne(convertBestelregelToDocument(bestelregel));
	     mongoConnector.close();
	     return bestelregel.getId();
	}

	@Override
	public BestelRegel getBestelRegel(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
        Document doc = collection.find(eq("id", id)).first();
        mongoConnector.close();
        return convertDocumentToBestelregel(doc);
	}

	@Override
	public ArrayList<BestelRegel> getAlleBestelRegel() {
		ArrayList<BestelRegel> bestelregels = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
        FindIterable<Document> allDocuments = collection.find();
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            bestelregels.add(convertDocumentToBestelregel(doc));
        }
        mongoConnector.close();
        return bestelregels;
	}

	@Override
	public boolean updateBestelRegel(BestelRegel bestelregel) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
        collection.findOneAndReplace(eq("id", bestelregel.getId()), convertBestelregelToDocument(bestelregel));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteBestelRegel(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
        collection.findOneAndDelete(eq("id", id));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteBestelRegel(BestelRegel bestelregel) {
		 return deleteBestelRegel(bestelregel.getId());
	}

	@Override
	public ArrayList<BestelRegel> getAlleBestelregelsPerBestelling(int bestellingId) {
		ArrayList<BestelRegel> bestelregels = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("bestelregel");
        FindIterable<Document> allDocuments = collection.find(eq("bestellingid", bestellingId));
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            bestelregels.add(convertDocumentToBestelregel(doc));
        }
        mongoConnector.close();
        return bestelregels;
	}

}
