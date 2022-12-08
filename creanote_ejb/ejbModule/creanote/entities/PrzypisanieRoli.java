package creanote.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the przypisanie_roli database table.
 * 
 */
@Entity
@Table(name="przypisanie_roli")
@NamedQuery(name="PrzypisanieRoli.findAll", query="SELECT p FROM PrzypisanieRoli p")
public class PrzypisanieRoli implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PrzypisanieRoliPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_modyfikacji")
	private Date dataModyfikacji;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_nadania")
	private Date dataNadania;

	//bi-directional many-to-one association to Rola
	@ManyToOne
	@JoinColumn(name="idrola")
	private Rola rola;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="iduzytkownik")
	private Uzytkownik uzytkownik;

	public PrzypisanieRoli() {
	}

	public PrzypisanieRoliPK getId() {
		return this.id;
	}

	public void setId(PrzypisanieRoliPK id) {
		this.id = id;
	}

	public Date getDataModyfikacji() {
		return this.dataModyfikacji;
	}

	public void setDataModyfikacji(Date dataModyfikacji) {
		this.dataModyfikacji = dataModyfikacji;
	}

	public Date getDataNadania() {
		return this.dataNadania;
	}

	public void setDataNadania(Date dataNadania) {
		this.dataNadania = dataNadania;
	}

	public Rola getRola() {
		return this.rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}