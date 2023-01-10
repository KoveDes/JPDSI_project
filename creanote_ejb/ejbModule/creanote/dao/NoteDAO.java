package creanote.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import creanote.entities.Note;
import creanote.entities.Uzytkownik;

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

	public Integer getCount() {
		Integer count = null;
		Query query = em.createQuery("select count(n) from Note n");
		try {
			count = Integer.parseInt(query.getSingleResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
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
	
	
	
	public List<Note> getList(Map<String, Object> searchParams) {
		List<Note> list = null;

		// 1. Build query string with parameters
		String select = "select n ";
		String from = "from Note n ";
		String where = "";
		String orderby = "order by n.nazwa";

		// search for login
		String nazwa = (String) searchParams.get("nazwa");
		if (nazwa != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.nazwa like :nazwa ";
		}
		// search for password
		Uzytkownik uzytkownik = (Uzytkownik) searchParams.get("uzytkownik");
		if (uzytkownik != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.uzytkownik like :uzytkownik";
		}


		// 2. Create query object
		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (nazwa != null) {
			query.setParameter("nazwa", nazwa + "%");
		}
		if (uzytkownik != null) {
			query.setParameter("uzytkownik", uzytkownik);
		}

		// ... other parameters ...

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public List<Note> findByName(String nazwa, Uzytkownik uzytkownik) {
		List<Note> list = null;
		String select = "select n ";
		String from = "from Note n ";
		String where = "where n.nazwa = :nazwa and n.uzytkownik = :uzytkownik";
		String orderby = "";
		Query query = em.createQuery(select + from + where);
		query.setParameter("nazwa", nazwa + "%");
		query.setParameter("uzytkownik", uzytkownik + "%");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

}