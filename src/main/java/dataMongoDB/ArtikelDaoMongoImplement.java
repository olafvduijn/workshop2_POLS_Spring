package dataMongoDB;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import dataOld.ArtikelDao;
import domein.Artikel;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class ArtikelDaoMongoImplement implements ArtikelDao {
private ConnectieDatabaseMongoImplement mongoConnector;
    
    public ArtikelDaoMongoImplement() {
        mongoConnector = new ConnectieDatabaseMongoImplement();
    }
    
    private Artikel convertDocumentToArtikel(Document doc){
        Artikel artikel= new Artikel(doc.getString("naam"), new BigDecimal(doc.getString("prijs")), doc.getInteger("voorraad"));
        artikel.setId(doc.getInteger("id"));
        return artikel;
    }
    
    private Document convertArtikelToDocument(Artikel artikel){
        Document document = new Document();
        document.append("id", artikel.getId());
        document.append("naam", artikel.getNaam());
        document.append("prijs", artikel.getPrijs().toPlainString());
        document.append("voorraad", artikel.getVoorraad());
        return document;
    }
    
    private Integer getNextId(){
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
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
    public int createArtikel(Artikel artikel) {
        artikel.setId(getNextId());
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
        collection.insertOne(convertArtikelToDocument(artikel));
        mongoConnector.close();
        return artikel.getId();
    }

    @Override
    public Artikel getArtikel(int id) {
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
        Document doc = collection.find(eq("id", id)).first();
        mongoConnector.close();
        return convertDocumentToArtikel(doc);
    }

    @Override
    public ArrayList<Artikel> getAlleArtikelen() {
        ArrayList<Artikel> artikelen = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
        FindIterable<Document> allDocuments = collection.find();
        MongoCursor<Document> iterator = allDocuments.iterator();
        while(iterator.hasNext()){
            Document doc = iterator.next();
            artikelen.add(convertDocumentToArtikel(doc));
        }
        mongoConnector.close();
        return artikelen;
    }

    @Override
    public boolean updateArtikel(Artikel nieuwArtikel) {
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
        collection.findOneAndReplace(eq("id", nieuwArtikel.getId()), convertArtikelToDocument(nieuwArtikel));
        mongoConnector.close();
        return true;
    }
    
    @Override
    public boolean updateArtikel(String naam, BigDecimal prijs , int voorraad, int id) {
    	MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
    	Artikel nieuwArtikel=new Artikel(naam, prijs, voorraad);
    	nieuwArtikel.setId(id);
    	collection.findOneAndReplace(eq("id", nieuwArtikel.getId()), convertArtikelToDocument(nieuwArtikel));
        mongoConnector.close();
        return true;
    }

    @Override
    public boolean deleteArtikel(Artikel artikel) {
        return deleteArtikel(artikel.getId());
    }

    @Override
    public boolean deleteArtikel(int id) {
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("artikel");
        collection.findOneAndDelete(eq("id", id));
        mongoConnector.close();
        return true;
    }
}
