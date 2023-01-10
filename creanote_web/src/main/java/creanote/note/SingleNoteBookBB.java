package creanote.note;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import creanote.dao.NotatkaDAO;
import creanote.dao.NoteDAO;
import creanote.dao.PrzypisanieRoliDAO;
import creanote.entities.Notatka;
import creanote.entities.Note;

@Named
@SessionScoped
public class SingleNoteBookBB implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NOTE = "note?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = "notebook";

	private Note loaded = null;
	private Note note = null;

	private String nazwa;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@Inject
	@ManagedProperty("#{txtNotes}")
	private ResourceBundle txtNotes;

	@EJB
	NotatkaDAO notatkaDAO;
	
	@EJB
	NoteDAO noteDAO;

	public Note getNote() {
		return note;
	}

	public String createNotatka() {
		flash.put("note", note);
		return PAGE_NOTE;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String restart() {

		flash.put("Notebook", note);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String editNotatka(Notatka notatka) {
		flash.clear();
		flash.put("note", note);
		flash.put("notatka", notatka);
		return PAGE_NOTE;
	}

	public void onLoad() throws IOException {

		loaded = (Note) flash.get("Notebook");
		System.out.println("Jestem w onLoad");
		setNazwa(null);

		if (loaded != null) {
			note = loaded;
		}
		flash.clear();
		System.out.println(note.getNazwa());

	}

	public List<Notatka> getList() {
		List<Notatka> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		System.out.println("W getlist: " + note.getNazwa());
		searchParams.put("note", note);
		if (nazwa != null && nazwa.length() > 0) {
			searchParams.put("nazwa", nazwa);
		}
		list = notatkaDAO.getList(searchParams);
		return list;
	}

	public String deleteNotatka(Notatka notatka) {
		notatkaDAO.remove(notatka);
		note.setIloscNotatek(note.getIloscNotatek() - 1);
		noteDAO.merge(note);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, txtNotes.getString("noteDeleted"), null));
		return PAGE_STAY_AT_THE_SAME;
	}

}
