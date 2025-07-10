package it.appaltiecontratti.gestionereportsms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.WConfig;
import it.appaltiecontratti.gestionereportsms.exceptions.GenericReportOperationException;
import it.appaltiecontratti.gestionereportsms.repositories.WConfigRepository;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${application.codiceProdotto}")
    private String COD_APP;

    private String COD_GENEWEB = "W_";

    @Autowired
    WConfigRepository wConfigRepository;

    public String generateToken(String reportName, String cypherKey) throws Exception {

        logger.info("START method JwtTokenService::generateToken.");

        byte[] jwtKey = new byte[0];

        ICriptazioneByte decrypt = null;
        WConfig config = wConfigRepository.getConfigObj(COD_APP, AppConstants.W_CONFIG_GENRIC_JWT_KEY_CHIAVE);

        if(config == null) {
            logger.error("Passphrase di cifratura non impostata a DB.");

            return null;
        }
        if(config.getValore() == null) {
            logger.error("Passphrase non valorizzata a DB");
        }

        try {

            //Cifro la chiave che l'utente ha inserito.

            if(StringUtils.equals(config.getCriptato(), "1")){
                if(config.getValore() != null){
                    decrypt = FactoryCriptazioneByte.getInstance(
                            FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, cypherKey.getBytes(),
                            ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
                }

                if(decrypt != null) {
                    jwtKey = decrypt.getDatoCifrato();
                }
            }
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            return null;
        } catch (GenericReportOperationException e) {
            logger.error(e.getMessage());
            return null;
        }

        if(!Arrays.equals(jwtKey, config.getValore().getBytes())){

            logger.error("Chiave inserita dall'utente diversa da quella salvata in DB.");
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORT_NOT_AUTHORIZED_CRYPT_KEY);

            throw ex;
        }

        //Creo l'algoritmo per firmare il token JWT
        Algorithm algorithm = Algorithm.HMAC256(cypherKey);

        logger.info("END method JwtTokenService::generateToken.");

        //Creo il token JWT e lo firmo con l'algoritmo appena creato.
        return JWT.create()
                .withIssuer("Report")
                .withSubject("Report Name")
                .withClaim("codReportWs", reportName)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) //Scade in 1 ora.
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }
}
