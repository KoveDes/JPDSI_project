package creanote.login;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import creanote.dao.PrzypisanieRoliDAO;
import creanote.dao.UzytkownikDAO;
import creanote.entities.PrzypisanieRoli;
import creanote.entities.PrzypisanieRoliPK;
import creanote.entities.Rola;
import creanote.entities.Uzytkownik;

@Named
@ViewScoped
public class LoginBB implements Serializable {
	private static final String PAGE_MAIN = "/public/index?faces-redirect=true";
	private static final String PAGE_LOGIN = "/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String password;
	
	@Inject
	Flash flash;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@EJB
	PrzypisanieRoliDAO przypisanieRoliDAO;

	@Inject
	UzytkownikDAO uzytkownikDAO;
	
	@Inject
	@ManagedProperty("#{txtLogin}")
	private ResourceBundle txtLogin;


	PrzypisanieRoliPK przypisanieRoliPK;
	Rola rola;

	public void generateHashedPassword(String password) {
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
		setPassword(hashedPassword.substring(0, 20));
	}

	public String doLogin() throws InterruptedException {
		generateHashedPassword(getPassword());
		System.out.println(getLogin());
		System.out.println(getPassword());
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get User from "database"
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("login", getLogin());
		searchParams.put("haslo", getPassword());

		List<Uzytkownik> uzytkownikList = uzytkownikDAO.getList(searchParams);
		Uzytkownik uzytkownik = null;

		if (!uzytkownikList.isEmpty()) {
			uzytkownik = uzytkownikList.get(0);
		}

		if (uzytkownik == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtLogin.getString("invalidLogIn"), null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session

		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>(); // create new RemoteClient
		client.setDetails(uzytkownik);

		// Get Role

		// pobierz idroli
		Integer idUser = uzytkownik.getIduzytkownik(); // iduzytkownik = 22;

		String role = przypisanieRoliDAO.getUserRoleFromDataBase(uzytkownik);
		client.getRoles().add(role);

		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, txtLogin.getString("successfullLogIn"), null));

		// store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);
		// and enter the system (now SecurityFilter will pass the request)
		flash.clear();
		return PAGE_MAIN;
	}

	public String doLogout() {
		System.out.println("Jestem w środku!");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		flash.clear();
		return PAGE_LOGIN;
	}

}