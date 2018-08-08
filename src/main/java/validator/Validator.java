package validator;

import java.math.BigDecimal;
import org.apache.commons.validator.GenericValidator;

import data.DaoFactory;

public class Validator {

	public static boolean postcodeIsValid(String postcode) { 
		  boolean retval = false;
		    String zipCodePattern = "^[0-9]{4}\\s*[a-zA-Z]{2}$";
		    retval = postcode.matches(zipCodePattern);
		    if (!retval) {
		    String msg = "Geen geldige postcode:" + postcode + "het juiste format is: 4 cijfers gevolgd door 2 letters " + "\r\n";

		    System.out.println(msg + "\r\n");
		    }
		    
		return retval;
		    
		}
	
	public static boolean aantalIsPositief(int aantal) {
		return aantal>=0;
	}
	
	public static boolean validPrijs(BigDecimal prijs){
		
		if ((prijs.compareTo(new BigDecimal ("0")))==-1) return false;
			
		boolean prijsScale = prijs.scale() <= 2;
		if(!prijsScale){
			System.out.println("Prijs heeft meer dan 2 decimalen.");
		}
		return prijsScale;
	}
		
	public static boolean isNaamValid(String naam){
		boolean nameEmpty = GenericValidator.isBlankOrNull(naam);
		boolean nameLengthOk = GenericValidator.maxLength(naam, 45);
		if(nameEmpty){
			System.out.println("Naam is leeg!");
		}
		if(!nameLengthOk) {
			System.out.println("Naam te lang!");
		}
		return (!nameEmpty)&&nameLengthOk;
	}
	public static boolean isRol(String rol) {
		String rollowercase= rol.toLowerCase()	;
		if (rollowercase.equals("beheerder")) {	
			return true;
		}
		
		if (rollowercase.equals("medewerker")){
			return true;
		}
		if (rollowercase.equals("klant")){
			return true;
		}
		else return false;	
	}
	
	
	
}