package creanote.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.Uzytkownik;

@Stateless
public class UzytkownikDAO {
	@PersistenceContext
	protected EntityManager em;

	public void create(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}

	public Uzytkownik merge(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}

	public void remove(Uzytkownik uzytkownik) {
		em.remove(em.merge(uzytkownik));
	}

	public Uzytkownik find(Object id) {
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
	
//	public List<Uzytkownik> findByLogin() {
//		List<Uzytkownik> list = null;
//	}
	//find
	//getUserfromLogin(
	
}