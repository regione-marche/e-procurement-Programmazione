package it.maggioli.ssointegrms.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.domain.WLogEventi;
import it.maggioli.ssointegrms.dto.WLogEventiSearchResultDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public class WLogEventiCriteriaRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(WLogEventiCriteriaRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	public WLogEventiSearchResultDTO loadListaEventi(//
			final String codApp, //
			final Long idEvento, //
			final Date dataOraDa, //
			final Date dataOraA, //
			final String descrizioneUtente, //
			final String livelloEvento, //
			final String codiceEvento, //
			final String oggettoEvento, //
			final String descrizione, //
			final Integer skip, //
			final Integer take, //
			final String sortKey, //
			final String sortDirectionKey) {

		LOGGER.debug(
				"Execution start WLogEventiCriteriaRepository::loadListaEventi for idEvento [ {} ], dataEventoDa [ {} ], dataEventoA [ {} ], descrizioneUtente [ {} ], "
						+ "livelloEvento [ {} ], codiceEvento [ {} ], oggettoEvento [ {} ], descrizione [ {} ]",
				idEvento, dataOraDa, dataOraA, descrizioneUtente, livelloEvento, codiceEvento, oggettoEvento,
				descrizione);

		WLogEventiSearchResultDTO dto = new WLogEventiSearchResultDTO();
		dto.setTotalCount(0L);
		dto.setEventsList(new ArrayList<>());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Count query
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<WLogEventi> userTableCount = countQuery.from(WLogEventi.class);
		List<Predicate> countPredicates = getPredicateList(criteriaBuilder, countQuery, userTableCount, codApp,
				idEvento, dataOraDa, dataOraA, descrizioneUtente, livelloEvento, codiceEvento, oggettoEvento,
				descrizione);

		countQuery.select(criteriaBuilder.count(userTableCount)).where(countPredicates.toArray(new Predicate[0]));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		dto.setTotalCount(totalCount);

		// Select query
		CriteriaQuery<WLogEventi> criteriaQuery = criteriaBuilder.createQuery(WLogEventi.class);
		Root<WLogEventi> selectFrom = criteriaQuery.from(WLogEventi.class);
		CriteriaQuery<WLogEventi> select = criteriaQuery.select(selectFrom);
		List<Predicate> selectPredicates = getPredicateList(criteriaBuilder, criteriaQuery, selectFrom, codApp,
				idEvento, dataOraDa, dataOraA, descrizioneUtente, livelloEvento, codiceEvento, oggettoEvento,
				descrizione);
		select.where(selectPredicates.toArray(new Predicate[0]));
		select = addSorting(criteriaBuilder, criteriaQuery, selectFrom, sortKey, sortDirectionKey);

		TypedQuery<WLogEventi> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(skip);
		typedQuery.setMaxResults(take);

		dto.setEventsList(typedQuery.getResultList());

		return dto;
	}

	private <T> List<Predicate> getPredicateList(final CriteriaBuilder criteriaBuilder, //
			final CriteriaQuery<T> criteriaQuery, //
			final Root<WLogEventi> from, //
			final String codApp, //
			final Long idEvento, //
			final Date dataOraDa, //
			final Date dataOraA, //
			final String descrizioneUtente, //
			final String livelloEvento, //
			final String codiceEvento, //
			final String oggettoEvento, //
			final String descrizione) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(codApp)) {
			predicates.add(equal(criteriaBuilder, from, "codApp", codApp));
		}

		if (idEvento != null) {
			predicates.add(equal(criteriaBuilder, from, "idEvento", idEvento));
		}

		if (dataOraDa != null && dataOraA != null) {

			predicates.add(dateGreaterThanOrEqualTo(criteriaBuilder, from, "dataOra", dataOraDa));
			predicates.add(dateLessThanOrEqualTo(criteriaBuilder, from, "dataOra", dataOraA));

		} else {

			if (dataOraDa != null) {
				predicates.add(dateGreaterThanOrEqualTo(criteriaBuilder, from, "dataOra", dataOraDa));
			}

			if (dataOraA != null) {
				predicates.add(dateLessThanOrEqualTo(criteriaBuilder, from, "dataOra", dataOraA));
			}
		}

		if (StringUtils.isNotBlank(descrizioneUtente)) {
			Join<WLogEventi, User> subRoot = from.join("user", JoinType.INNER);
			Predicate userJoinPredicate = criteriaBuilder.like(criteriaBuilder.lower(subRoot.get("sysute")),
					"%" + descrizioneUtente.toLowerCase() + "%");
			predicates.add(userJoinPredicate);
		}

		if (StringUtils.isNotBlank(livelloEvento)) {
			predicates.add(equal(criteriaBuilder, from, "livelloEvento", livelloEvento));
		}

		if (StringUtils.isNotBlank(codiceEvento)) {
			predicates.add(equal(criteriaBuilder, from, "codiceEvento", codiceEvento));
		}

		if (StringUtils.isNotBlank(oggettoEvento)) {
			predicates.add(like(criteriaBuilder, from, "oggettoEvento", oggettoEvento));
		}

		if (StringUtils.isNotBlank(descrizione)) {
			predicates.add(like(criteriaBuilder, from, "descrizione", descrizione));
		}

		return predicates;
	}

	private <T> Predicate like(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
			final String searchField) {
		return criteriaBuilder.like(criteriaBuilder.lower(from.get(dbField)), "%" + searchField.toLowerCase() + "%");
	}

	private <T> Predicate equal(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
			final Object searchField) {
		return criteriaBuilder.equal(from.get(dbField), searchField);
	}

	private <T> Predicate dateGreaterThanOrEqualTo(final CriteriaBuilder criteriaBuilder, final Root<T> from,
			final String dbField, final Date searchField) {
		return criteriaBuilder.greaterThanOrEqualTo(from.get(dbField), searchField);
	}

	private <T> Predicate dateLessThanOrEqualTo(final CriteriaBuilder criteriaBuilder, final Root<T> from,
			final String dbField, final Date searchField) {
		return criteriaBuilder.lessThanOrEqualTo(from.get(dbField), searchField);
	}

	private CriteriaQuery<WLogEventi> addSorting(final CriteriaBuilder criteriaBuilder,
			final CriteriaQuery<WLogEventi> query, final Root<WLogEventi> from, final String sortKey,
			final String sortDirectionKey) {
		if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(sortDirectionKey)) {
			Order order = null;
			order = "asc".equals(sortDirectionKey) ? criteriaBuilder.asc(from.get(mapOrder(sortKey)))
					: criteriaBuilder.desc(from.get(mapOrder(sortKey)));
			query.orderBy(order);
		}
		return query;
	}

	private String mapOrder(final String sortKey) {
		if (StringUtils.isNotBlank(sortKey)) {
			String correctedSortKey = "";
			switch (sortKey) {
			case "idEvento":
				correctedSortKey = "idEvento";
				break;
			case "dataOra":
				correctedSortKey = "dataOra";
				break;
			case "livelloEvento":
				correctedSortKey = "livelloEvento";
				break;
			case "codiceEvento":
				correctedSortKey = "codiceEvento";
				break;
			case "oggettoEvento":
				correctedSortKey = "oggettoEvento";
				break;
			case "descrizione":
				correctedSortKey = "descrizione";
				break;
			default:
				correctedSortKey = "idEvento";
				break;
			}
			return correctedSortKey;
		}
		return sortKey;
	}
}
