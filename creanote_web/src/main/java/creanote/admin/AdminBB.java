package creanote.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import creanote.dao.PrzypisanieRoliDAO;
import creanote.dao.UzytkownikDAO;
import creanote.entities.PrzypisanieRoli;
import creanote.entities.Uzytkownik;
import creanote.login.*;
import creanote.register.RegisterBB;

@Named
@ViewScoped
public class AdminBB implements Serializable {
	private static final String PAGE_CHANGE_ROLE = "changeRole?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private String login;
	 private LazyDataModel<PrzypisanieRoli> lazyModel;
	

	@Inject
	ExternalContext extcontext;

	@Inject
	RegisterBB registerBB;
	
	
	@Inject
	@ManagedProperty("#{txtAdmin}")
	private ResourceBundle txtAdmin;
	
	@Inject
	Flash flash;

	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@Inject
	FacesContext context;
	
	@EJB
	PrzypisanieRoliDAO przypisanieRoliDAO;
	


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@PostConstruct
	public void init() {
		lazyModel = new LazyUzytkownikDataModel(getList());
		System.out.println(lazyModel);
	}
	
	public LazyDataModel<PrzypisanieRoli> getLazyModel() {
		return lazyModel;
	}
	
	
	public String changeRole(PrzypisanieRoli przypisanieRoli) {
		flash.put("przypisanieRoli", przypisanieRoli);
		return PAGE_CHANGE_ROLE;
	}
	
	public String resetPassword(PrzypisanieRoli przypisanieRoli) {
		String hashed =  registerBB.generateHashedPassword("creanote");
		przypisanieRoli.getUzytkownik().setHaslo(hashed);
		uzytkownikDAO.update(przypisanieRoli.getUzytkownik());
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, txtAdmin.getString("passwordChanged"), null));
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String deleteUser(PrzypisanieRoli przypisanieRoli) {
		uzytkownikDAO.delete(przypisanieRoli.getUzytkownik());
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,txtAdmin.getString("userDeleted"), null));
		return PAGE_STAY_AT_THE_SAME;
	}
	

	public List<Uzytkownik> getFullList() {
		List<Uzytkownik> list = uzytkownikDAO.getFullList();
		
		List<Object[]> nowalista = null;
		
		
		return list;
	}

	public List<PrzypisanieRoli> getList() {
		List<PrzypisanieRoli> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (login != null && login.length() > 0) {
			searchParams.put("login", login);
		}
		list = przypisanieRoliDAO.getList(searchParams);
	
		
		
		//
		return list;

	}

}
