package creanote.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.PrzypisanieRoli;
import creanote.entities.PrzypisanieRoliPK;
import creanote.entities.Uzytkownik;

@Stateless
public class PrzypisanieRoliDAO {
	@PersistenceContext
	protected EntityManager em;

	public void create(PrzypisanieRoli przypisanieRoli) {
		em.persist(przypisanieRoli);
	}

	public PrzypisanieRoli merge(PrzypisanieRoli przypisanieRoli) {
		return em.merge(przypisanieRoli);
	}

	public void remove(PrzypisanieRoli przypisanieRoli) {
		em.remove(em.merge(przypisanieRoli));
	}

	public PrzypisanieRoli find(Object id) {
		return em.find(PrzypisanieRoli.class, id);
	}
	
	public String getUserRoleFromDataBase(Uzytkownik uzytkownik) {
		String role = null;
		String select = "select p.rola.nazwa ";
		String from = "from PrzypisanieRoli p ";
		String where = "where p.uzytkownik = :uzytkownik";
		Query query = em.createQuery(select + from + where).setParameter("uzytkownik", uzytkownik);
		try {
			role = (String) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return role;
		
	}
	
	

	
	public List<PrzypisanieRoli> getFullList() {

		List<PrzypisanieRoli> list = null;

		Query query = em.createQuery("select p from PrzypisanieRoli p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<PrzypisanieRoli> getList(Map<String, Object> searchParams) {
		List<PrzypisanieRoli> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from PrzypisanieRoli p ";
		String where = "";
		String orderby = "";

		// search for login
		String login = (String) searchParams.get("login");
		if (login != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.uzytkownik.login like :login ";
		}
		


		// 2. Create query object
		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (login != null) {
			query.setParameter("login", login + "%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
}