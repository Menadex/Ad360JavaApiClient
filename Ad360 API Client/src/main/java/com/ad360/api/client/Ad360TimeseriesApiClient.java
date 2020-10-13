package com.ad360.api.client;

import com.ad360.api.ApiClient;
import com.ad360.api.ApiException;
import com.ad360.api.ApiTimeseriesClient;
import com.ad360.api.client.model.AuthSpec;
import com.ad360.api.client.model.AuthToken;

import lombok.Getter;
import lombok.Setter;

public class Ad360TimeseriesApiClient extends ApiTimeseriesClient {
	
	@Getter @Setter String username;
	@Getter @Setter String password;
	@Getter @Setter String apiKey;
	
	public Ad360TimeseriesApiClient(String username, String password, String apiKey) throws ApiException {
		
		this.username = username;
		this.password = password;
		this.apiKey = apiKey;
		
		super
			.addDefaultHeader("Authorization", getAuthToken())
			.setDebugging(true);
	}
	
	private String getAuthToken() throws ApiException {
		
		ApiClient apiClient = new ApiClient().setDebugging(true);
		AuthApi authApi = new AuthApi(apiClient);
		
		AuthSpec authSpec = new AuthSpec().username(username).password(password);
		AuthToken authToken = authApi.getAuthToken(authSpec, apiKey);
		return authToken.getToken();
	}
	
}
