package it.appaltiecontratti.monitoraggiocontratti.contratti.client.api.v102;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.KeycloakTokenResponse;
import it.appaltiecontratti.pcp.v102.ApiClient;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-02-03T16:40:04.926+01:00[Europe/Berlin]")
public class AuthenticationApi {
  private ApiClient apiClient;

  public AuthenticationApi() {
	  this(new ApiClient());
  }

  public AuthenticationApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

	public KeycloakTokenResponse getToken(String client_id, String client_secret, String grant_type) throws Exception {
		Object postBody = null;
		// verify the required parameter 'grant_type' is set
		if (client_id == null) {
			throw new Exception("Missing the required parameter 'client_id' when calling getToken");
		}
		if (client_secret == null) {
			throw new Exception("Missing the required parameter 'client_secret' when calling getToken");
		}
		if (grant_type == null) {
			throw new Exception("Missing the required parameter 'grant_type' when calling getToken");
		}
		String path = "";
		// query params
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		formParams.add("client_id", client_id);
		formParams.add("client_secret", client_secret);
		formParams.add("grant_type", grant_type);

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = { "application/x-www-form-urlencoded" };
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] {};

		ParameterizedTypeReference<KeycloakTokenResponse> returnType = new ParameterizedTypeReference<KeycloakTokenResponse>() {
		};
		ResponseEntity<KeycloakTokenResponse> res = apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody,
				headerParams, formParams, accept, contentType, authNames, returnType);
//    GenericType<KeycloakTokenResponse> localVarReturnType = new GenericType<KeycloakTokenResponse>() {};

		return new KeycloakTokenResponse().accessToken(res.getBody().getAccessToken());

	}
  
}
