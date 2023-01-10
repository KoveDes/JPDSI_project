package creanote.notes;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import creanote.dao.NotatkaDAO;
import creanote.dao.NoteDAO;
import creanote.dao.UzytkownikDAO;
import creanote.entities.Notatka;
import creanote.entities.Note;
import creanote.entities.Uzytkownik;

@Named
@ViewScoped
public class NotebookBB implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NOTEBOOK = "notebook?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = "notebooks";

	private Note note = new Note();
	private Boolean publicNote;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	@Inject
	@ManagedProperty("#{txtNotes}")
	private ResourceBundle txtNotes;
	
	@EJB
	NoteDAO noteDAO;

	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@EJB
	NotatkaDAO notatkaDAO;

	public Boolean getPublicNote() {
		return publicNote;
	}

	public void setPublicNote(Boolean publicNote) {
		this.publicNote = publicNote;
	}

	RemoteClient<Uzytkownik> uzytkownikRemote;
	RemoteClient<Uzytkownik> uzytkownikRemoteNew;

	public Note getNote() {
		return note;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public String enterNotebook(Note note) {
		flash.put("Notebook", note);
		return PAGE_NOTEBOOK;
	}

	public List<Note> getList() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		uzytkownikRemoteNew = uzytkownikRemote.load(session);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("uzytkownik", (Uzytkownik) uzytkownikRemoteNew.getDetails());
		List<Note> list = noteDAO.getList(searchParams);
		return list;
	}

	public String deleteNotebook(Note note) {
	
		
		//usuń wszystkie notatki powiązane z tym notesem
		//pobierz wszystkie notatki zwiazne z tym notesem
		List<Notatka> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put("note", note);
		list = notatkaDAO.getList(searchParams);
		//zrb for i kazda usun
		for(Notatka notatka: list) {
			notatkaDAO.remove(notatka);
		}
		
		
		
		//
		noteDAO.remove(note);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, txtNotes.getString("successfulDelete"), null));
		return PAGE_STAY_AT_THE_SAME;
	}

	public Boolean checkForDoubling() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		uzytkownikRemoteNew = uzytkownikRemote.load(session);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("nazwa", note.getNazwa());
		searchParams.put("uzytkownik", (Uzytkownik) uzytkownikRemoteNew.getDetails());

		System.out.println(uzytkownikRemoteNew.getDetails().getLogin());
		System.out.println(noteDAO.getList(searchParams));
		if (noteDAO.getList(searchParams).isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public String createNotebook() {
		try {
			if (checkForDoubling()) {
				// nazwa/publiczna/iduzytkownik
				if (publicNote == true) {
					note.setPubliczna((byte) 1);
				} else {
					note.setPubliczna((byte) 0);
				}
				note.setUzytkownik(uzytkownikRemoteNew.getDetails());
				noteDAO.create(note);
				Uzytkownik x = uzytkownikDAO.findByLogin(uzytkownikRemoteNew.getDetails().getLogin()).get(0);
				x.setLiczbaNotesow(x.getLiczbaNotesow() + 1);
				uzytkownikDAO.update(x);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, txtNotes.getString("successfulAdd"), null));
				return PAGE_STAY_AT_THE_SAME;
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, txtNotes.getString("notebookExists"), "");
				return PAGE_STAY_AT_THE_SAME;
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, txtNotes.getString("simpleError"), null));
			return PAGE_STAY_AT_THE_SAME;
		}
	}

}
