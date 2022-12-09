package creanote.register;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIImportConstants;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import creanote.dao.PrzypisanieRoliDAO;
import creanote.dao.UzytkownikDAO;
import creanote.entities.PrzypisanieRoli;
import creanote.entities.PrzypisanieRoliPK;
import creanote.entities.Uzytkownik;

@Named
@ViewScoped
public class RegisterBB implements Serializable {
	private Uzytkownik uzytkownik = new Uzytkownik();
	private PrzypisanieRoli przypisanieRoli = new PrzypisanieRoli();
	private PrzypisanieRoliPK przypisanieRoliPK = new PrzypisanieRoliPK();
	private Boolean premium;

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@EJB
	PrzypisanieRoliDAO przypisanieRoliDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	// Sprawdza czy login
	public Boolean checkForDoubling() {
		if (uzytkownikDAO.findByLogin(uzytkownik.getLogin()).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	//
	public void setRole() {
		// premium i uzytkownik
		// tworzymy obiekt przypisanieRoliPK, dodajemy id i podajemy jego id w
		// PrzypisanieRoli

		// Tworzymy obiekt PrzypisanieRoli (getid, setid) w setid podajemy obiekt
		// przypisanieRoliPK
		// Obiket PrzypisanieRoliPK tworzymy
		// pobiermay id
		Uzytkownik user = uzytkownikDAO.findByLogin(uzytkownik.getLogin()).get(0);
		Integer idUser = user.getIduzytkownik();
		przypisanieRoliPK.setIduzytkownik(idUser);

		if (premium == true) {
			// Rola premium idrola 2
			przypisanieRoliPK.setIdrola(2);

		} else {
			// Rola user idrola 3
			przypisanieRoliPK.setIdrola(3);
		}
		przypisanieRoli.setId(przypisanieRoliPK);
		
		przypisanieRoli.setDataNadania(new Date());
		przypisanieRoli.setDataModyfikacji(new Date());
		przypisanieRoliDAO.create(przypisanieRoli);
	}
	//

	public String generateHashedPassword(String password) {
		String hashedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedPassword.substring(0, 20);
	}

	public String createAccount() {
		try {
			if (checkForDoubling()) {
				System.out.println(LocalDate.now());
				System.out.println("Premium: " + premium);
				LocalDate currTime = LocalDate.now();
				uzytkownik.setHaslo(generateHashedPassword(uzytkownik.getHaslo()));
				uzytkownik.setKtoStworzyl(uzytkownik.getLogin());
				uzytkownik.setDataModyfikacji(new Date());
				uzytkownikDAO.insert(uzytkownik);
				setRole();
				return "index";
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Istnieje już taki login", "");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystapił błąd poczas zapisu", null));
			return "index";
		}
	}
}
