package domein;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Bestelling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ColumnDefault("0")
	private BigDecimal totaalprijs;

	@ColumnDefault("0")
	@Column (name="Klant_idKlant")
	private int klantId;

	public Bestelling(int id, BigDecimal totaalprijs, int klantId) {
		this.id = id;
		this.totaalprijs = totaalprijs;
		this.klantId = klantId;
	}

	public Bestelling(Klant klant) {
		this.totaalprijs = new BigDecimal("0.00");
		this.klantId = klant.getId();
	}

	public Bestelling() {
		this.totaalprijs = new BigDecimal("0.00");
	}

	public int getId() {
		return this.id;
	}

	public BigDecimal gettotaalprijs() {
		return this.totaalprijs;
	}

	public int GetKlantId() {
		return this.klantId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void settotaalprijs(BigDecimal totaalprijs) {
		this.totaalprijs = totaalprijs;
	}

	public void setKlantId(int klantId) {
		this.klantId = klantId;
	}

	public boolean equals(Bestelling bestelling) {
		if (!this.totaalprijs.equals(bestelling.gettotaalprijs())) {
			return false;
		}
		if (this.id != bestelling.getId()) {
			return false;
		}
		return true;
	}

}
