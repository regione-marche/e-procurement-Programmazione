package it.maggioli.ssointegrms.repositories;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.Profilo;
import it.maggioli.ssointegrms.domain.Uffint;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.UserSearchResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@Repository
public class UserCriteriaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCriteriaRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public UserSearchResultDTO loadListaUtenti(//
                                               final String denominazione, //
                                               final String username, //
                                               final String usernameCF, //
                                               final String abilitato, //
                                               final String codiceFiscale, //
                                               final String email, //
                                               final List<String> ufficiIntestatari, //
                                               final String gestioneUtenti, //
                                               final String administrator, //
                                               final String profilo, //
                                               final boolean utenteDelegatoGestioneUtenti, //
                                               final boolean registrazioneLoginCF, //
                                               final Integer skip, //
                                               final Integer take, //
                                               final String sortKey, //
                                               final String sortDirectionKey, //
                                               final boolean paginated) {

        LOGGER.debug(
                "Execution start UserCriterionRepository::loadListaUtenti for descrizione [ {} ], nomeUtente [ {} ], usernameCF [ {} ], abilitato [ {} ], "
                        + "codiceFiscale [ {} ], email [ {} ], ufficiIntestatari [ {} ], gestioneUtenti [ {} ], administrator [ {} ], profilo [ {} ], "
                        + "utenteDelegatoGestioneUtenti [ {} ], registrazioneLoginCF [ {} ]",
                denominazione, username, usernameCF, abilitato, codiceFiscale, email, ufficiIntestatari, gestioneUtenti,
                administrator, profilo, utenteDelegatoGestioneUtenti, registrazioneLoginCF);

        UserSearchResultDTO dto = new UserSearchResultDTO();
        dto.setTotalCount(0L);
        dto.setUserList(new ArrayList<>());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Count query
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> userTableCount = countQuery.from(User.class);
        List<Predicate> countPredicates = getPredicateList(criteriaBuilder, countQuery, userTableCount, denominazione,
                username, usernameCF, abilitato, codiceFiscale, email, ufficiIntestatari, gestioneUtenti, administrator, profilo, utenteDelegatoGestioneUtenti, registrazioneLoginCF);

        countQuery.select(criteriaBuilder.countDistinct(userTableCount)).where(countPredicates.toArray(new Predicate[0]));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        dto.setTotalCount(totalCount);

        // Select query
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.distinct(true);
        Root<User> selectFrom = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(selectFrom);
        List<Predicate> selectPredicates = getPredicateList(criteriaBuilder, criteriaQuery, selectFrom, denominazione,
                username, usernameCF, abilitato, codiceFiscale, email, ufficiIntestatari, gestioneUtenti, administrator, profilo, utenteDelegatoGestioneUtenti, registrazioneLoginCF);
        select.where(selectPredicates.toArray(new Predicate[0]));
        if (paginated) {
            select = addSorting(criteriaBuilder, criteriaQuery, selectFrom, sortKey, sortDirectionKey);
        }


        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        if (paginated) {
            typedQuery.setFirstResult(skip);
            typedQuery.setMaxResults(take);
        }

        dto.setUserList(typedQuery.getResultList());

        return dto;
    }

    private <T> List<Predicate> getPredicateList(final CriteriaBuilder criteriaBuilder, //
                                                 final CriteriaQuery<T> criteriaQuery, //
                                                 final Root<User> from, //
                                                 final String denominazione, //
                                                 final String username, //
                                                 final String usernameCF, //
                                                 final String abilitato, //
                                                 final String codiceFiscale, //
                                                 final String email, //
                                                 final List<String> ufficiIntestatari, //
                                                 final String gestioneUtenti, //
                                                 final String administrator, //
                                                 final String profilo, //
                                                 final boolean utenteDelegatoGestioneUtenti, //
                                                 final boolean registrazioneLoginCF) {

        List<Predicate> predicates = new ArrayList<>();

        // Se sono un'utente delegato nascondo tutti gli utenti admin e abilitati a tutte le SA
        // cosi' come quelli con syscon 47/48/49/50
        if (utenteDelegatoGestioneUtenti) {
            // Escludo syscon 47/48/49/50
            predicates.add(criteriaBuilder.not(from.get("syscon").in(47L, 48L, 49L, 50L)));
            // Escludo admin e abilitati a tutte le SA
            predicates.add(notLike(criteriaBuilder, from, "syspwbou", AppConstants.OU_AMMINISTRATORE));
            predicates.add(notLike(criteriaBuilder, from, "syspwbou", AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI));
        }

        if (StringUtils.isNotBlank(denominazione)) {
            Predicate predicateSysute = like(criteriaBuilder, from, "sysute", denominazione);
            Predicate predicateSyscf = like(criteriaBuilder, from, "codiceFiscale", denominazione);
            Predicate predicateOR = criteriaBuilder.or(predicateSysute, predicateSyscf);
            predicates.add(predicateOR);
        }

        // filtro solo se sono admin oppure sono delegato e registrazione.loginCF e' diverso da 1
        if (!registrazioneLoginCF && StringUtils.isNotBlank(username)) {
            predicates.add(like(criteriaBuilder, from, "login", username));
        }

        if (registrazioneLoginCF && StringUtils.isNotBlank(usernameCF)) {
            Predicate predicateUsername = like(criteriaBuilder, from, "login", usernameCF);
            Predicate predicateCodiceFiscale = like(criteriaBuilder, from, "codiceFiscale", usernameCF);
            Predicate predicateOR = criteriaBuilder.or(predicateUsername, predicateCodiceFiscale);
            predicates.add(predicateOR);
        }

        if (StringUtils.isNotBlank(abilitato)) {
            if (AppConstants.COMBOBOX_SI.equals(abilitato)) {
                Predicate predicateNull = isNull(criteriaBuilder, from, "disabilitato");
                Predicate predicateAbilitato = equal(criteriaBuilder, from, "disabilitato", AppConstants.SYSDISAB_UTENTE_ABILITATO);
                Predicate predicateOR = criteriaBuilder.or(predicateNull, predicateAbilitato);
                predicates.add(predicateOR);
            } else if (AppConstants.COMBOBOX_NO.equals(abilitato))
                // not equal a 0 anziche' equal a 1 per evitare problemi che sysdisab in alcuni db, per qualche motivo, vale 2
                predicates.add(notEqual(criteriaBuilder, from, "disabilitato", AppConstants.SYSDISAB_UTENTE_ABILITATO));
        }

        if (!registrazioneLoginCF && StringUtils.isNotBlank(codiceFiscale)) {
            predicates.add(like(criteriaBuilder, from, "codiceFiscale", codiceFiscale));
        }

        if (StringUtils.isNotBlank(email)) {
            predicates.add(like(criteriaBuilder, from, "email", email));
        }

        if (ufficiIntestatari != null && !ufficiIntestatari.isEmpty()) {
            Join<User, Uffint> subRoot = from.join("uffints", JoinType.INNER);
            predicates.add(subRoot.get("codice").in(ufficiIntestatari.toArray()));
        }

        // VIGILANZA2-343 rimosso controllo per il quale, se si e' utente gestore,
        // non si filtra per combobox "gestione utenti"
        if (StringUtils.isNoneBlank(gestioneUtenti)) {
            if ("2".equals(gestioneUtenti)) {
                Predicate likeOU11 = like(criteriaBuilder, from, "syspwbou",
                        AppConstants.OU_GESTIONE_UTENTI_COMPLETA);
                Predicate likeOU12 = like(criteriaBuilder, from, "syspwbou",
                        AppConstants.OU_GESTIONE_UTENTI_OU12);
                Predicate andGestioneUtenti = criteriaBuilder.and(likeOU11, likeOU12);
                predicates.add(andGestioneUtenti);
            } else if ("3".equals(gestioneUtenti)) {
                predicates.add(like(criteriaBuilder, from, "syspwbou",
                        AppConstants.OU_GESTIONE_UTENTI_COMPLETA));
            }
        }

        if (!utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(administrator)) {
            if (AppConstants.COMBOBOX_SI.equals(administrator))
                predicates.add(like(criteriaBuilder, from, "syspwbou", AppConstants.OU_AMMINISTRATORE));
            else if (AppConstants.COMBOBOX_NO.equals(administrator))
                predicates
                        .add(notLike(criteriaBuilder, from, "syspwbou", AppConstants.OU_AMMINISTRATORE));
        }

        if (!utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(profilo)) {
            Join<User, Profilo> subRoot = from.join("profili", JoinType.INNER);
            Predicate codiceProfiloEqual = criteriaBuilder.equal(subRoot.get("codice"), profilo);
            predicates.add(codiceProfiloEqual);
        }

        return predicates;
    }

    private <T> Predicate like(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
                               final String searchField) {
        return criteriaBuilder.like(criteriaBuilder.lower(from.get(dbField)), "%" + searchField.toLowerCase() + "%");
    }

    private <T> Predicate notLike(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
                                  final String searchField) {
        return criteriaBuilder.notLike(criteriaBuilder.lower(from.get(dbField)), "%" + searchField.toLowerCase() + "%");
    }

    private <T> Predicate equal(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
                                final Object searchField) {
        return criteriaBuilder.equal(from.get(dbField), searchField);
    }

    private <T> Predicate notEqual(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField,
                                   final Object searchField) {
        return criteriaBuilder.notEqual(from.get(dbField), searchField);
    }

    private <T> Predicate isNull(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField) {
        return criteriaBuilder.isNull(from.get(dbField));
    }

    private <T> Predicate isNotNull(final CriteriaBuilder criteriaBuilder, final Root<T> from, final String dbField) {
        return criteriaBuilder.isNotNull(from.get(dbField));
    }

    private CriteriaQuery<User> addSorting(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<User> query,
                                           final Root<User> from, final String sortKey, final String sortDirectionKey) {
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
                case "syscon":
                    correctedSortKey = "syscon";
                    break;
                case "nome":
                    correctedSortKey = "sysute";
                    break;
                case "codiceFiscale":
                    correctedSortKey = "codiceFiscale";
                    break;
                case "username":
                    correctedSortKey = "login";
                    break;
                case "email":
                    correctedSortKey = "email";
                    break;
                case "dataUltimoAccesso":
                    correctedSortKey = "dataUltimoAccesso";
                    break;
                default:
                    correctedSortKey = sortKey;
                    break;
            }
            return correctedSortKey;
        }
        return sortKey;
    }
}
