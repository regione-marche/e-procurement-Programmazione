package it.appaltiecontratti.programmi.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import it.appaltiecontratti.programmi.entity.UsrSysEntry;


/**
 * DAO Interface per l'estrazione delle informazioni relative al login
 * nell'applicazione.
 * 
 * @author Stefano.Sabbadin
 */
@Mapper
public interface AccountMapper {

	/**
	 * Restituisce i dati dell'account relativi alle credenziali passate
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return dati relativi all'account
	 */
	@Select("SELECT syspwd as password FROM usrsys WHERE UPPER(syslogin) = #{username}")
	public String getAccountPwd(@Param("username") String username);

	/**
	 * Restituisce una clientKey se clientId esiste
	 * 
	 * @param clientId
	 *            clientId
	 * @return clientKey relativa all'account
	 */
	@Select("SELECT chiave FROM w_application_keys WHERE clientid = #{clientId}")
	public String getClientKey(@Param("clientId") String clientId);

	/**
	 * Restituisce la chiave di cifratura per il JWT Token salvato nella w_config 
	 * 
	 * @param codapp
	 *            codapp
	 * @param chiave
	 *            chiave
	 * @return Jwt key
	 */
	@Select("SELECT valore FROM w_config WHERE UPPER(codapp) = #{codapp} AND chiave = #{chiave}")	
	public String getCipherKey(@Param("codapp") String codapp, @Param("chiave") String chiave);

	
	/**
	 * Restituisce le info associate all'utente nella usersys 
	 * 
	 * @param username
	 *            username
	 * @return UsrSysEntry usrSys
	 */
	@Select("SELECT syscon, syspwbou FROM usrsys WHERE UPPER(syslogin) = #{username}")
	public UsrSysEntry getUserSysInfo(@Param("username") String username);
}
