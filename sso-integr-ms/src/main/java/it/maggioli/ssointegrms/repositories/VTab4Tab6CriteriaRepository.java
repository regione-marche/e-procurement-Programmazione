package it.maggioli.ssointegrms.repositories;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.VTab4Tab6;
import it.maggioli.ssointegrms.dto.VTab4Tab6SearchResultDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public class VTab4Tab6CriteriaRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(VTab4Tab6CriteriaRepository.class);

	@Value("${application.codiceProdotto}")
	private String codiceProdotto;

	@PersistenceContext
	private EntityManager entityManager;

	public VTab4Tab6SearchResultDTO loadListaTabellati(//
			final String codiceTabellato, //
			final String descrizioneTabellato, //
			final Integer skip, //
			final Integer take, //
			final String sortKey, //
			final String sortDirectionKey) {

		LOGGER.debug(
				"Execution start VTab4Tab6CriteriaRepository::loadListaTabellati for codiceTabellato [ {} ], descrizioneTabellato",
				codiceTabellato, descrizioneTabellato);

		VTab4Tab6SearchResultDTO dto = new VTab4Tab6SearchResultDTO();
		dto.setTotalCount(0L);
		dto.setTabellatiList(new ArrayList<>());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Count query
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<VTab4Tab6> userTableCount = countQuery.from(VTab4Tab6.class);
		List<Predicate> countPredicates = getPredicateList(criteriaBuilder, countQuery, userTableCount, codiceTabellato,
				descrizioneTabellato);

		countQuery.select(criteriaBuilder.count(userTableCount)).where(countPredicates.toArray(new Predicate[0]));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		dto.setTotalCount(totalCount);

		// Select query
		CriteriaQuery<VTab4Tab6> criteriaQuery = criteriaBuilder.createQuery(VTab4Tab6.class);
		Root<VTab4Tab6> selectFrom = criteriaQuery.from(VTab4Tab6.class);
		CriteriaQuery<VTab4Tab6> select = criteriaQuery.select(selectFrom);
		List<Predicate> selectPredicates = getPredicateList(criteriaBuilder, criteriaQuery, selectFrom, codiceTabellato,
				descrizioneTabellato);
		select.where(selectPredicates.toArray(new Predicate[0]));
		select = addSorting(criteriaBuilder, criteriaQuery, selectFrom, sortKey, sortDirectionKey);

		TypedQuery<VTab4Tab6> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(skip);
		typedQuery.setMaxResults(take);

		dto.setTabellatiList(typedQuery.getResultList());

		return dto;
	}

	private <T> List<Predicate> getPredicateList(final CriteriaBuilder criteriaBuilder, //
			final CriteriaQuery<T> criteriaQuery, //
			final Root<VTab4Tab6> from, //
			final String codiceTabellato, //
			final String descrizioneTabellato) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(codiceTabellato)) {
			predicates.add(like(criteriaBuilder, from, "tab46Tip", codiceTabellato, true));
		}

		if (StringUtils.isNotBlank(descrizioneTabellato)) {
			predicates.add(like(criteriaBuilder, from, "tab46Desc", descrizioneTabellato));
		}

		// tab46cod
		String[] tab46CodList;
		if ("PL".equals(codiceProdotto)) {
			tab46CodList = new String[] { "G__1", "W__1", "W0_1", "W1_1", "G2_1", "G5_1", "PT_1" };
		} else if ("PG".equals(codiceProdotto)) {
			tab46CodList = new String[] { "G__1", "W__1", "W0_1", "W1_1", "G1_1" };
		} else {
			String codiceModuloAttivo = codiceProdotto + "_1";
			tab46CodList = new String[] { "G__1", "W__1", "W0_1", "W1_1", codiceModuloAttivo };
		}

		predicates.add(from.get("id").get("tab46Cod").in(Arrays.asList(tab46CodList)));

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

	private CriteriaQuery<VTab4Tab6> addSorting(final CriteriaBuilder criteriaBuilder,
			final CriteriaQuery<VTab4Tab6> query, final Root<VTab4Tab6> from, final String sortKey,
			final String sortDirectionKey) {
		if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(sortDirectionKey)) {
			Order order = null;
			if ("codiceTabellato".equals(sortKey)) {
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
			case "codiceTabellato":
				correctedSortKey = "tab46Tip";
				break;
			case "descrizioneTabellato":
				correctedSortKey = "tab46Desc";
				break;
			default:
				correctedSortKey = "tab46Desc";
				break;
			}
			return correctedSortKey;
		}
		return sortKey;
	}
}
