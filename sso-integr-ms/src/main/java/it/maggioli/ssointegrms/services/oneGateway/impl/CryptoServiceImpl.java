package it.maggioli.ssointegrms.services.oneGateway.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.exceptions.oneGateway.DecryptException;
import it.maggioli.ssointegrms.exceptions.oneGateway.EncryptException;
import it.maggioli.ssointegrms.model.AbstractJwtToken;
import it.maggioli.ssointegrms.model.ResponseSuccess;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Servizio di utilita' per la crittografia, la creazione e la decodifica dei
 * token per i tre sistemi di autenticazione
 *
 * @author Cristiano Perin
 */
@Service("cryptoService")
public class CryptoServiceImpl extends BaseService implements CryptoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoServiceImpl.class);

    private static final JWEAlgorithm ALG = JWEAlgorithm.RSA_OAEP_256;
    private static final EncryptionMethod ENC = EncryptionMethod.A128CBC_HS256;


    @Value("${crypto.idpPublicKeyLocation}")
    private String idpPublicKeyLocation;

    @Value("${crypto.tokenExpireTimeSeconds}")
    private Long tokenExpireTimeSeconds;

    @Value("${crypto.spSymmetricKey}")
    private String spSymmetricKey;

    @Value("${crypto.spSignatureAlgorithm}")
    private String spSignatureAlgorithm;

    @Value("${crypto.spSymmetricKeyEncrypted}")
    private Boolean spSymmetricKeyEncrypted;

    @Value("${crypto.spSymmetricKeyBase64}")
    private Boolean spSymmetricKeyBase64;

    @Override
    public <T extends AbstractJwtToken> String encryptIdpJwtToken(final String clientId, final T idpJwtToken)
            throws EncryptException {

        LOGGER.info("Execution start CryptoServiceImpl::encryptIdpJwtToken()");

        if (idpJwtToken == null)
            throw new EncryptException("Errore IllegalArgumentException idpJwtToken null",
                    new IllegalArgumentException());

        try {

            // converto l'oggetto di richiesta in una mappa
            Map<String, Object> map = OBJECT_MAPPER.convertValue(idpJwtToken,
                    new TypeReference<Map<String, Object>>() {
                    });

            LOGGER.info("IDP Token content {}", map);

            // creo il signer con la chiave privata
            JWSSigner signer = new RSASSASigner(parsePrivateKey(clientId));
            // creo l'oggetto da firmare
            JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), new Payload(map));

            jwsObject.sign(signer);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(ENC.cekBitLength());
            SecretKey cek = keyGenerator.generateKey();
            JWEObject jwe = new JWEObject(new JWEHeader(ALG, ENC), new Payload(jwsObject));
            jwe.encrypt(new RSAEncrypter((RSAPublicKey) parsePublicKey(), cek));

            return jwe.serialize();

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Errore NoSuchAlgorithmException", e);
            throw new EncryptException("Errore NoSuchAlgorithmException", e);
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Errore InvalidKeySpecException", e);
            throw new EncryptException("Errore InvalidKeySpecException", e);
        } catch (IOException e) {
            LOGGER.error("Errore IOException", e);
            throw new EncryptException("Errore IOException", e);
        } catch (JOSEException e) {
            LOGGER.error("Errore JOSEException", e);
            throw new EncryptException("Errore JOSEException", e);
        }
    }

    @Override
    public Map<String, Object> decryptAndVerifyIdpJwtToken(final String mimsClientId, final String jweString)
            throws DecryptException {

        LOGGER.info("Execution start CryptoServiceImpl::decryptAndVerifyIdpJwtToken()");

        try {

            String localClientId = super.getLocalClientIdByMimsClientId(mimsClientId);

            JWEObject jweObject = JWEObject.parse(jweString);
            jweObject.decrypt(new RSADecrypter(parsePrivateKey(localClientId)));
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            if (!signedJWT.verify(new RSASSAVerifier((RSAPublicKey) parsePublicKey())))
                throw new RuntimeException("Firma non valida");
            return new TreeMap<>(signedJWT.getPayload().toJSONObject());

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Errore NoSuchAlgorithmException", e);
            throw new DecryptException("Errore NoSuchAlgorithmException", e);
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Errore InvalidKeySpecException", e);
            throw new DecryptException("Errore InvalidKeySpecException", e);
        } catch (ParseException e) {
            LOGGER.error("Errore ParseException", e);
            throw new DecryptException("Errore ParseException", e);
        } catch (JOSEException e) {
            LOGGER.error("Errore JOSEException", e);
            throw new DecryptException("Errore JOSEException", e);
        } catch (IOException e) {
            LOGGER.error("Errore IOException", e);
            throw new DecryptException("Errore IOException", e);
        }
    }

    @Override
    public String encryptSpJwtToken(final ResponseSuccess responseSuccess) throws EncryptException {

        LOGGER.info("Execution start CryptoServiceImpl::encryptSpJwtToken()");

        if (responseSuccess == null)
            throw new EncryptException("Errore IllegalArgumentException responseSuccess null",
                    new IllegalArgumentException());

        long nowSeconds = Instant.now().getEpochSecond();
        Date nowDate = new Date(nowSeconds * 1000);
        String codiceFiscale = StringUtils.isNotBlank(responseSuccess.getFiscalNumber())
                ? responseSuccess.getFiscalNumber().replace("TINIT-", "").trim()
                : null;

        JwtBuilder builder = Jwts.builder() //
                .issuedAt(nowDate) //
                .subject(codiceFiscale) //
                .issuer("sso-integr-ms") //
                .expiration(getExpDate(nowSeconds, tokenExpireTimeSeconds)) //
                .claim("codiceFiscale", codiceFiscale) //
                .claim("cognome", responseSuccess.getFamilyName()) //
                .claim("nome", responseSuccess.getName()) //
                .claim("email", responseSuccess.getEmail()) //
                .claim("userIdpType", getIdpType(responseSuccess.get_type_())); //


        // Modifica CNS a CUSTOM e LoA4 (SPINAPP-10)
        if (OneGatewayAppConstants.IDP_AUTH_TYPE_CNS.equals(responseSuccess.get_type_())) {
            builder = builder.claim("userLoa", "4");
        } else {
            builder = builder.claim("userLoa", getLoA(responseSuccess.get_spid_level_()));
        }


        String jwtKey = null;
        try {
            jwtKey = getSpSymmetricKey();
        } catch (CriptazioneException e) {
            LOGGER.error("Errore durante la decrittazione della chiave simmetrica", e);
            throw new EncryptException(e);
        }

        if (spSymmetricKeyBase64) {
            byte[] jwtKeyByte = Base64.decodeBase64(jwtKey);
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKeyByte);
            builder.signWith(secretKey, getSignatureAlgorithm(spSignatureAlgorithm));
        } else {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
            builder.signWith(secretKey, getSignatureAlgorithm(spSignatureAlgorithm));
        }

        return builder.compact();
    }

    private PrivateKey parsePrivateKey(final String clientId)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        ClassPathResource cpr = super.loadResourceFromClasspath(
                OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId).getPrivateKeyLocation());
        InputStream is = cpr.getInputStream();

        byte[] keyBytes = IOUtils.toByteArray(is);

        is.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey parsePublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        ClassPathResource cpr = super.loadResourceFromClasspath(idpPublicKeyLocation);
        InputStream is = cpr.getInputStream();

        String base64Encoded = IOUtils.toString(is, StandardCharsets.UTF_8);

        is.close();

        base64Encoded = cleanPem(base64Encoded);
        byte[] encoded = Base64.decodeBase64(base64Encoded);
        X509EncodedKeySpec x509publicKey = new X509EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(x509publicKey);
        return key;
    }

    private String cleanPem(String key) {
        key = key.trim();
        if (!key.startsWith("-----BEGIN "))
            return key;
        key = key.replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\n", "").replaceAll("\r", "");
        return key;
    }

    private Date getExpDate(final long nowSecondsTimestamp, final long tokenExpireTimeSeconds) {
        return new Date((nowSecondsTimestamp + tokenExpireTimeSeconds) * 1000);
    }

    private String getSpSymmetricKey() throws CriptazioneException {
        return spSymmetricKeyEncrypted ? getValoreNonCifrato(spSymmetricKey) : spSymmetricKey;
    }

    /**
     * Metodo per ritornare l'idp type formattato alle tipologie conosciute e gestite
     *
     * @param idpType Idp Type ritornato da OneGateway
     * @return Idp Type formattato
     */
    private String getIdpType(final String idpType) {
        if (StringUtils.isBlank(idpType))
            return null;

        switch (idpType) {
            case OneGatewayAppConstants.IDP_AUTH_TYPE_SPID:
            case OneGatewayAppConstants.IDP_AUTH_TYPE_CIE:
                return idpType;
            case OneGatewayAppConstants.IDP_AUTH_TYPE_CNS:
                // Modifica CNS a CUSTOM e LoA4 (SPINAPP-10)
                return OneGatewayAppConstants.IDP_AUTH_TYPE_CUSTOM;
            default:
                return null;
        }
    }

    /**
     * Metodo per ritornare il LoA alle tipologie conosciute e gestite
     *
     * @param idpLoA LoA ritornato da OneGateway
     * @return LoA formattato
     */
    private String getLoA(final String idpLoA) {
        if (StringUtils.isBlank(idpLoA))
            return null;

        switch (idpLoA) {
            case "L1":
                return "2";
            case "L2":
                return "3";
            case "L3":
                return "4";
            default:
                return null;
        }
    }
}
