package it.appaltiecontratti.gestionereportsms.specifications;

import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.domain.WRicerche;
import it.appaltiecontratti.gestionereportsms.domain.WRicpro;
import it.appaltiecontratti.gestionereportsms.domain.WRicuffint;
import it.appaltiecontratti.gestionereportsms.dto.WRicercheDTO;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class RicercheSpecification {

    /**
     * Logger di classe
     * */
    private static final Logger logger = LoggerFactory.getLogger(RicercheSpecification.class);

    /**
     * Costanti e variabili
     * */

    /**
     * Metodi di classe
     * */
    public static Specification<WRicerche> getRicercheSpecification(
                                            final String codApp,
                                            final Integer famiglia,
                                            final String nome,
                                            final String descr,
                                            final Integer disp,
                                            final String sysute,
                                            final String sort,
                                            final String sortDirection){

        logger.info("Invoked getRicercheSpecification");
        return new Specification<>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<WRicerche> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate p = criteriaBuilder.conjunction();

                if (codApp != null && !StringUtils.isEmpty(codApp)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("codApp"), codApp));
                }
                if (famiglia != null) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("famiglia"), famiglia));
                }

                //Il nome arriva come stringa che mando in like nei criteriaBuilder.
                if (nome != null && !StringUtils.isEmpty(nome)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
                }
                if (descr != null && !StringUtils.isEmpty(descr)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("descr"), "%" + descr + "%"));
                }
                if (disp != null) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("disp"), disp));
                }

                if(sysute != null && !StringUtils.isEmpty(sysute)){
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("sysute"), "%" + sysute + "%"));
                }

                //Creo la join tra WRicerche e User
                Join<WRicerche, User> wrJoin = root.join("user", JoinType.INNER);

                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("userOwner"), wrJoin.get("syscon")));


                //Sort ASC della query in base ai parametri
                if (sortDirection.equals("asc")) {

                    if(sort.equals("idRicerca")) {
                        query.orderBy(criteriaBuilder.asc(root.get("idRicerca")));
                    }

                    if (sort.equals("nome")) {
                        query.orderBy(criteriaBuilder.asc(root.get("nome")));
                    }

                    if (sort.equals("descr")) {
                        query.orderBy(criteriaBuilder.asc(root.get("descr")));
                    }

                    if (sort.equals("disp")) {
                        query.orderBy(criteriaBuilder.asc(root.get("disp")));
                    }
                }

                //Sort DESC della query in base ai parametri
                if (sortDirection.equals("desc")) {

                    if(sort.equals("idRicerca")) {
                        query.orderBy(criteriaBuilder.asc(root.get("idRicerca")));
                    }

                    if (sort.equals("nome")) {
                        query.orderBy(criteriaBuilder.desc(root.get("nome")));
                    }

                    if (sort.equals("descr")) {
                        query.orderBy(criteriaBuilder.desc(root.get("descr")));
                    }

                    if (sort.equals("disp")) {
                        query.orderBy(criteriaBuilder.desc(root.get("disp")));
                    }
                }

                return p;
            }
        };
    }

    public static Specification<WRicerche> getReportPredifinitiSpecification(
            String syscon,
            String idProfilo,
            String uffInt,
            String codApp,
            String nome,
            String descrizione,
            String fieldToSort,
            String sortDirection
    ){

        logger.info("Invoked getRicercheSpecification");

        return new Specification<>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<WRicerche> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                //Creo le due join tra (WRicerche, WRicpro) e (WRicerche, WRicuffint)
                //Controllare che la join venga effettuata correttamente in debug-log di jdbc.
                Join<WRicerche, WRicpro> wRicercheLeftJoinWRicPro = root.join("profili", JoinType.LEFT);
                Join<WRicerche, WRicuffint> wRicercheLeftJoinWRicUffInt = root.join("uffInt", JoinType.LEFT);

                Predicate p = criteriaBuilder.conjunction();

                //INIZIO condizione where

                //Condizioni base
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("codApp"), codApp));
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("pubblicato"), 1));

                // Prima condizione di OR (PERSONALE <> 1 or OWNER = <id dell’utente>)
                Predicate orConditionFirst = criteriaBuilder.or(
                        criteriaBuilder.notEqual(root.get("personale"), 1),
                        criteriaBuilder.equal(root.get("userOwner"), syscon)
                );
                p = criteriaBuilder.and(p, orConditionFirst);

                // Seconda condizione di OR (r.TUTTI_PROFILI = 1 or p.COD_PROFILO = <codice profilo>)
                Predicate orConditionSecond = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tuttiProfili"), 1),
                        criteriaBuilder.equal(wRicercheLeftJoinWRicPro.get("id").get("codProfilo"), idProfilo)
                );
                p = criteriaBuilder.and(p, orConditionSecond);

                // Terza condizione di OR (r.TUTTI_UFFICI = 1 or p.CODEIN = <codice uffint>).
                //Se CODEIN == 'Tutte le Stazioni Appaltanti', levo il filtro "or p.CODEIN = <codice uffint>"
                Predicate orConditionThird;
                if ("Tutte le Stazioni Appaltanti".equals(uffInt)) {
                    orConditionThird = criteriaBuilder.equal(root.get("tuttiUffici"), 1);
                } else {
                    orConditionThird = criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("tuttiUffici"), 1),
                            criteriaBuilder.equal(wRicercheLeftJoinWRicUffInt.get("id").get("codein"), uffInt)
                    );
                }
                p = criteriaBuilder.and(p, orConditionThird);

                //Il nome o la descrizione arrivano come stringa che mando in like nei criteriaBuilder.
                if (nome != null && !StringUtils.isEmpty(nome)) {

                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
                }

                if (descrizione != null && !StringUtils.isEmpty(descrizione)) {

                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("descrizione"), "%" + descrizione + "%"));
                }

                //Fine condizione WHERE

                //Sort ASC della query in base al nome
                if (sortDirection.equals("asc")) {

                    if (fieldToSort.equals("nome")) {

                        query.orderBy(criteriaBuilder.asc(root.get("nome")));
                    }
                }

                //Sort DESC della query in base ai parametri
                if (sortDirection.equals("desc")) {

                    if (fieldToSort.equals("nome")) {

                        query.orderBy(criteriaBuilder.desc(root.get("nome")));
                    }
                }

                return p;
            }
        };
    }
}
