package it.maggioli.ssointegrms.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Formatter;


/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class MToken {

    private InputStream caCertificate;
    private String caSubjectEmail;
    private String caSubjectCN;

    private String utente;            // user estratto dal certificato
    private String email;            // email estratta dal certificato
    private boolean esito;            // true se il certificato � stato decodificato correttamente
    private String error;            // codice dell'errore in caso di errori nel certificato
    private Date timestamp;
    private String sha1;

    private boolean expired;        // indica se il certificato caricato � valido o scaduto
    private boolean verified;        // indica se il certificato ha una firma valida
    private PublicKey publicKey;    // chiave pubblica del certificato CA

    private String subjectEmail;
    private String subjectCN;


    public String getError() {
        return error;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isEsito() {
        return esito;
    }

    public void setEsito(boolean esito) {
        this.esito = esito;
    }

    public String getSha1() {
        return sha1;
    }

    /**
     * Crea il client definendo il nome file del certificato "admin"
     * contenente la chiave pubblica per la verifica della firma
     * dei certificati utente
     */
    public MToken(InputStream caCertificate) {
        this.caCertificate = caCertificate;
    }

    /**
     * Carica il certificato "ca-admin.maggioli.it.crt"
     * contenente la chiave pubblica per la validazione delle firme
     * dei certificati utente.
     *
     * @throws Exception
     */
    private PublicKey loadPublicKey() throws Exception {
        PublicKey key = null;
        try {
            byte[] cert = bytesFromStream(this.caCertificate);

            if (this.loadFromCRT(cert, null)) {
                key = this.publicKey;
                this.caSubjectCN = this.subjectCN;
                this.caSubjectEmail = this.subjectEmail;
            }

        } catch (Exception e) {
            throw e;
        }

        return key;
    }

    /**
     * recupera le informazioni del certificato e valida le info contenute.
     *
     * @throws Exception
     */
    private void readMtokenCredentials(byte[] certificate) {
        this.esito = false;
        this.error = null;
        this.utente = null;
        this.email = null;
        this.expired = false;
        this.verified = false;
        this.sha1 = null;

        try {
            // carica il certificato "ca-admin.maggioli.it.crt"
            // contenente la chiave pubblica per la validazione delle firme
            // dei certificati utente
            PublicKey key = this.loadPublicKey();

            // carica il certificato utente
            if (key != null) {
                this.loadFromCRT(certificate, key);

                // verifica se e' stato utilizzato come certificato utente
                // il certificato ca !!!
                if ((StringUtils.isNotEmpty(this.subjectCN) && this.subjectCN.equalsIgnoreCase(this.caSubjectCN)) ||
                        StringUtils.isEmpty(this.subjectEmail)) {
                    this.error = "mtokenerrorcertificate";
                }

                if (this.expired) {
                    this.error = "mtokenerrorexpired";
                }
            }
        } catch (CertificateException e) {
            this.error = "mtokenerrorcertificate";
        } catch (SignatureException | InvalidKeyException | NoSuchProviderException | NoSuchAlgorithmException e) {
            this.error = "mtokenerrorsignature";
        } catch (Exception e) {
            this.error = "mtokenerrorpublikey";
        }

        if (StringUtils.isEmpty(this.error)) {
            if (StringUtils.isNotEmpty(this.utente)) {
                this.esito = true;
            } else {
                this.error = "mtokenerrorcredentials";
            }
        }
    }

    /**
     * carica le informazioni del certificato e valida le info contenute
     *
     * @throws Exception
     */
    public void setMtokenCredentials(String certificate) throws Exception {
        if (certificate != null) {
            certificate = this.fixCertificateStringValue(certificate);
            byte[] cert = certificate.getBytes();
            this.readMtokenCredentials(cert);
        }

    }

    /**
     * carica le informazioni del certificato e valida le info contenute
     *
     * @throws Exception
     */
    public void setMtokenCredentials(byte[] certificate) throws Exception {
        if (certificate != null) {
            this.readMtokenCredentials(certificate);
            this.sha1 = this.evaluateSha1(certificate);
        }
    }

    /**
     * Carica le informazioni del certificato e valida le info contenute
     *
     * @throws Exception
     */
    public void setMtokenCredentials(File certificate) throws Exception {
        byte[] cert = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(certificate);
            cert = bytesFromStream(fis);
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
        }
        if (cert != null) {
            this.readMtokenCredentials(cert);
            this.sha1 = this.evaluateSha1(cert);
        }
    }

    /**
     * Decodifica le informazioni per un certificato X.509.
     *
     * @throws CertificateException
     * @throws SignatureException
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    private boolean loadFromCRT(byte[] stream, PublicKey key)
            throws CertificateException, SignatureException, NoSuchProviderException, InvalidKeyException, NoSuchAlgorithmException {
        boolean loaded = false;

        InputStream ca = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(stream);
            ca = new BufferedInputStream(is);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(ca);
            X509Certificate x509 = (X509Certificate) cert;
            this.publicKey = x509.getPublicKey();

            if (key != null) {
                this.verified = false;
                try {
                    x509.verify(key);
                    this.verified = true;
                } catch (SignatureException | NoSuchProviderException | InvalidKeyException | CertificateException |
                         NoSuchAlgorithmException e) {
                    throw e;
                }

                this.decodeSubject(x509.getSubjectDN());
                this.decodeIssuer(x509.getIssuerDN());
                this.utente = this.subjectCN;
                this.email = this.subjectEmail;
                Date now = new Date();
                this.timestamp = now;
                this.expired = now.before(x509.getNotBefore()) || now.after(x509.getNotAfter()) ||
                        !this.verified;
                this.sha1 = this.evaluateSha1(stream);
            }

            loaded = true;
        } catch (CertificateException e) {
            throw e;
        } finally {
            if (ca != null) {
                try {
                    ca.close();
                } catch (Exception e) {
                }
            }
        }
        return loaded;
    }

    private void decodeSubject(Principal subject) {
        //CN=Super FAKE, O=CA FAKE, ST=Some-State, C=IT
        String[] k = subject.toString().split(",");
        for (int i = 0; i < k.length; i++) {
            String[] v = k[i].split("=");
            v[0] = v[0].trim();
            if (v[0].equalsIgnoreCase("EMAILADDRESS")) this.subjectEmail = v[1];
            if (v[0].equalsIgnoreCase("CN")) this.subjectCN = v[1];
        }
    }

    private void decodeIssuer(Principal issuer) {
        //CN=Super FAKE, O=CA FAKE, ST=Some-State, C=IT
        String[] k = issuer.toString().split(",");
        for (int i = 0; i < k.length; i++) {
            String[] v = k[i].split("=");
            v[0] = v[0].trim();
        }
    }

    /**
     * ...
     */
    private byte[] bytesFromStream(InputStream fis) throws IOException {
        return IOUtils.toByteArray(fis);
    }

    /**
     * Calcola il valore dello SHA-1 di un array di bytes.
     */
    @SuppressWarnings("java:S4790")
    private String evaluateSha1(byte[] stream) {
        String sha = null;
        Formatter fmt = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(stream, 0, stream.length);
            byte[] hash = md.digest();
            fmt = new Formatter();
            for (byte b : hash) {
                fmt.format("%02x", b);
            }
            sha = fmt.toString().toUpperCase();
        } catch (Exception e) {
            sha = null;
        } finally {
            if (fmt != null)
                fmt.close();
        }
        return sha;
    }

    /**
     * Corregge la stringa di un certificato filtrato da una textarea.
     */
    private String fixCertificateStringValue(String value) {
        value = value.replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replace("\r", "")
                .replace("\n", "");
        String s = "";
        for (int i = 0; i < value.length(); i = i + 64) {
            s = s + value.substring(i, Math.min(i + 64, value.length())) + "\n";
        }
        return "-----BEGIN CERTIFICATE-----\n" + s + "-----END CERTIFICATE-----\n";
    }
}
