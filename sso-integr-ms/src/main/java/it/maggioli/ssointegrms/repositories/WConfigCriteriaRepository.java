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

import it.maggioli.ssointegrms.common.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WConfig;
import it.maggioli.ssointegrms.dto.WConfigSearchResultDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public class WConfigCriteriaRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(WConfigCriteriaRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	public WConfigSearchResultDTO loadListaConfigurazioni(//
			final String sezione, //
			final String chiave, //
			final String valore, //
			final String descrizione, //
			final Integer skip, //
			final Integer take, //
			final String sortKey, //
			final String sortDirectionKey) {

		LOGGER.debug(
				"Execution start WConfigCriteriaRepository::loadListaConfigurazioni for sezione [ {} ], chiave [ {} ], valore [ {} ], "
						+ "descrizione [ {} ]",
				sezione, chiave, valore, descrizione);

		WConfigSearchResultDTO dto = new WConfigSearchResultDTO();
		dto.setTotalCount(0L);
		dto.setConfigList(new ArrayList<>());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Count query
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<WConfig> userTableCount = countQuery.from(WConfig.class);
		List<Predicate> countPredicates = getPredicateList(criteriaBuilder, countQuery, userTableCount, sezione, chiave,
				valore, descrizione);

		countQuery.select(criteriaBuilder.count(userTableCount)).where(countPredicates.toArray(new Predicate[0]));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		dto.setTotalCount(totalCount);

		// Select query
		CriteriaQuery<WConfig> criteriaQuery = criteriaBuilder.createQuery(WConfig.class);
		Root<WConfig> selectFrom = criteriaQuery.from(WConfig.class);
		CriteriaQuery<WConfig> select = criteriaQuery.select(selectFrom);
		List<Predicate> selectPredicates = getPredicateList(criteriaBuilder, criteriaQuery, selectFrom, sezione, chiave,
				valore, descrizione);
		select.where(selectPredicates.toArray(new Predicate[0]));
		select = addSorting(criteriaBuilder, criteriaQuery, selectFrom, sortKey, sortDirectionKey);

		TypedQuery<WConfig> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(skip);
		typedQuery.setMaxResults(take);

		dto.setConfigList(typedQuery.getResultList());

		return dto;
	}

	private <T> List<Predicate> getPredicateList(final CriteriaBuilder criteriaBuilder, //
			final CriteriaQuery<T> criteriaQuery, //
			final Root<WConfig> from, //
			final String sezione, //
			final String chiave, //
			final String valore, //
			final String descrizione) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(sezione)) {
			predicates.add(like(criteriaBuilder, from, "sezione", sezione));
		}

		if (StringUtils.isNotBlank(chiave)) {
			predicates.add(like(criteriaBuilder, from, "chiave", chiave, true));
		}

		if (StringUtils.isNotBlank(valore)) {
			predicates.add(like(criteriaBuilder, from, "valore", valore));
			Predicate predicateNull = isNull(criteriaBuilder, from, "criptato");
			Predicate predicateNotCriptato = notEqual(criteriaBuilder, from, "criptato", AppConstants.W_CONFIG_SI);
			Predicate predicateOR = criteriaBuilder.or(predicateNull, predicateNotCriptato);
			predicates.add(predicateOR);
		}

		if (StringUtils.isNotBlank(descrizione)) {
			predicates.add(like(criteriaBuilder, from, "descrizione", descrizione));
		}

		return predicates;
	}

	private <T> Predicate isNull(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField) {
		return criteriaBuilder.isNull(from.get(dbField));
	}

	private <T> Predicate notEqual(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
								   final Object searchField) {
		return criteriaBuilder.notEqual(from.get(dbField), searchField);
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

	private CriteriaQuery<WConfig> addSorting(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<WConfig> query,
			final Root<WConfig> from, final String sortKey, final String sortDirectionKey) {
		if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(sortDirectionKey)) {
			Order order = null;
			if ("chiave".equals(sortKey)) {
				order = "asc".equals(sortDirectionKey) ? criteriaBuilder.asc(from.get("id").get(mapOrder(sortKey)))
						: criteriaBuilder.desc(from.get("id").get(mapOrder(sortKey)));
			} else {
				order = "asc".equals(sortDirectionKey) ? criteriaBuilder.asc(from.get(mapOrder(sortKey)))
						: criteriaBuilder.desc(from.get(mapOrder(sortKey)));
			}
			query.orderBy(order);
		}
		return query;
	}

	private String mapOrder(final String sortKey) {
		if (StringUtils.isNotBlank(sortKey)) {
			String correctedSortKey = "";
			switch (sortKey) {
			case "sezione":
				correctedSortKey = "sezione";
				break;
			case "valore":
				correctedSortKey = "valore";
				break;
			case "descrizione":
				correctedSortKey = "descrizione";
				break;
			case "criptato":
				correctedSortKey = "criptato";
				break;
			case "chiave":
			default:
				correctedSortKey = "chiave";
				break;
			}
			return correctedSortKey;
		}
		return sortKey;
	}
}
