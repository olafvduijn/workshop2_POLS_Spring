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

import data.KlantDao;
import domein.Artikel;
import domein.Klant;

public class KlantDaoMongoImplement implements KlantDao{
	
	private ConnectieDatabaseMongoImplement mongoConnector;
	
	public KlantDaoMongoImplement() {
        mongoConnector = new ConnectieDatabaseMongoImplement();
    }

	private Klant convertDocumentToKlant(Document doc){
        Klant klant= new Klant(doc.getString("voornaam"), (doc.getString("tussenvoegsel")), (doc.getString("achternaam")), (doc.getInteger("accountid")));
        klant.setId(doc.getInteger("id"));
        return klant;
    }
	

    private Document convertKlantToDocument(Klant klant){
        Document document = new Document();
        document.append("id", klant.getId());
        document.append("voornaam", klant.getVoornaam());
        document.append("tussenvoegsel", klant.getTussenvoegsel());
        document.append("achternaam", klant.getAchternaam());
        document.append("accountid", klant.getAccountId());
        return document;
    }
	
    private Integer getNextId(){
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
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
	public int createKlant(Klant klant) {
		 klant.setId(getNextId());
	     MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
	     collection.insertOne(convertKlantToDocument(klant));
	     mongoConnector.close();
	     return klant.getId();
	}

	@Override
	public Klant getKlant(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
        Document doc = collection.find(eq("id", id)).first();
        mongoConnector.close();
        return convertDocumentToKlant(doc);
	}

	@Override
	public ArrayList<Klant> getAlleKlanten() {
		ArrayList<Klant> klanten = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
        FindIterable<Document> allDocuments = collection.find();
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            klanten.add(convertDocumentToKlant(doc));
        }
        mongoConnector.close();
        return klanten;
	}

	@Override
	public boolean updateKlant(String voornaam, String tussenvoegsel, String achternaam, int accountId, int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
    	Klant nieuwklant =new Klant(voornaam, tussenvoegsel, achternaam, accountId);
    	nieuwklant.setId(id);
    	collection.findOneAndReplace(eq("id", nieuwklant.getId()), convertKlantToDocument(nieuwklant));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean updateKlant(Klant nieuwKlant) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
        collection.findOneAndReplace(eq("id", nieuwKlant.getId()), convertKlantToDocument(nieuwKlant));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteKlant(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
        collection.findOneAndDelete(eq("id", id));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteKlant(Klant klant) {
		 return deleteKlant(klant.getId());
		
	}

	@Override
	public Klant getAlleKlantenPerAccount(int accountId) {
		Klant klanten = null;
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("klant");
        FindIterable<Document> allDocuments = collection.find((eq("accountid", accountId)));
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            klanten=(convertDocumentToKlant(doc));
        }
        mongoConnector.close();
        return klanten;
	}
	


}
