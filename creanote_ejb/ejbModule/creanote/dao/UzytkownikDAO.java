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
		String orderby = "order by p.surname asc, p.name";
		Query query = em.createQuery(select + from + where).setParameter("login", login).setMaxResults(1);
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