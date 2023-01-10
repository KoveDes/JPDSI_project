package creanote.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import creanote.entities.Notatka;
import creanote.entities.Note;

@Stateless
public class NotatkaDAO {
	@PersistenceContext
	protected EntityManager em;

	public void create(Notatka notatka) {
		em.persist(notatka);
	}

	public Notatka merge(Notatka notatka) {
		return em.merge(notatka);
	}

	public void remove(Notatka notatka) {
		em.remove(em.merge(notatka));
	}

	public Notatka find(Object id) {
		return em.find(Notatka.class, id);
	}

	public List<Notatka> getFullList() {

		List<Notatka> list = null;

		Query query = em.createQuery("select n from Notatka n");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Integer getCount() {
		Integer count = null;
		Query query = em.createQuery("select count(n) from Notatka n");
		try {
			count = Integer.parseInt(query.getSingleResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Notatka> getList(Map<String, Object> searchParams) {
		List<Notatka> list = null;

		String select = "select n ";
		String from = "from Notatka n ";
		String where = "";
		String orderby = "order by n.nazwa asc, n.nazwa";

		Note note = (Note) searchParams.get("note");
		if (note != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.note = :note ";
		}
		
		String nazwa = (String) searchParams.get("nazwa");
		if (nazwa != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.nazwa like :nazwa ";
		}

		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (nazwa != null) {
			query.setParameter("nazwa", nazwa+"%");
		}
		
		
		if (note != null) {
			query.setParameter("note", note);
		}
		

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Notatka> getListByNotebook(Map<String, Object> searchParams) {
		List<Notatka> list = null;

		String select = "select n ";
		String from = "from Notatka n ";
		String where = "";

		Note note = (Note) searchParams.get("note");
		if (note != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.note = :note ";
		}

		String nazwa = (String) searchParams.get("nazwa");
		if (note != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.nazwa = :nazwa ";
		}

		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (note != null) {
			query.setParameter("note", note);
		}
		if (note != null) {
			query.setParameter("nazwa", nazwa);
		}
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Notatka> getListByPublicStatus(Map<String, Object> searchParams) {
		List<Notatka> list = null;

		String select = "select n ";
		String from = "from Notatka n ";
		String where = "";
		String orderby = "order by n.nazwa asc";

		// search for public status
		byte publiczna = (byte) searchParams.get("publiczna");
		if (publiczna != 0) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "n.publiczna = :publiczna ";
		}

		// ... other parameters ...

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (publiczna != 0) {
			query.setParameter("publiczna", publiczna);
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

}