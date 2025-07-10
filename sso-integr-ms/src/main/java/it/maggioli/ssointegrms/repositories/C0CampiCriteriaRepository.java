package it.maggioli.ssointegrms.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.C0Campi;
import it.maggioli.ssointegrms.dto.C0CampiSearchResultDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public class C0CampiCriteriaRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(C0CampiCriteriaRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	public C0CampiSearchResultDTO loadListaC0Campi(//
			final String searchData, //
			final Integer skip, //
			final Integer take, //
			final String sortKey, //
			final String sortDirectionKey) {

		LOGGER.debug("Execution start C0CampiCriteriaRepository::loadListaC0Campi for searchData [ {} ]", searchData);

		C0CampiSearchResultDTO dto = new C0CampiSearchResultDTO();
		dto.setTotalCount(0L);
		dto.setC0CampiList(new ArrayList<>());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Count query
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<C0Campi> tableCount = countQuery.from(C0Campi.class);
		List<Predicate> countPredicates = getPredicateList(criteriaBuilder, countQuery, tableCount, searchData);

		countQuery.select(criteriaBuilder.count(tableCount)).where(countPredicates.toArray(new Predicate[0]));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		dto.setTotalCount(totalCount);

		// Select query
		CriteriaQuery<C0Campi> criteriaQuery = criteriaBuilder.createQuery(C0Campi.class);
		Root<C0Campi> selectFrom = criteriaQuery.from(C0Campi.class);
		CriteriaQuery<C0Campi> select = criteriaQuery.select(selectFrom);
		List<Predicate> selectPredicates = getPredicateList(criteriaBuilder, criteriaQuery, selectFrom, searchData);
		select.where(selectPredicates.toArray(new Predicate[0]));
		select = addSorting(criteriaBuilder, criteriaQuery, selectFrom, sortKey, sortDirectionKey);

		TypedQuery<C0Campi> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(skip);
		typedQuery.setMaxResults(take);

		dto.setC0CampiList(typedQuery.getResultList());

		return dto;
	}

	private <T> List<Predicate> getPredicateList(final CriteriaBuilder criteriaBuilder, //
			final CriteriaQuery<T> criteriaQuery, //
			final Root<C0Campi> from, //
			final String searchData) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(searchData)) {
			Predicate like_c0c_mne_ber = like(criteriaBuilder, from, "c0c_mne_ber", searchData);
			Predicate like_coc_mne_uni = like(criteriaBuilder, from, "coc_mne_uni", searchData);
			Predicate orSearchData = criteriaBuilder.or(like_c0c_mne_ber, like_coc_mne_uni);
			predicates.add(orSearchData);
		}

		return predicates;
	}

	private <T> Predicate like(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
			final String searchField) {
		return like(criteriaBuilder, from, dbField, searchField, false);
	}

	private <T> Predicate like(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
			final String searchField, final boolean chiave) {
		if (chiave)
			return criteriaBuilder.like(criteriaBuilder.lower(from.get("id").get(dbField)),
					"%" + searchField.toLowerCase() + "%");
		else
			return criteriaBuilder.like(criteriaBuilder.lower(from.get(dbField)),
					"%" + searchField.toLowerCase() + "%");
	}

	private CriteriaQuery<C0Campi> addSorting(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<C0Campi> query,
			final Root<C0Campi> from, final String sortKey, final String sortDirectionKey) {
		if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(sortDirectionKey)) {
			Order order = "asc".equals(sortDirectionKey) ? criteriaBuilder.asc(from.get(mapOrder(sortKey)))
					: criteriaBuilder.desc(from.get(mapOrder(sortKey)));
			query.orderBy(order);
		}
		return query;
	}

	private String mapOrder(final String sortKey) {
		if (StringUtils.isNotBlank(sortKey)) {
			String correctedSortKey = "";
			switch (sortKey) {
			case "c0c_mne_ber":
				correctedSortKey = "c0c_mne_ber";
				break;
			case "coc_mne_uni":
				correctedSortKey = "coc_mne_uni";
				break;
			case "coc_mne_uni_entita":
				correctedSortKey = "coc_mne_uni";
				break;
			case "coc_mne_uni_campo":
				correctedSortKey = "coc_mne_uni";
				break;
			case "coc_des":
				correctedSortKey = "coc_des";
				break;
			case "c0c_fs":
				correctedSortKey = "c0c_fs";
				break;
			case "c0c_tab1":
				correctedSortKey = "c0c_tab1";
				break;
			case "coc_dom":
				correctedSortKey = "coc_dom";
				break;
			default:
				correctedSortKey = "c0c_mne_ber";
				break;
			}
			return correctedSortKey;
		}
		return sortKey;
	}
}
