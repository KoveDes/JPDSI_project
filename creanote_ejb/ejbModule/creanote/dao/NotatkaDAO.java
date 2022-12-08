package creanote.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.Notatka;

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
	
	
}