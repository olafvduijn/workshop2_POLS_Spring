package controllerTest;


import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.*;

import Controllers.ArtikelController;
import dataMySQL.ArtikelDaoImplement;
import domein.Artikel;
import junit.framework.Assert;



public class ArtikelControllerTest {
	
	@Mock private ArtikelDaoImplement  artikelDaoImplement;
	@Rule public MockitoRule rule = MockitoJUnit.rule();		
	
	Artikel nieuweArtikel1=new Artikel ("Oude kaas", new BigDecimal ("7.20"), 100);
	Artikel nieuweArtikel2=new Artikel ("pinda kaas", new BigDecimal ("10.00"), 30);
	ArtikelController artikelController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		artikelController = new ArtikelController(artikelDaoImplement);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtikelController() {
	    
		artikelController.voegArtikelToe2(nieuweArtikel1);;
	//    Mockito.verify(artikelDaoImplement).createArtikel(nieuweArtikel1);  
	    Mockito.verify(artikelDaoImplement).createArtikel(nieuweArtikel1); 

	    
	//    Mockito.verify(artikelDaoImplement).createArtikel(refEq(nieuweArtikel1));
   
	}  
		 
	@Test
	public void testVoegArtikelToe() {
    	

    	nieuweArtikel1.setId(10);
    	int id1 = nieuweArtikel1.getId();	
	
    	Mockito.when(artikelDaoImplement.createArtikel((Artikel)any())).thenReturn(id1);
    	Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
    	
		boolean voegtoesucces =artikelController.voegArtikelToe(nieuweArtikel1.getNaam(), nieuweArtikel1.getPrijs(), nieuweArtikel1.getVoorraad());
		Artikel actual=artikelDaoImplement.getArtikel(id1);
		
		System.out.println("testing actual "+ actual);
		System.out.println("testing nieuwe "+ nieuweArtikel1);
		Assert.assertTrue(voegtoesucces);
		Assert.assertTrue(actual.equals(nieuweArtikel1));		
		

		Mockito.verify(artikelDaoImplement).createArtikel((Artikel)any());
 		
		
	}     

	@Test
	public void testPasNaamAan() {
		
		//nieuweArtikel1.setNaam("nieuwe kaasnaam");
		nieuweArtikel1.setId(10);
		System.out.println(nieuweArtikel1.getNaam());
		
		int id1 = nieuweArtikel1.getId();
		
		Mockito.when(artikelDaoImplement.updateArtikel(nieuweArtikel1)).thenReturn(true);		
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		System.out.println(artikelen.indexOf(nieuweArtikel1));
		System.out.println(artikelen);
		boolean pasNaamAansucces = artikelController.pasNaamAan(artikelen.indexOf(nieuweArtikel1), "nieuwe kaasnaam");
		
		Artikel actual=artikelDaoImplement.getArtikel(id1);
		System.out.println(nieuweArtikel1.getNaam());
		
		Assert.assertTrue(pasNaamAansucces);
		Assert.assertTrue(actual.equals(nieuweArtikel1));
		
		Mockito.verify(artikelDaoImplement).updateArtikel(nieuweArtikel1);

	}

	@Test
	public void testPasPrijsAan() {
		nieuweArtikel1.setId(10);
		System.out.println(nieuweArtikel1.getPrijs());
		
		int id1 = nieuweArtikel1.getId();
		
		Mockito.when(artikelDaoImplement.updateArtikel(nieuweArtikel1)).thenReturn(true);		
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		System.out.println(artikelen.indexOf(nieuweArtikel1));
		System.out.println(artikelen);
		boolean pasPrijsAansucces = artikelController.pasPrijsAan(artikelen.indexOf(nieuweArtikel1), new BigDecimal ("9.99"));
		
		Artikel actual=artikelDaoImplement.getArtikel(id1);
		System.out.println(nieuweArtikel1.getPrijs());
		
		Assert.assertTrue(pasPrijsAansucces);
		Assert.assertTrue(actual.equals(nieuweArtikel1));
		
		Mockito.verify(artikelDaoImplement).updateArtikel(nieuweArtikel1);
	}

	@Test
	public void testPasVoorraadAan() {
		nieuweArtikel1.setId(10);
		System.out.println(nieuweArtikel1.getVoorraad());
		
		int id1 = nieuweArtikel1.getId();
		
		Mockito.when(artikelDaoImplement.updateArtikel(nieuweArtikel1)).thenReturn(true);		
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		System.out.println(artikelen.indexOf(nieuweArtikel1));
		System.out.println(artikelen);
		boolean pasVoorraadAansucces = artikelController.pasVoorraadAan(artikelen.indexOf(nieuweArtikel1), 999);
		
		Artikel actual=artikelDaoImplement.getArtikel(id1);
		System.out.println(nieuweArtikel1.getVoorraad());
		
		Assert.assertTrue(pasVoorraadAansucces);
		Assert.assertTrue(actual.equals(nieuweArtikel1));
		
		Mockito.verify(artikelDaoImplement).updateArtikel(nieuweArtikel1);

	}       

	@Test
	public void testZoekArtikel() {
		nieuweArtikel1.setId(0);
		int id1 = nieuweArtikel1.getId();
		
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		String actual = artikelController.zoekArtikel(id1); 		
		Assert.assertEquals(actual,("" + id1 +" "+ nieuweArtikel1.getNaam()+" " + nieuweArtikel1.getPrijs()+" " + nieuweArtikel1.getVoorraad()));
		 
		
		Mockito.verify(artikelDaoImplement).getArtikel(id1);
	}

	@Test
	public void testDeleteArtikel() {
		nieuweArtikel1.setId(0);
		int id1 = nieuweArtikel1.getId();
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		Mockito.when(artikelDaoImplement.deleteArtikel(anyInt())).thenReturn(true);
		Mockito.when(artikelDaoImplement.deleteArtikel((Artikel)any())).thenReturn(true);
		System.out.println(artikelen.indexOf(nieuweArtikel1));
		System.out.println(artikelen);
				
		boolean actual = artikelController.deleteArtikel(id1);	
		System.out.println(actual + "test");
		Assert.assertTrue(actual);
		
		Mockito.verify(artikelDaoImplement).deleteArtikel(nieuweArtikel1);
	}

	@Test
	public void testGetAlleArtikelen() {
		nieuweArtikel1.setId(0);
		int id1 = nieuweArtikel1.getId();
		
		Mockito.when(artikelDaoImplement.getArtikel(anyInt())).thenReturn(nieuweArtikel1);
		ArrayList<Artikel> artikelen = new ArrayList<Artikel>(); artikelen.add(nieuweArtikel1) ;
		
		Mockito.when(artikelDaoImplement.getAlleArtikelen()).thenReturn(artikelen);
		String[] actual = artikelController.getAlleArtikelen(); 	
		//System.out.println(Arrays.toString(actual)+ " test4");
		
		String inVoorraad = nieuweArtikel1.getVoorraad() > 0 ? nieuweArtikel1.getVoorraad() + " in voorraad." : "UITVERKOCHT!";
		
		String[] expected=new String[] {"nummer :" + id1 + " naam: " + nieuweArtikel1.getNaam() + ". €" + nieuweArtikel1.getPrijs().toPlainString() + " voorraad: " + inVoorraad};
		//System.out.println(Arrays.toString(actual)+ "test 5");
//		Assert.assertEquals(Arrays.toString(actual),Arrays.toString(expected));
		 
		
		Mockito.verify(artikelDaoImplement).getAlleArtikelen();
		
		
		
		
		

	  
	}     
}  
