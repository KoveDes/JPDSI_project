package creanote.settings;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import creanote.dao.UzytkownikDAO;
import creanote.entities.Uzytkownik;

@Named
@ViewScoped
public class UserSettings implements Serializable {
	private static final String PAGE_MAIN = "/public/index?faces-redirect=true";
	private static final String PAGE_LOGIN = "/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@Inject
	FacesContext context;
	
	@Inject
	@ManagedProperty("#{txtSettings}")
	private ResourceBundle txtSettings;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	private String haslo;
	private Integer notebooksCount;

	public Integer getNotebooksCount() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		uzytkownikRemoteNew = uzytkownikRemote.load(session);
		uzytkownik = uzytkownikDAO.findByLogin(uzytkownikRemoteNew.getDetails().getLogin()).get(0);
		Integer notebooksCount = uzytkownik.getLiczbaNotesow();
		return notebooksCount;
	}

	public void setNotebooksCount(Integer notebooksCount) {
		this.notebooksCount = notebooksCount;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	Uzytkownik uzytkownik;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	RemoteClient<Uzytkownik> uzytkownikRemote;
	RemoteClient<Uzytkownik> uzytkownikRemoteNew;

	public String changePassword() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		uzytkownikRemoteNew = uzytkownikRemote.load(session);
		uzytkownik = uzytkownikDAO.findByLogin(uzytkownikRemoteNew.getDetails().getLogin()).get(0);
		haslo = generateHashedPassword(haslo);
		uzytkownik.setHaslo(haslo);
		uzytkownikDAO.update(uzytkownik);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtSettings.getString("passwordChanged"), null));
//		uzytkownik = uzytkownikDAO.findByLogin(uzytkownikRemote.load(session).getName())
//		uzytkownik = uzytkownikDAO.findByLogin('jacek');
//		uzytkownik.setHaslo(null);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String deleteAccount() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		uzytkownikRemoteNew = uzytkownikRemote.load(session);
		uzytkownik = uzytkownikDAO.findByLogin(uzytkownikRemoteNew.getDetails().getLogin()).get(0);
		uzytkownik.setHaslo("usunieto");
		uzytkownik.setDataModyfikacji(new Date());
		uzytkownikDAO.update(uzytkownik);
		return PAGE_MAIN;
	}

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

}
