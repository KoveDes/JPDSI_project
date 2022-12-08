package creanote.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u")
public class Uzytkownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iduzytkownik;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_modyfikacji")
	private Date dataModyfikacji;

	@Column(name="data_utworzenia")
	private Timestamp dataUtworzenia;

	private String haslo;

	@Column(name="kto_stworzyl")
	private String ktoStworzyl;

	@Column(name="liczba_notesow")
	private int liczbaNotesow;

	private String login;

	//bi-directional many-to-one association to Note
	@OneToMany(mappedBy="uzytkownik")
	private List<Note> notes;

	//bi-directional many-to-one association to PrzypisanieRoli
	@OneToMany(mappedBy="uzytkownik")
	private List<PrzypisanieRoli> przypisanieRolis;

	public Uzytkownik() {
	}

	public int getIduzytkownik() {
		return this.iduzytkownik;
	}

	public void setIduzytkownik(int iduzytkownik) {
		this.iduzytkownik = iduzytkownik;
	}

	public Date getDataModyfikacji() {
		return this.dataModyfikacji;
	}

	public void setDataModyfikacji(Date dataModyfikacji) {
		this.dataModyfikacji = dataModyfikacji;
	}

	public Timestamp getDataUtworzenia() {
		return this.dataUtworzenia;
	}

	public void setDataUtworzenia(Timestamp dataUtworzenia) {
		this.dataUtworzenia = dataUtworzenia;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getKtoStworzyl() {
		return this.ktoStworzyl;
	}

	public void setKtoStworzyl(String ktoStworzyl) {
		this.ktoStworzyl = ktoStworzyl;
	}

	public int getLiczbaNotesow() {
		return this.liczbaNotesow;
	}

	public void setLiczbaNotesow(int liczbaNotesow) {
		this.liczbaNotesow = liczbaNotesow;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setUzytkownik(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setUzytkownik(null);

		return note;
	}

	public List<PrzypisanieRoli> getPrzypisanieRolis() {
		return this.przypisanieRolis;
	}

	public void setPrzypisanieRolis(List<PrzypisanieRoli> przypisanieRolis) {
		this.przypisanieRolis = przypisanieRolis;
	}

	public PrzypisanieRoli addPrzypisanieRoli(PrzypisanieRoli przypisanieRoli) {
		getPrzypisanieRolis().add(przypisanieRoli);
		przypisanieRoli.setUzytkownik(this);

		return przypisanieRoli;
	}

	public PrzypisanieRoli removePrzypisanieRoli(PrzypisanieRoli przypisanieRoli) {
		getPrzypisanieRolis().remove(przypisanieRoli);
		przypisanieRoli.setUzytkownik(null);

		return przypisanieRoli;
	}

}