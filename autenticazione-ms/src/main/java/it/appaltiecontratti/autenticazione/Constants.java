package it.appaltiecontratti.autenticazione;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String SSO_KEY_CODICE_FISCALE = "sso.attribute.fiscalCode";
	public static final String SSO_KEY_NOME = "sso.attribute.firstName";
	public static final String SSO_KEY_COGNOME = "sso.attribute.lastName";
	public static final String SSO_KEY_NOME_COMPLETO = "sso.attribute.description";
	public static final String SSO_KEY_LOGIN = "sso.attribute.login";
	public static final String SSO_KEY_EMAIL = "sso.attribute.mail";
	public static final String SSO_SPECIFIC_KEY_CODICE_FISCALE = "auth.sso.webserver.mapping.fiscalCode.{0}";
	public static final String SSO_SPECIFIC_KEY_NOME = "auth.sso.webserver.mapping.firstName.{0}";
	public static final String SSO_SPECIFIC_KEY_COGNOME = "auth.sso.webserver.mapping.lastName.{0}";
	public static final String SSO_SPECIFIC_KEY_NOME_COMPLETO = "auth.sso.webserver.mapping.description.{0}";
	public static final String SSO_SPECIFIC_KEY_LOGIN = "auth.sso.webserver.mapping.login.{0}";
	public static final String SSO_SPECIFIC_KEY_EMAIL = "auth.sso.webserver.mapping.mail.{0}";
	public static final String SSO_SPECIFIC_KEY_LOA = "auth.sso.webserver.mapping.loa.{0}";
	public static final String SSO_SPECIFIC_KEY_LOA_ENCODING = "auth.sso.webserver.mapping.loaEncoding.{0}";
	public static final String SSO_SPECIFIC_KEY_IDP_TYPE = "auth.sso.webserver.mapping.standardProvider.{0}";
	public static final String SSO_SPECIFIC_KEY_IDP_TYPE_ENCODING = "auth.sso.webserver.mapping.standardProviderEncoding.{0}";
	public static final String DEFAULT_LOA = "2";
	public static final String DEFAULT_IDP_TYPE = "CUSTOM";

	public static final String ADMIN_ACCOUNT = "admin";
	
	public static final String W_ATT_OPZIONI_PRODOTTO_CHIAVE = "it.eldasoft.opzioni";
	public static final String ASSISTENZA_MODO = "assistenza.modo";
	public static final String PROP_CONFIGURAZIONE_MAIL_STANDARD = "STD";
	
	public static final String LOGIN_MODE_0_CF = "preferred_username";
	public static final String LOGIN_MODE_OTHERS_CF = "USER_CF";
	
	public static final String OU_GESTIONE_UTENTI_COMPLETA = "ou11";
	public static final String OU_GESTIONE_UTENTI_OU12 = "ou12";
	public static final String OU_ABILITA_TUTTE_SA = "ou238";
	public static final String OU_AMMINISTRATORE = "ou89";

	public static final String OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI = "ou214";

	// Controlli GDPR
	// Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
	@SuppressWarnings("java:S2068")
	public static final String OU_UTENTE_PASSWORD_SICURA = "ou39";
	public static final String OU_SEPARATORE_REGEX = "\\|";
	public static final String OP_SEPARATORE_REGEX = "\\|";
	
	public static final String W_GENCHIAVI_W_LOGEVENTI = "W_LOGEVENTI";

	public static final String W_CONFIG_COD_APP_GENEWEB = "W_";

	public static final String W_CONFIG_COD_APP_GENE = "G_";

	public static final String W_CONFIG_SSO_DEFAULT_PASSWORD = "registrazione.sso.defaultPassword";

	public static final String W_CONFIG_URL_MANUALI = "it.eldasoft.manuale";

	public static final String W_CONFIG_REGISTRAZIONE_ATTIVA = "it.eldasoft.registrazione.attivaForm";

	public static final Map<String, String> FILE_TYPES = new HashMap<>() {{
		put("doc", "application/msword");
		put("dot", "application/msword");
		put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
		put("docm", "application/vnd.ms-word.document.macroEnabled.12");
		put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
		put("xls", "application/vnd.ms-excel");
		put("xlt", "application/vnd.ms-excel");
		put("xla", "application/vnd.ms-excel");
		put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
		put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
		put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
		put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
		put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
		put("ppt", "application/vnd.ms-powerpoint");
		put("pot", "application/vnd.ms-powerpoint");
		put("pps", "application/vnd.ms-powerpoint");
		put("ppa", "application/vnd.ms-powerpoint");
		put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
		put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
		put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
		put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		put("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
		put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
		put("mdb", "application/vnd.ms-access");
		put("7z", "application/x-7z-compressed");
		put("xml", "application/xml");
		put("pdf", "application/pdf");
		put("p7m", "application/pkcs7-mime");
		put("zip", "application/zip");
		put("odt", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	}};
}
