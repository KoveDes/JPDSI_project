package creanote.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.Note;

@Stateless
public class NoteDAO {
	@PersistenceContext
	protected EntityManager em;

	public void create(Note note) {
		em.persist(note);
	}

	public Note merge(Note note) {
		return em.merge(note);
	}

	public void remove(Note note) {
		em.remove(em.merge(note));
	}

	public Note find(Object id) {
		return em.find(Note.class, id);
	}
	
	public List<Note> getFullList() {

		List<Note> list = null;

		Query query = em.createQuery("select n from Note n");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
}