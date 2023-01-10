package creanote.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import creanote.entities.Uzytkownik;

@Stateless
public class UzytkownikDAO {
	@PersistenceContext
	protected EntityManager em;

	public void insert(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}

	public Uzytkownik update(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}

	public void delete(Uzytkownik uzytkownik) {
		em.remove(em.merge(uzytkownik));
	}

	public Uzytkownik get(Object id) {
		return em.find(Uzytkownik.class, id);
	}

	public List<Uzytkownik> getFullList() {

		List<Uzytkownik> list = null;

		Query query = em.createQuery("select u from Uzytkownik u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Uzytkownik> findByLogin(String login) {
		List<Uzytkownik> list = null;
		String select = "select u ";
		String from = "from Uzytkownik u ";
		String where = "where u.login = :login";
		String orderby = "";
		Query query = em.createQuery(select + from + where).setParameter("login", login).setMaxResults(1);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
	
	public Integer getCount() {
		Integer count= null;
		String q = "select count(*) from Uzytkownik";
		Query query = em.createQuery("select count(u) from Uzytkownik u");
		try {
			count = Integer.parseInt(query.getSingleResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Uzytkownik> getUserFromDataBase(String login, String password) {
		List<Uzytkownik> list = null;
		String select = "select u ";
		String from = "from Uzytkownik u ";
		String where = "where u.login = :login";
		String orderby = "";
		Query query = em.createQuery(select + from + where).setParameter("login", login).setMaxResults(1);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public List<Uzytkownik> getList(Map<String, Object> searchParams) {
		List<Uzytkownik> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from Uzytkownik u ";
		String where = "";
		String orderby = "order by u.login";

		// search for login
		String login = (String) searchParams.get("login");
		if (login != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.login like :login ";
		}
		// search for password
		String password = (String) searchParams.get("haslo");
		if (password != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.haslo like :haslo ";
		}


		// 2. Create query object
		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (login != null) {
			query.setParameter("login", login + "%");
		}
		if (password != null) {
			query.setParameter("haslo", password + "%");
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
	// find
	// getUserfromLogin(

}