package domein;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="bestelregel")
public class BestelRegel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Artikel artikel;
	
	@ColumnDefault("0")
	private int aantal;
	
	@ColumnDefault("0")
	private BigDecimal prijs;
	
	@ColumnDefault("0")
	@Column (name="Bestelling_idBestelling")
	private int bestellingId;
	
	
	public BestelRegel() {
		
	}
	
	public BestelRegel ( int aantal ,int bestellingId, Artikel artikel ) {
		this.artikel=artikel;
		this.setBestellingId(bestellingId);
		this.aantal=aantal;
		
	}
	public BestelRegel ( int aantal ,int bestellingId, Artikel artikel, BigDecimal totaalprijs ) {
		this.artikel=artikel;
		this.setBestellingId(bestellingId);
		this.aantal=aantal;
		this.prijs=totaalprijs;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setArtikel(Artikel artikel) {
		this.artikel=artikel;
	}

	public void setAantal(int aantal) {
		this.aantal=aantal;
	}
	
	public void setPrijs(BigDecimal prijs) {
		this.prijs=prijs;
	}
	
	public void setBestellingId(int bestellingId) {
		this.bestellingId = bestellingId;
	}
	
	public int getId() {
		return this.id;
	}

	public Artikel getArtikel() {
		return this.artikel;
	}
	
	public int getAantal() {
		return this.aantal;
	}
	
	public BigDecimal getPrijs() {
		return this.prijs;
	}
	
	public int getBestellingId() {
		return bestellingId;
	}
	
	public boolean equals (BestelRegel bestelregel) {
		if (this.id!=bestelregel.getId()) {
			return false;
		}
		if (!this.artikel.equals(bestelregel.getArtikel())) {
			return false;
		}
		if (this.aantal!=bestelregel.getAantal()) {
			return false;
		}
		if (!this.prijs.equals(bestelregel.getPrijs())) {
			return false;
		}
		return true;
	}




	
}
