package creanote.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the notatka database table.
 * 
 */
@Entity
@NamedQuery(name="Notatka.findAll", query="SELECT n FROM Notatka n")
public class Notatka implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idnotatka;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_modyfikacji")
	private Date dataModyfikacji;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_utworzenia")
	private Date dataUtworzenia;

	private String kategoria;

	private String nazwa;

	private byte publiczna;

	private Object tresc;

	//bi-directional many-to-one association to Note
	@ManyToOne
	@JoinColumn(name="idnotes")
	private Note note;

	public Notatka() {
	}

	public int getIdnotatka() {
		return this.idnotatka;
	}

	public void setIdnotatka(int idnotatka) {
		this.idnotatka = idnotatka;
	}

	public Date getDataModyfikacji() {
		return this.dataModyfikacji;
	}

	public void setDataModyfikacji(Date dataModyfikacji) {
		this.dataModyfikacji = dataModyfikacji;
	}

	public Date getDataUtworzenia() {
		return this.dataUtworzenia;
	}

	public void setDataUtworzenia(Date dataUtworzenia) {
		this.dataUtworzenia = dataUtworzenia;
	}

	public String getKategoria() {
		return this.kategoria;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public byte getPubliczna() {
		return this.publiczna;
	}

	public void setPubliczna(byte publiczna) {
		this.publiczna = publiczna;
	}

	public Object getTresc() {
		return this.tresc;
	}

	public void setTresc(Object tresc) {
		this.tresc = tresc;
	}

	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

}