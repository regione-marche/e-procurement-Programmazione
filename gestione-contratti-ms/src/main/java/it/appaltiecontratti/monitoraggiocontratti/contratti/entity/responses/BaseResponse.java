package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";

	public static final String ERROR_NOT_FOUND = "not-found";
	
	public static final String ERROR_FUNCTION_NOT_AVAILABLE = "ERROR-FUNCTION-NOT-AVAILABLE";

	public static final String ERROR_PERMISSION = "ERROR-PERMISSION";
	
	public static final String ERROR_PERMISSION_PCP = "ERROR-PERMISSION-PCP";
	
	public static final String ERROR_INVALID_VALUE = "ERROR-INVALID-VALUE";
	
	public static final String ERROR_PERMISSION_LOA = "ERROR-PERMISSION-LOA";
	
	public static final String ERROR_PERMISSION_SA = "ERROR-PERMISSION-SA";
	
	public static final String ERROR_PERMISSION_SA_PCP = "ERROR-PERMISSION-SA-PCP";
	
	public static final String ERROR_STATO_APPALTO_PCP = "ERROR-STATO-APPALTO-PCP";
	
	public static final String ERROR_COD_AUSA_NOT_SET = "ERROR-COD-AUSA-NOT-SET";
	
	public static final String ERROR_ALREADY_EXIST = "ERROR-ALREADY-EXIST";
	
	public static final String ERROR_NO_CIG_PCP = "ERROR-NO-CIG-PCP";
	
	//public static final String ERROR_ALREADY_EXIST = "ERROR-ALREADY-EXIST";
	
	public static final String ERROR_PERMISSION_IDP = "ERROR-PERMISSION-IDP";
	
	public static final String ERROR_NOT_FOUND_IN_PCP = "ERROR-NOT-FOUND-IN-PCP";
	
	public static final String ERROR_INVALID_CF_IMPR = "ERROR-INVALID-CF-IMPR";
	
	public static final String ERROR_RIALLINEA_ANAC_S4 = "ERROR-RIALLINEA-ANAC-S4";

	public static final String ERROR_APPALTO_ANNULLATO = "ERROR_APPALTO_ANNULLATO";

	public static final String ERROR_PCP_CONFERMA_ESITO_OP = "ERROR-PCP-CONFERMA-ESITO-OP";

	public static final String ERROR_APPALTO_ANNULLATO_ELIMINARE = "ERROR_APPALTO_ANNULLATO-ELIMINARE";

	public static final String ERROR_PCP_PRESA_CARICO_NO_IDAPPALTO = "ERROR-PCP-PRESA-CARICO-NO-IDAPPALTO";

	@ApiModelProperty(value = "Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	
	@ApiModelProperty(value = "Eventuali parametri di errore in caso di operazione fallita")
	private List<String> errorDataParameters;
	
	private boolean loginError;
	private boolean rpntLoginFailed;
	private String simogUsername;
	private String cfRup;
	private boolean rupCredentialsInvalid;
	private boolean rupCredentialsMissing;
	private ArrayList<String> infoMessaggi;
	private boolean delete;

//	public void copyResponse(BaseResponse copy) {
//		this.esito = copy.esito;
//		this.errorData = copy.errorData;
//		this.loginError = copy.loginError;
//		this.rpntLoginFailed = copy.rpntLoginFailed;
//		this.simogUsername = copy.simogUsername;
//		this.cfRup = copy.cfRup;
//		this.rupCredentialsInvalid = copy.rupCredentialsInvalid;
//		this.rupCredentialsMissing = copy.rupCredentialsMissing;
//		if (copy.infoMessaggi != null) {
//			if (this.infoMessaggi == null) {
//				this.infoMessaggi = new ArrayList<String>();
//			}
//			this.infoMessaggi.addAll(copy.infoMessaggi);
//		}
//	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public List<String> getErrorDataParameters() {
		return errorDataParameters;
	}

	public void setErrorDataParameters(List<String> errorDataParameters) {
		this.errorDataParameters = errorDataParameters;
	}

	public boolean isLoginError() {
		return loginError;
	}

	public void setLoginError(boolean loginError) {
		this.loginError = loginError;
	}

	public boolean isRpntLoginFailed() {
		return rpntLoginFailed;
	}

	public void setRpntLoginFailed(boolean rpntLoginFailed) {
		this.rpntLoginFailed = rpntLoginFailed;
	}

	public String getSimogUsername() {
		return simogUsername;
	}

	public void setSimogUsername(String simogUsername) {
		this.simogUsername = simogUsername;
	}

	public String getCfRup() {
		return cfRup;
	}

	public void setCfRup(String cfRup) {
		this.cfRup = cfRup;
	}

	public boolean isRupCredentialsInvalid() {
		return rupCredentialsInvalid;
	}

	public void setRupCredentialsInvalid(boolean rupCredentialsInvalid) {
		this.rupCredentialsInvalid = rupCredentialsInvalid;
	}

	public boolean isRupCredentialsMissing() {
		return rupCredentialsMissing;
	}

	public void setRupCredentialsMissing(boolean rupCredentialsMissing) {
		this.rupCredentialsMissing = rupCredentialsMissing;
	}

	public ArrayList<String> getInfoMessaggi() {
		return infoMessaggi;
	}

	public void setInfoMessaggi(ArrayList<String> infoMessaggi) {
		this.infoMessaggi = infoMessaggi;
	}
	
	public void rpntLoginFailed() {
		this.errorData = null;
		this.rpntLoginFailed = true;
	}

	public void rupCredentialsMissing(String cfRup) {
		this.rupCredentialsMissing = true;
		this.errorData = null;
		this.cfRup = cfRup;
	}

	public void rupCredentialsInvalid(String simogUsername, String cfRup) {
		this.rupCredentialsInvalid = true;
		this.errorData = null;
		this.simogUsername = simogUsername;
		this.cfRup = cfRup;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
