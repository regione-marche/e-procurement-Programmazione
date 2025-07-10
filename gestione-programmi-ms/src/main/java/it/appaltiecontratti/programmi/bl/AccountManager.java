package it.appaltiecontratti.programmi.bl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.programmi.entity.LoginResult;
import it.appaltiecontratti.programmi.entity.UsrSysEntry;
import it.appaltiecontratti.programmi.mapper.AccountMapper;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

/**
 * Manager per la gestione della business logic.
 * 
 * @author Stefano.Sabbadin
 */
@Component(value = "accountManager")
public class AccountManager {

	private static final String CONFIG_CODICE_APP = "W9";
	private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

	/** Logger di classe. */
	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	/**
	 * Dao MyBatis con le primitive di estrazione dei dati.
	 */
	@Autowired
	private AccountMapper accountMapper;

	/**
	 * @param accountMapper
	 *            accountMapper da settare internamente alla classe.
	 */
	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	public LoginResult getLoginPubblica(String username, String password) {

		LoginResult risultato = new LoginResult();

		try {

			// recupero dati account
			String pwd = this.accountMapper.getAccountPwd(username);
			if (pwd != null) {
				// verifico se la password � corretta
				if (!isCorrectPassword(password, pwd)) {
					risultato.setEsito(false);
					risultato.setErrorData(LoginResult.ERROR_NOT_FOUND);
				} else {
					risultato.setEsito(true);
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(LoginResult.ERROR_NOT_FOUND);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante il getLoginPubblica username"
							+ username + ", password " + password, t);
			risultato.setEsito(false);
			risultato.setErrorData(LoginResult.ERROR_UNEXPECTED + ": "
					+ t.getMessage());
		}

		return risultato;
	}

	public LoginResult getLoginApplicazione(String clientId, String key) {
		LoginResult risultato = new LoginResult();

		try {

			String clientKey = this.accountMapper.getClientKey(clientId);
			if (clientKey != null) {

				if (!isCorrectClientKey(clientKey, key)) {
					risultato.setEsito(false);
					risultato
							.setErrorData(LoginResult.ERROR_CLIENTID_CLIENTKEY_EMPTY);
				} else {
					risultato.setEsito(true);
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(LoginResult.ERROR_NOT_FOUND);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante il getLoginApplicazione username"
							+ clientId + ", password " + key, t);
			risultato.setEsito(false);
			risultato.setErrorData(LoginResult.ERROR_UNEXPECTED + ": "
					+ t.getMessage());
		}

		return risultato;
	}

	public byte[] getJWTKey() throws CriptazioneException {

		String criptedKey = this.accountMapper.getCipherKey(CONFIG_CODICE_APP,
				CONFIG_CHIAVE_APP);
		try {
			ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
					FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, 	 
					criptedKey.getBytes(),
            		ICriptazioneByte.FORMATO_DATO_CIFRATO);
			
			return decrypt.getDatoNonCifrato();
		} catch (CriptazioneException e) {
			logger.error("Errore in fase di decrypt della chiave hash per generazione token",e);
			throw e;
		}
	}

	public static boolean isCorrectClientKey(String criptedKey, String key)
			throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");

		md.update(key.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		if (sb.toString().equals(criptedKey))
			return true;
		else
			return false;
	}

	public UsrSysEntry getUsrSysEntry(String username) {
		return this.accountMapper.getUserSysInfo(username.toUpperCase());
	}

	
	public Boolean isCorrectPassword(String passwordInChiaro, String passwordCriptata) {
		if (passwordInChiaro != null) {
			String passwordDecifrata = "";
			try {
				ICriptazioneByte decriptatorePsw = FactoryCriptazioneByte.getInstance("LEG",
						passwordCriptata.getBytes(),
		            ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordDecifrata = new String(decriptatorePsw.getDatoNonCifrato());
			} catch(Exception ex) {
				;
			}
			return passwordDecifrata.equals(passwordInChiaro);
		} else {
			return false;
		}
	}
}
