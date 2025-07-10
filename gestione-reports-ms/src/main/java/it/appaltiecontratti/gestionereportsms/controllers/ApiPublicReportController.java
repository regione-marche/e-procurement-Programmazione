package it.appaltiecontratti.gestionereportsms.controllers;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.WConfig;
import it.appaltiecontratti.gestionereportsms.dto.ParametriReportWS;
import it.appaltiecontratti.gestionereportsms.dto.ResponseJson;
import it.appaltiecontratti.gestionereportsms.exceptions.GenericReportOperationException;
import it.appaltiecontratti.gestionereportsms.managers.RicercheManager;
import it.appaltiecontratti.gestionereportsms.repositories.WConfigRepository;
import it.appaltiecontratti.gestionereportsms.service.JwtTokenService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/public/gestioneReports")
public class ApiPublicReportController extends AbstractBaseController{

    /**
     * Logger di classe
     * */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Repositories di ogni Entity.
     * */
    @Autowired
    private RicercheManager rManager;

    @Autowired
    public JwtTokenService jwtTokenService;

    @Autowired
    WConfigRepository wConfigRepository;

    /**
     * Variabili d'ambiente. Vedere application.properties
     * */
    @Value("${application.codiceProdotto}")
    private String COD_APP;

    private String COD_GENEWEB = "W_";

    /**
     * Endpoint di classe
     * */

    @Operation(
            summary = "Genera un nuovo token JWT ritornando il risultato in formato JSON.",
            description = "Permette di generare un nuovo token JWT. È necessario conoscere il codice del report che si vuole eseguire e la chiave con cui cifrare il token.",
            tags = {"Report"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token generato correttamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token non valido o non autorizzato",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Errore interno del server",
                    content = @Content
            )
    })
    @PutMapping(path = "/generaToken", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJson> generaToken(
            @Parameter(description = "Chiave di cifratura per il token")
            @RequestParam String cypherKey,
            @Parameter(description = "Codice del servizio per esposizione report.")
            @RequestParam String codReportWS
    ) {
        try {
            //Genero il token
            String token = jwtTokenService.generateToken(codReportWS, cypherKey);

            return ResponseEntity.ok(new ResponseJson(token));

        } catch (Exception e) {

            return ResponseEntity.status(500).body(new ResponseJson("Errore durante la generazione del token."));
        }
    }

    @Operation(
            summary = "Metodo che ritorna il risultato di un report.",
            description = "Estrazione dati di un report mediante autenticazione con token JWT.",
            tags = {"Report"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Report eseguito correttamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token non valido o non autorizzato",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Report non trovato. Controllare che il codice inserito nel token sia corretto.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Errore interno del server",
                    content = @Content
            )
    })
    @PutMapping(value = "/estraiDati", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> estraiDatiReportWS(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Formato per inserimento token: Bearer <jwt_token>",
                    name = "Authentication_Token",
                    required = true,
                    schema = @Schema(type = "string", format = "Bearer <jwt_Token>")
            )
            @RequestHeader("Authentication_Token") String authorizationHeader,
            @Parameter(description = "Inserire i parametri richiesti per il report.", required = true)
            @RequestBody ParametriReportWS parametriReportWS
    ) {

        logger.info("START method ApiPublicReportController::estraiDatiReportWS");

        ResponseEntity<String> response = ResponseEntity.ok("");
        byte[] jwtKey = new byte[0];
        WConfig config = wConfigRepository.getConfigObj(COD_APP, AppConstants.W_CONFIG_GENRIC_JWT_KEY_CHIAVE);
        if(config == null) {
            logger.error("Passphrase di cifratura non valorizzata a DB");
            return null;
        }

        try {

            String ipEvento = resolveRemoteIpAddress(request);
            String authToken = authorizationHeader.replace("Bearer ", "");

            ICriptazioneByte decrypt = null;
            try {
                //Decifro la chiave presente a DB per verificare il JWT token nel caso in cui criptato = 1.

                if(StringUtils.equals(config.getCriptato(), "1")){
                    if(config.getValore() != null){
                        decrypt = FactoryCriptazioneByte.getInstance(
                                FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, config.getValore().getBytes(),
                                ICriptazioneByte.FORMATO_DATO_CIFRATO);
                    }

                    if(decrypt != null) {
                        jwtKey = decrypt.getDatoNonCifrato();
                    }
                }
                else {
                    jwtKey = config.getValore().getBytes();
                }
            } catch (CriptazioneException e) {
                logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
                return null;
            } catch (GenericReportOperationException e) {
                logger.error(e.getMessage());
                return null;
            }

            Algorithm algorithm = Algorithm.HMAC256(jwtKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Report")
                    .build();

            DecodedJWT jwt;
            try {

                 jwt = verifier.verify(authToken);

            } catch (JWTVerificationException e){

                logger.error("Token JWT non valido o non verificabile: [{}]", e.getMessage());
                throw e;
            }

            //Estrazione claim codReportWs
            String codReportWs = jwt.getClaim("codReportWs").asString();
            logger.info("Claim del token JWT: [{}]", codReportWs);

            //Procedo all'esecuzione del report. Per poterlo eseguire basta richiamare la funzione preposta all'esecuzione ovvero la executeReportWithParams, ricercando il report per nome.
            response = rManager.executeReportWSParams(codReportWs, ipEvento, parametriReportWS);

        } catch (Exception e) {
            logger.error("Errore generico durante l'esecuzione del metodo estraiDati: [{}]", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        logger.info("END method ApiPublicReportController::estraiDatiReportWS");

        if(response.getStatusCode().is2xxSuccessful()){
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        else if(response.getStatusCode().is4xxClientError()){
            return new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);
        }
        else if(response.getStatusCode().is5xxServerError()) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return new ResponseEntity<>(response.getBody(), HttpStatus.NOT_FOUND);
        }

    }
}
