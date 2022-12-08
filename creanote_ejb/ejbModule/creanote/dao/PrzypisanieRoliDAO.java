package creanote.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.PrzypisanieRoli;

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
	
	public List<PrzypisanieRoli> getFullList() {

		List<PrzypisanieRoli> list = null;

		Query query = em.createQuery("select p from PrzypisanieRoli n");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
}