package creanote.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import creanote.entities.PrzypisanieRoli;
import creanote.entities.PrzypisanieRoliPK;


@Stateless
public class PrzypisanieRoliPKDAO {
	@PersistenceContext
	protected EntityManager em;

	public void create(PrzypisanieRoliPK przypisanieRoliPK) {
		em.persist(przypisanieRoliPK);
	}

	public PrzypisanieRoliPK merge(PrzypisanieRoliPK przypisanieRoliPK) {
		return em.merge(przypisanieRoliPK);
	}

	public void remove(PrzypisanieRoliPK przypisanieRoliPK) {
		em.remove(em.merge(przypisanieRoliPK));
	}

	public PrzypisanieRoliPK find(Object id) {
		return em.find(PrzypisanieRoliPK.class, id);
	}
	
	public List<PrzypisanieRoliPK> findByUzytkownikId(Integer iduzytkownik) {
		List<PrzypisanieRoliPK> list = null;
		String select = "select p ";
		String from = "from PrzypisanieRoliPK p ";
		String where = "where p.iduzytkownik = :iduzytkownik";
		Query query = em.createQuery(select + from + where).setParameter("iduzytkownik", iduzytkownik).setMaxResults(1);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

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