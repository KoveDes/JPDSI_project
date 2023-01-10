package creanote.admin;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import creanote.dao.UzytkownikDAO;
import creanote.entities.PrzypisanieRoli;
import creanote.entities.Uzytkownik;

public class LazyUzytkownikDataModel extends  LazyDataModel<PrzypisanieRoli> {
	private static final long serialVersionUID = 1L;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	private List<PrzypisanieRoli> datasource;

	public LazyUzytkownikDataModel(List<PrzypisanieRoli> datasource) {
		this.datasource = datasource;
	}
	@Override
	public List<PrzypisanieRoli> load(int offset, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
		// apply offset & filters
		List<PrzypisanieRoli> users = datasource.stream().skip(offset).limit(pageSize).collect(Collectors.toList());

		return users;
	}
	
}