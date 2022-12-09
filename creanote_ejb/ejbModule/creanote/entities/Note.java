package creanote.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the notes database table.
 * 
 */
@Entity
@Table(name="notes")
@NamedQuery(name="Note.findAll", query="SELECT n FROM Note n")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idnotes;

	@Column(name="ilosc_notatek")
	private int iloscNotatek;

	private String nazwa;

	private byte publiczna;


	//bi-directional many-to-one association to Notatka
	@OneToMany(mappedBy="note")
	private List<Notatka> notatkas;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="iduzytkownik")
	private Uzytkownik uzytkownik;

	public Note() {
	}

	public int getIdnotes() {
		return this.idnotes;
	}

	public void setIdnotes(int idnotes) {
		this.idnotes = idnotes;
	}

	public int getIloscNotatek() {
		return this.iloscNotatek;
	}

	public void setIloscNotatek(int iloscNotatek) {
		this.iloscNotatek = iloscNotatek;
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

	public List<Notatka> getNotatkas() {
		return this.notatkas;
	}

	public void setNotatkas(List<Notatka> notatkas) {
		this.notatkas = notatkas;
	}

	public Notatka addNotatka(Notatka notatka) {
		getNotatkas().add(notatka);
		notatka.setNote(this);

		return notatka;
	}

	public Notatka removeNotatka(Notatka notatka) {
		getNotatkas().remove(notatka);
		notatka.setNote(null);

		return notatka;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}