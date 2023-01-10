package creanote.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import creanote.dao.PrzypisanieRoliDAO;
import creanote.dao.PrzypisanieRoliPKDAO;
import creanote.entities.PrzypisanieRoli;

@Named
@ViewScoped
public class RoleEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ADMIN = "admin?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private PrzypisanieRoli loaded = null;
	private PrzypisanieRoli przypisanieRoli = null;

	@Inject
	FacesContext context;
	
	@Inject
	@ManagedProperty("#{txtAdmin}")
	private ResourceBundle txtAdmin;

	@Inject
	Flash flash;

	@EJB
	PrzypisanieRoliDAO przypisanieRoliDAO;

	public PrzypisanieRoli getPrzypisanieRoli() {
		return przypisanieRoli;
	}

	public void setPrzypisanieRoli(PrzypisanieRoli przypisanieRoli) {
		this.przypisanieRoli = przypisanieRoli;
	}

	public void onLoad() throws IOException {
		loaded = (PrzypisanieRoli) flash.get("przypisanieRoli");
		System.out.println(loaded);
		if (loaded != null) {
			przypisanieRoli = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtAdmin.getString("wrongUse"), null));
		}
	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
			// (iduzytkownika w tabeli przypisanieroli, usuń je i wstaw nowe.)
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("login", przypisanieRoli.getUzytkownik().getLogin());
			przypisanieRoliDAO.remove(przypisanieRoliDAO.getList(searchParams).get(0));
			System.out.println("IDRola: " + przypisanieRoli.getRola().getIdrola());
			przypisanieRoli.setDataModyfikacji(new Date());
			przypisanieRoliDAO.merge(przypisanieRoli);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, txtAdmin.getString("wrongRoleChange"), null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_ADMIN;
	}

}
