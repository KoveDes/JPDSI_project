package creanote.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The primary key class for the przypisanie_roli database table.
 * 
 */
@Embeddable
public class PrzypisanieRoliPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int iduzytkownik;

	@Column(insertable=false, updatable=false)
	private int idrola;

	public PrzypisanieRoliPK() {
	}
	public int getIduzytkownik() {
		return this.iduzytkownik;
	}
	public void setIduzytkownik(int iduzytkownik) {
		this.iduzytkownik = iduzytkownik;
	}
	public int getIdrola() {
		return this.idrola;
	}
	public void setIdrola(int idrola) {
		this.idrola = idrola;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PrzypisanieRoliPK)) {
			return false;
		}
		PrzypisanieRoliPK castOther = (PrzypisanieRoliPK)other;
		return 
			(this.iduzytkownik == castOther.iduzytkownik)
			&& (this.idrola == castOther.idrola);
	}

	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.iduzytkownik;
		hash = hash * prime + this.idrola;
		
		return hash;
	}
}