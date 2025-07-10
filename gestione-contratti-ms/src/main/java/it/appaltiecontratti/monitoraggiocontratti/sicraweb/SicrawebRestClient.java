package it.appaltiecontratti.monitoraggiocontratti.sicraweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.ApiToken;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.Cig;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.LogonRequest;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.LogonResponse;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaBudgetImpegniRequest;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaBudgetImpegniResponse;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaMandatiRequest;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaMandatiResponse;

@Service("sicrawebrestclient")
public class SicrawebRestClient {

	private static final Logger LOG = LogManager.getLogger(SicrawebRestClient.class);

	private Client getClient() {
		var client = ClientBuilder.newClient();
		client.register(new LoggingFeature(
				java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
				java.util.logging.Level.INFO,
			    LoggingFeature.Verbosity.PAYLOAD_ANY,
			    8192));
		return client;
		
	}
	
	public RicercaMandatiResponse ricercaMandati(String serviceUrl, String token, String cig) {

		try {
			
			String apiUrl = buildUrl(serviceUrl, "ragioneria/fde/v1/integrazioni/sitat/mandati/ricerca_mandati");
			
			Client client = this.getClient();
			
			RicercaMandatiRequest req = new RicercaMandatiRequest();
			req.setTipoRisultato("JSON");
	        
			List<Cig> listaCig = new ArrayList<Cig>();
	        listaCig.add(new Cig(cig));
	        req.setListaCig(listaCig);
	        
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        ApiToken tk = objectMapper.readValue(token, ApiToken.class);
	        
	        
            String jsonRequest = objectMapper.writeValueAsString(req);
            LOG.debug("JSON Request (ricerca_mandati): " + jsonRequest); // Log the JSON
	        
	        Response response = client.target(apiUrl) 
	                .request(MediaType.APPLICATION_JSON) 
	                .header("Authorization", "Bearer " + tk.getMaggioliApiToken()) 
	                .post(Entity.entity(req, MediaType.APPLICATION_JSON));
	        
	        //var res1 = response.readEntity(String.class);
	        
	        int status = response.getStatus();
	        if (status == 200) {
		        RicercaMandatiResponse res = response.readEntity(RicercaMandatiResponse.class);
		        var resAsString = objectMapper.writeValueAsString(res);
		        LOG.debug("JSON Response (ricerca_mandati): " + resAsString); // Log the JSON
		        
		        client.close();
		        return res;

	        } else if (status == 500) {
	            // Internal Server Error: Handle 500 status
	        	String err = response.readEntity(String.class);
	        	LOG.error("Internal Server Error: The server encountered an error:" + err);
	            client.close();
	            throw new RuntimeException("Internal Server Error: The server encountered an error.");

	        } else {
	            // Other status codes
	            LOG.error("Unexpected Status: " + status);
	            String errorMessage = response.readEntity(String.class); 
	            client.close();
	            throw new RuntimeException("Unexpected Status Code: " + status + " - response: " + errorMessage);
	        }

		} catch (JsonProcessingException ex) {
			LOG.error("Error processing token json", ex);
			throw new RuntimeException("Error processing token json:" + ex);
		} catch (Exception ex) {
			LOG.error("", ex);
			throw ex;
		}		
	}
	
