package it.appaltiecontratti.gestionereportsms.specifications;

import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.domain.WRicParam;
import it.appaltiecontratti.gestionereportsms.domain.WRicerche;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class RicParamSpecification {

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
    public static Specification<WRicParam> getParamsSpecification(
            final Long idRicerca,
            final String sort,
            final String sortDirection){

        logger.info("Invoked getParamsSpecification");

        return new Specification<>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<WRicParam> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate p = criteriaBuilder.conjunction();

                if (idRicerca != null) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("id").get("idRicerca"), idRicerca));
                }

                //Sort ASC della query in base ai parametri
                if (!StringUtils.isEmpty(sortDirection) && sortDirection.equals("asc")) {

                    if (sort.equals("progressivo")) {
                        query.orderBy(criteriaBuilder.asc(root.get("id").get("progressivo")));
                    }
                }

                //Sort DESC della query in base ai parametri
                if (!StringUtils.isEmpty(sortDirection) && sortDirection.equals("desc")) {

                    if (sort.equals("progressivo")) {
                        query.orderBy(criteriaBuilder.desc(root.get("id").get("progressivo")));
                    }
                }

                return p;
            }
        };
    }
}
