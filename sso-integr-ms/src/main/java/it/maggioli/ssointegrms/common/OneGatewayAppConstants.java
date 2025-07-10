package it.maggioli.ssointegrms.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.maggioli.ssointegrms.model.ApplicationInfo;

/**
 * Classe di costanti applicative
 * 
 * @author Cristiano Perin
 *
 */
public class OneGatewayAppConstants {

	// SP
	public static final Map<String, ApplicationInfo> SERVICE_PROVIDERS = new HashMap<>() {
		private static final long serialVersionUID = -2491899749817563541L;

		{
			// SCPSA Collaudo
			put("SCPSACollaudo",
					new ApplicationInfo("/SCPSACollaudo/SSOGatewayLoginResponse.do", "SCPSACollaudo", "SCPSA_coll",
							"keys/sp/SCPSACollaudo/SCPSA_coll_private_unencrypted.der", true,
							"/SCPSACollaudo/InitLogin.do", null, true, true, false, false));
			// SCPSA Produzione
			put("SCPSAProduzione",
					new ApplicationInfo("/SCPSA/SSOGatewayLoginResponse.do", "SCPSAProduzione", "SCPSA_prod",
							"keys/sp/SCPSAProduzione/SCPSA_prod_private_unencrypted.der", true, "/SCPSA/InitLogin.do",
							null, true, true, false, false));
			// SCPProg Collaudo
			put("SCPProgCollaudo",
					new ApplicationInfo("/scphomeCollaudo-fe/login.html", "SCPProgCollaudo", "SCPProg_coll",
							"keys/sp/SCPProgCollaudo/SCPProg_coll_private_unencrypted.der", true,
							"/scphomeCollaudo-fe/#/page/home-page?internalLoginRedirectClientId=W9-PT", "https://www.serviziocontrattipubblici.it/SCPSACollaudo/registrazione-utente-scp.jsp?modo=NUOVO", true,
							true, false, false));
			// SCPBandi Collaudo
			put("SCPBandiCollaudo",
					new ApplicationInfo("/scphomeCollaudo-fe/login.html", "SCPProgCollaudo", "SCPProg_coll",
							"keys/sp/SCPProgCollaudo/SCPProg_coll_private_unencrypted.der", true,
							"/scphomeCollaudo-fe/#/page/home-page?internalLoginRedirectClientId=W9-AEC", "https://www.serviziocontrattipubblici.it/SCPSACollaudo/registrazione-utente-scp.jsp?modo=NUOVO", true,
							true, false, false));
			// SCPProg Produzione
			put("SCPProgProduzione",
					new ApplicationInfo("/scphome-fe/login.html", "SCPProgProduzione", "SCPProg_prod",
							"keys/sp/SCPProgProduzione/SCPProg_prod_private_unencrypted.der", true,
							"/scphome-fe/#/page/home-page?internalLoginRedirectClientId=W9-PT", "https://www.serviziocontrattipubblici.it/SCPSA/registrazione-utente-scp.jsp?modo=NUOVO", true, true, false,
							false));
			// SCPBandi Produzione
			put("SCPBandiProduzione",
					new ApplicationInfo("/scphome-fe/login.html", "SCPProgProduzione", "SCPProg_prod",
							"keys/sp/SCPProgProduzione/SCPProg_prod_private_unencrypted.der", true,
							"/scphome-fe/#/page/home-page?internalLoginRedirectClientId=W9-AEC", "https://www.serviziocontrattipubblici.it/SCPSA/registrazione-utente-scp.jsp?modo=NUOVO", true, true, false,
							false));
			// SCPProg Collaudo OR
			put("SCPProgCollaudoOR",
					new ApplicationInfo("/scphomeCollaudo-fe-or/login.html", "SCPProgCollaudo", "SCPProg_coll",
							"keys/sp/SCPProgCollaudo/SCPProg_coll_private_unencrypted.der", true,
							"/scphomeCollaudo-fe-or/#/page/home-page?internalLoginRedirectClientId=W9-PT", null, false,
							false, false, false));
			put("SCPBAndiCollaudoOR",
					new ApplicationInfo("/scphomeCollaudo-fe-or/login.html", "SCPProgCollaudo", "SCPProg_coll",
							"keys/sp/SCPProgCollaudo/SCPProg_coll_private_unencrypted.der", true,
							"/scphomeCollaudo-fe-or/#/page/home-page?internalLoginRedirectClientId=W9-AEC", null, false,
							false, false, false));
			// SCPProg Produzione OR
			put("SCPProgProduzioneOR",
					new ApplicationInfo("/scphome-fe-or/login.html", "SCPProgProduzioneOR", "SCPProg_prod_or",
							"keys/sp/SCPProgProduzione/SCPProg_prod_private_unencrypted.der", true,
							"/scphome-fe-or/#/page/home-page?internalLoginRedirectClientId=W9-PT", null, false, false,
							false, false));
			put("SCPBAndiProduzioneOR",
					new ApplicationInfo("/scphome-fe-or/login.html", "SCPProgProduzioneOR", "SCPProg_prod_or",
							"keys/sp/SCPProgProduzione/SCPProg_prod_private_unencrypted.der", true,
							"/scphome-fe-or/#/page/home-page?internalLoginRedirectClientId=W9-AEC", null, false, false,
							false, false));

			// SCPEsecuzione Collaudo
			put("SCPEsecuzione_coll",
					new ApplicationInfo("/scpesecuzionecontratti-fe-collaudo/login.html", "SCPEsecuzione_coll", "SCPEsecuzione_coll",
							"keys/sp/SCPEsecuzione_coll/SCPEsecuzione_coll_private_unencrypted.der", true,
							"/scpesecuzionecontratti-fe-collaudo/#/page/internal-login-page", null, true,
							true, false, false));

			// SCPEsecuzione Produzione
			put("SCPEsecuzione_prod",
					new ApplicationInfo("/scpesecuzione-fe/login.html", "SCPEsecuzione_prod", "SCPEsecuzione_prod",
							"keys/sp/SCPEsecuzione_prod/SCPEsecuzione_prod_private_unencrypted.der", true,
							"/scpesecuzionecontratti-fe/#/page/internal-login-page", null, true,
							true, false, false));

			// Piattaforma E-Procurement
			// eproc_bko_coll
			put("eproc_bko_coll",
					new ApplicationInfo("/Appalti/SSOGatewayLoginResponse.do", "eproc_bko_coll", "eproc_bko_coll",
							"keys/sp/eproc_bko_coll/eproc_bko_coll_private_unencrypted.der", true,
							"/Appalti/InitLoginForm.do", null, true, true, true, false));
			// eproc_bko_prod
			put("eproc_bko_prod",
					new ApplicationInfo("/Appalti/SSOGatewayLoginResponse.do", "eproc_bko_prod", "eproc_bko_prod",
							"keys/sp/eproc_bko_prod/eproc_bko_prod_private_unencrypted.der", true,
							"/Appalti/InitLoginForm.do",
							"https://portaleappalti.mit.gov.it/Appalti/registrazione-account.jsp?modo=NUOVO", true,
							true, true, false));
			// eproc_port_coll
			put("eproc_port_coll",
					new ApplicationInfo("/PortaleAppalti/do/SSO/GatewayLoginResponse.action", "eproc_port_coll",
							"eproc_port_coll", "keys/sp/eproc_port_coll/eproc_port_coll_private_unencrypted.der", false,
							null, null, true, true, true, false));
			// eproc_port_prod
			put("eproc_port_prod",
					new ApplicationInfo("/PortaleAppalti/do/SSO/GatewayLoginResponse.action", "eproc_port_prod",
							"eproc_port_prod", "keys/sp/eproc_port_prod/eproc_port_prod_private_unencrypted.der", false,
							null, null, true, true, true, false));

			// eproc_feu_coll
			put("eproc_feu_coll",
					new ApplicationInfo("/FEU/SSOGatewayLoginResponse.do", "eproc_feu_coll", "eproc_feu_coll",
							"keys/sp/eproc_feu_coll/eproc_feu_coll_private_unencrypted.der", true, "/FEU/InitLoginForm.do",
							null, true, true, true, false));
			// eproc_feu_prod
			put("eproc_feu_prod",
					new ApplicationInfo("/FEU/SSOGatewayLoginResponse.do", "eproc_feu_prod", "eproc_feu_prod",
							"keys/sp/eproc_feu_prod/eproc_feu_prod_private_unencrypted.der", true, "/FEU/InitLoginForm.do",
							null, true, true, true, false));

			// eproc_lfs_coll
			put("eproc_lfs_coll",
					new ApplicationInfo("/LFS/SSOGatewayLoginResponse.do", "eproc_lfs_coll", "eproc_lfs_coll",
							"keys/sp/eproc_lfs_coll/eproc_lfs_coll_private_unencrypted.der", true, "/LFS/InitLoginForm.do",
							null, true, true, true, false));
			// eproc_lfs_prod
			put("eproc_lfs_prod",
					new ApplicationInfo("/LFS/SSOGatewayLoginResponse.do", "eproc_lfs_prod", "eproc_lfs_prod",
							"keys/sp/eproc_lfs_prod/eproc_lfs_prod_private_unencrypted.der", true, "/LFS/InitLoginForm.do",
							null, true, true, true, false));
		}
	};

	public static ApplicationInfo getApplicationInfoByMimsClientId(final String mimsClientId) {
		if (StringUtils.isBlank(mimsClientId))
			return null;

		ApplicationInfo applicationInfo = null;

		for (Map.Entry<String, ApplicationInfo> entry : SERVICE_PROVIDERS.entrySet())
			if (mimsClientId.equals(entry.getValue().getMimsClientId()))
				applicationInfo = entry.getValue();

		return applicationInfo;
	}

	// Authentication Types
	public static final String AUTH_TYPE_SPID = "AUTH_TYPE_SPID";
	public static final String AUTH_TYPE_CIE = "AUTH_TYPE_CIE";
	public static final String AUTH_TYPE_CNS = "AUTH_TYPE_CNS";

	// Purpose
	public static final String ID_PERSONA_GIURIDICA = "LP";
	public static final String ID_PERSONA_FISICA = "P";

	// IDP Authentication Types
	public static final String IDP_AUTH_TYPE_SPID = "SPID";
	public static final String IDP_AUTH_TYPE_CIE = "CIE";
	public static final String IDP_AUTH_TYPE_CNS = "CNS";
	public static final String IDP_AUTH_TYPE_CUSTOM = "CUSTOM";
}
