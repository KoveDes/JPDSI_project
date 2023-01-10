package creanote.note;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import creanote.entities.Notatka;
import creanote.entities.Note;

@Named
@ViewScoped
public class NoteEdit implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NOTE = "notebook?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Note loaded = null;
	private Note note = null;
	private Notatka loadedNotatka = null;
//	private Note note = null;
	private Notatka notatka = new Notatka();
	private Boolean publicNotatka;

	public Boolean getPublicNotatka() {
		return publicNotatka;
	}

	public void setPublicNotatka(Boolean publicNotatka) {
		this.publicNotatka = publicNotatka;
	}

	@Inject
	FacesContext context;

	@Inject
	@ManagedProperty("#{txtNote}")
	private ResourceBundle txtNote;
	
	@Inject
	Flash flash;

	@EJB
	NotatkaDAO notatkaDAO;

	@EJB
	NoteDAO noteDAO;
	
	public Notatka getNotatka() {
		return notatka;
	}

	public void onLoad() throws IOException {

		loaded = (Note) flash.get("note");
		if (loaded != null) {
			note = loaded;
			loadedNotatka = (Notatka) flash.get("notatka");
			if (loadedNotatka != null) {
				notatka = loadedNotatka;
			}
			flash.clear();
			notatka.setDataModyfikacji(new Date());
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txtNote.getString("invalidUse"), null));
		}
	}

	public Boolean checkIfNameExists() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("note", note);
		searchParams.put("nazwa", notatka.getNazwa());
		System.out.println(notatkaDAO.getListByNotebook(searchParams));
		if (notatkaDAO.getListByNotebook(searchParams).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String createNotatka() {
		flash.put("Notebook", note);
		try {
			if (loadedNotatka != null) {
				if (publicNotatka == true) {
					notatka.setPubliczna((byte) 1);
				} else {
					notatka.setPubliczna((byte) 0);
				}
				notatka.setNote(note);
				notatka.setDataModyfikacji(new Date());

				notatkaDAO.merge(notatka);

				return PAGE_NOTE;
			} else {
				if (checkIfNameExists()) {
					if (publicNotatka == true) {
						notatka.setPubliczna((byte) 1);
					} else {
						notatka.setPubliczna((byte) 0);
					}
					notatka.setNote(note);
					notatka.setDataModyfikacji(new Date());
					notatka.setDataUtworzenia(new Date()); 
					note.setIloscNotatek(note.getIloscNotatek() + 1);
					noteDAO.merge(note);
					notatkaDAO.create(notatka);

					return PAGE_NOTE;
				} else {
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, txtNote.getString("noteExists"), null));
					return PAGE_STAY_AT_THE_SAME;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, txtNote.getString("saveError"), null));
			return PAGE_STAY_AT_THE_SAME;
		}
	}

	public String goBack() {
		flash.put("noteEXIT", note);
		return PAGE_NOTE;
	}

}