	public RicercaBudgetImpegniResponse ricercaBudgetImpegni(String serviceUrl, String token, Date datavaluta, String cig) {

		try {
			String apiUrl = buildUrl(serviceUrl, "ragioneria/fde/v1/integrazioni/generico/budget/ricerca_budget_impegni");
			
			Client client = this.getClient();
			
			RicercaBudgetImpegniRequest req = new RicercaBudgetImpegniRequest();
			req.setParte("U");
			req.setFlraggruppacig(true);
			req.setFlraggruppaanagrafica(true);
			req.setFlraggruppatipofinanz(true);
			req.setTiporisultato("JSON");
	        req.setDatavaluta(datavaluta);
	        
	        var listaCig = new ArrayList<Cig>();
	        listaCig.add(new Cig(cig));
	        req.setListaCig(listaCig);
	        
	        //req.setCup("");
	        
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        ApiToken tk = objectMapper.readValue(token, ApiToken.class);
	        
	        
            String jsonRequest = objectMapper.writeValueAsString(req);
            LOG.debug("JSON Request (ricercaBudgetImpegni): " + jsonRequest); // Log the JSON
	        
	        Response response = client.target(apiUrl) 
	                .request(MediaType.APPLICATION_JSON) 
	                .header("Authorization", "Bearer " + tk.getMaggioliApiToken()) 
	                .post(Entity.entity(req, MediaType.APPLICATION_JSON));
	        
	        //var res1 = response.readEntity(String.class);
	        //LOG.debug("JSON Response (ricercaBudgetImpegni): " + res1); // Log the JSON
	        
	        int status = response.getStatus();
	        if (status == 200) {
		        RicercaBudgetImpegniResponse res = response.readEntity(RicercaBudgetImpegniResponse.class);
		        var resAsString = objectMapper.writeValueAsString(res);
		        LOG.debug("JSON Response (ricercaBudgetImpegni): " + resAsString); // Log the JSON
		        
		        client.close();
		        return res;

	        } else if (status == 500) {
	            // Internal Server Error: Handle 500 status
	        	LOG.error("Internal Server Error: The server encountered an error.");
	            client.close();
	            throw new RuntimeException("Internal Server Error: The server encountered an error.");

	        } else {
	            // Other status codes
	            LOG.error("Unexpected Status: " + status);
	            String errorMessage = response.readEntity(String.class); 
	            client.close();
	            throw new RuntimeException("Unexpected Status Code: " + status + " - response: " + errorMessage);
	        }

		} catch (JsonProcessingException ex) {
			LOG.error("Error processing token json", ex);
			throw new RuntimeException("Error processing token json:" + ex);
		} catch (Exception ex) {
			LOG.error("", ex);
			throw ex;
		}		
	}


	public String logon(String serviceUrl, String alias, String userName, String password) {

	    try {
	        
	        String apiUrl = buildUrl(serviceUrl, "infrastruttura/aut/v1/basic/logon");
	        
	        Client client = this.getClient();
	        
	        LogonRequest req = new LogonRequest();
	        req.setAlias(alias);
	        req.setUsername(userName);
	        req.setPassword(password);

	        Response response = client.target(apiUrl)
	                .request(MediaType.APPLICATION_JSON)
	                .post(Entity.entity(req, MediaType.APPLICATION_JSON));        

	        int status = response.getStatus();
	        if (status == 200) {
	            LogonResponse res = response.readEntity(LogonResponse.class);
	            String token = res.getBearerToken();
	            client.close();
	            return token;

	        } else if (status == 401) {
	            // Unauthorized: Handle 401 status
	        	LOG.error("Unauthorized: Invalid credentials.");
	            client.close();
	            throw new RuntimeException("Unauthorized: Invalid credentials.");

	        } else if (status == 500) {
	            // Internal Server Error: Handle 500 status
	        	String errorMessage = response.readEntity(String.class);
	        	LOG.error("Internal Server Error: The server encountered an error:" + errorMessage);
	            client.close();
	            throw new RuntimeException("Internal Server Error: The server encountered an error.");

	        } else {
	            // Other status codes
	            String errorMessage = response.readEntity(String.class); 
	            LOG.error("Unexpected Status Code: " + status + ", Response: " + errorMessage);
	            client.close();
	            throw new RuntimeException("Unexpected Status Code: " + status + ", Response: " + errorMessage);
	        }

	    } catch (Exception ex) {
	        LOG.error("SicrawebRestClient - logon- An error occurred during logon.", ex);
	        throw ex;
	    }
	}


	private String buildUrl(final String baseUrl, final String endpoint) {
		String controller = baseUrl;
		if (controller == null)
			return null;
		if (!controller.endsWith("/"))
			controller += "/";
		String url = controller + endpoint;
		return url;
	}

}
