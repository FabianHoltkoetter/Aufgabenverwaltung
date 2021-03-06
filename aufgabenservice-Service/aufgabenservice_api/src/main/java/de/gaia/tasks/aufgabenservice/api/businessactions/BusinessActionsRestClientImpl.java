package de.gaia.tasks.aufgabenservice.api.businessactions;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a simple Implementation for executing BusinessActions on the Microservice.
 *
 * @author p.mueller
 * @version 1.0
 */
public class BusinessActionsRestClientImpl implements BusinessActionsRestClient {

	/** The relation on the base endpoint for the BusinessActions. */
	public static final String BUSINESS_ACTIONS = "businessActions";
	/** The restTemplate to do the REST Operations. */
	private final RestTemplate restTemplate;
	/** Tool used to traverse through the relations. */
	private final Traverson traverson;
	
	/**
	 * Create a new BusinessActionsRestClientImpl by RestTemplate of the server.
	 * @param restTemplate The restTemplate for the HTTP Requests.
	 */
	public BusinessActionsRestClientImpl(RestTemplate restTemplate, final URI basePath) {
		this.restTemplate = restTemplate;
		traverson = new Traverson(basePath, MediaTypes.HAL_JSON);
		traverson.setRestOperations(restTemplate);
	}
	
	/**
	 * This BusinessAction's purpose is: null
	 */
	@Override
	public void mitarbeiterInformieren(){
		final Map<String, Object> params = new HashMap<String, Object>();
		getTraverson()
			.follow(BUSINESS_ACTIONS, "mitarbeiterInformieren")
			.withTemplateParameters(params)
			.toObject(Void.class);
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public Traverson getTraverson() {
		return traverson;
	}
}
