package creanote.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rola database table.
 * 
 */
@Entity
@NamedQuery(name="Rola.findAll", query="SELECT r FROM Rola r")
public class Rola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrola;

	private byte aktywna;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_utworzenia")
	private Date dataUtworzenia;

	private String nazwa;

	//bi-directional many-to-one association to PrzypisanieRoli
	@OneToMany(mappedBy="rola")
	private List<PrzypisanieRoli> przypisanieRolis;

	public Rola() {
	}

	public int getIdrola() {
		return this.idrola;
	}

	public void setIdrola(int idrola) {
		this.idrola = idrola;
	}

	public byte getAktywna() {
		return this.aktywna;
	}

	public void setAktywna(byte aktywna) {
		this.aktywna = aktywna;
	}

	public Date getDataUtworzenia() {
		return this.dataUtworzenia;
	}

	public void setDataUtworzenia(Date dataUtworzenia) {
		this.dataUtworzenia = dataUtworzenia;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<PrzypisanieRoli> getPrzypisanieRolis() {
		return this.przypisanieRolis;
	}

	public void setPrzypisanieRolis(List<PrzypisanieRoli> przypisanieRolis) {
		this.przypisanieRolis = przypisanieRolis;
	}

	public PrzypisanieRoli addPrzypisanieRoli(PrzypisanieRoli przypisanieRoli) {
		getPrzypisanieRolis().add(przypisanieRoli);
		przypisanieRoli.setRola(this);

		return przypisanieRoli;
	}

	public PrzypisanieRoli removePrzypisanieRoli(PrzypisanieRoli przypisanieRoli) {
		getPrzypisanieRolis().remove(przypisanieRoli);
		przypisanieRoli.setRola(null);

		return przypisanieRoli;
	}

}