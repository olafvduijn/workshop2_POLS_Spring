package dataMongoDB;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import data.AdresDao;
import domein.Account;
import domein.Adres;
import domein.Account.Rol;
import domein.Adres.AdresType;

public class AdresDaoMongoImplement implements AdresDao{
	
	
	ConnectieDatabaseMongoImplement mongoConnector;
	
	public AdresDaoMongoImplement() {
        mongoConnector = new ConnectieDatabaseMongoImplement();
    }
	
	private Adres convertDocumentToAdres(Document doc){
        Adres adres= new Adres (AdresType.toAdresType(doc.getString("adrestype")), doc.getString("straatnaam"), doc.getInteger("huisnummer"), doc.getString("toevoeging"), doc.getString("postcode"), doc.getString ("woonplaats"), doc.getInteger("klantid"));    
        adres.setId(doc.getInteger("id"));
        return adres;
    }
    
    private Document convertAdresToDoc(Adres adres){
        Document document = new Document();
        document.append("id", adres.getId());
        document.append("straatnaam", adres.getStraatnaam());
        document.append("huisnummer", adres.getHuisnummer());
        document.append("toevoeging", adres.getToevoeging());
        document.append("postcode", adres.getPostcode());
        document.append("woonplaats", adres.getWoonplaats());
        document.append("adrestype", adres.getAdresType().toString());
        document.append("klantid", adres.getKlantId());
        return document;
    }
    
    private Integer getNextId(){
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
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
	public boolean createAdres(Adres adres, int klantid) {
		adres.setId(getNextId());
		adres.setKlantid(klantid);
        MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
        collection.insertOne(convertAdresToDoc(adres));
        mongoConnector.close();
        return true;
	}

	@Override
	public Adres getAdres(int klantid, AdresType adrestype) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
        Document doc = collection.find(and(eq("klantid", klantid),eq("adrestype", adrestype.toString()))).first();
        mongoConnector.close();
        return convertDocumentToAdres(doc);
	}

	@Override
	public boolean updateAdres(Adres gewijzigdAdres, int adresId) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
		collection.findOneAndReplace(eq("id", adresId), convertAdresToDoc(gewijzigdAdres));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteAdres(int id) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
        collection.findOneAndDelete(eq("id", id));
        mongoConnector.close();
        return true;
	}

	@Override
	public boolean deleteAdres(Adres teVerwijderenAdres) {
		return deleteAdres(teVerwijderenAdres.getId());
	}

	@Override
	public boolean factuurAdresAanwezig(int klantid) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
        long aantal=collection.countDocuments(and(eq("klantid", klantid),eq("adrestype", "FACTUURADRES")));
        if (aantal>0) {
        	return true;
        }
        else return false;
	}

	@Override
	public boolean bezorgAdresAanwezig(int klantid) {
		MongoCollection<Document> collection = mongoConnector.getMongoDB().getCollection("adres");
        long aantal=collection.countDocuments(and(eq("klantid", klantid),eq("adrestype", "BEZORGADRES")));
        if (aantal>0) {
        	return true;
        }
        else return false;
	}

	@Override
	public String toonPostadres(int klantId) {
		Adres adres=getAdres(klantId, AdresType.POSTADRES);
		String s="";
		s=s+"straatnaam: "+adres.getStraatnaam()+ System.lineSeparator();
		s=s+"huisnummer: "+adres.getHuisnummer()+ System.lineSeparator();
		s=s+"toevoeging: "+adres.getToevoeging()+ System.lineSeparator();
		s=s+"postcode: "+adres.getPostcode()+ System.lineSeparator();
		s=s+"plaats: "+adres.getWoonplaats();
		return s;
	}

	@Override
	public String toonFactuuradres(int klantId) {
		Adres adres=getAdres(klantId, AdresType.FACTUURADRES);
		String s="";
		s=s+"straatnaam: "+adres.getStraatnaam()+ System.lineSeparator();
		s=s+"huisnummer: "+adres.getHuisnummer()+ System.lineSeparator();
		s=s+"toevoeging: "+adres.getToevoeging()+ System.lineSeparator();
		s=s+"postcode: "+adres.getPostcode()+ System.lineSeparator();
		s=s+"plaats: "+adres.getWoonplaats();
		return s;
	}

	@Override
	public String toonBezorgadres(int klantId) {
		Adres adres=getAdres(klantId, AdresType.BEZORGADRES);
		String s="";
		s=s+"straatnaam: "+adres.getStraatnaam()+ System.lineSeparator();
		s=s+"huisnummer: "+adres.getHuisnummer()+ System.lineSeparator();
		s=s+"toevoeging: "+adres.getToevoeging()+ System.lineSeparator();
		s=s+"postcode: "+adres.getPostcode()+ System.lineSeparator();
		s=s+"plaats: "+adres.getWoonplaats();
		return s;
	}

}
