package creanote.main;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import creanote.dao.NotatkaDAO;
import creanote.dao.NoteDAO;
import creanote.dao.UzytkownikDAO;
import creanote.entities.Notatka;
import creanote.entities.Note;
import creanote.entities.Uzytkownik;

@Named
@ViewScoped
public class MainPage implements Serializable {

	@EJB
	NotatkaDAO notatkaDAO;
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	@EJB
	NoteDAO noteDAO;
		

	public List<Notatka> getList() {
		List<Notatka> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("publiczna",(byte) 1);
		list = notatkaDAO.getListByPublicStatus(searchParams);
		return list;
	}
	public Integer getUsersCount() {
		return uzytkownikDAO.getCount();
	}
	
	public Integer getNotebooksCount() {
		return noteDAO.getCount();
	}
	public Integer getNotesCount() {
		return notatkaDAO.getCount();
	}

}
