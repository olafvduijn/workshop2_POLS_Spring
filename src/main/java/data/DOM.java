package data;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DOM {
	DocumentBuilder builder = null;
	Document document = null;
	Element rootElement = null;
	
	public DOM(){
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		try {
		    document = builder.parse(new FileInputStream("resources/connectie.xml"));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		rootElement = document.getDocumentElement();
	}
	
	public String getUrl(){
		NodeList urlNodes = rootElement.getElementsByTagName("url");
		return urlNodes.item(0).getTextContent();
	}
	
	public String getUrl(String db){
		Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
		Element dbElement = (Element) dbNode;
		return dbElement.getElementsByTagName("url").item(0).getTextContent();
	}
	
	
	public String getDriverName(){
		NodeList driverNodes = rootElement.getElementsByTagName("drivername");
		return driverNodes.item(0).getTextContent();
	}
	
	public String getDriverName(String db){
		Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
		Element dbElement = (Element) dbNode;
		return dbElement.getElementsByTagName("drivername").item(0).getTextContent();
	}
	
	public String getUsername(){
		NodeList userNodes = rootElement.getElementsByTagName("username");
		return userNodes.item(0).getTextContent();
	}
	
	public String getUsername(String db){
		Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
		Element dbElement = (Element) dbNode;
		return dbElement.getElementsByTagName("username").item(0).getTextContent();
	}
	
	public String getPassword(){
		NodeList passNodes = rootElement.getElementsByTagName("password");
		return passNodes.item(0).getTextContent();
	}
	
	public String getPassword(String db){
		Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
		Element dbElement = (Element) dbNode;
		return dbElement.getElementsByTagName("password").item(0).getTextContent();
	}
    public int getPort(String db){
        Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
        Element dbElement = (Element) dbNode;
        return Integer.parseInt(dbElement.getElementsByTagName("port").item(0).getTextContent());
    }
    
    public String getDatabase(String db){
        Node dbNode = rootElement.getElementsByTagName(db.toLowerCase()).item(0);
        Element dbElement = (Element) dbNode;
        return dbElement.getElementsByTagName("database").item(0).getTextContent();
    }
}
