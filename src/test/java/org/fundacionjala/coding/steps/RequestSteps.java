package org.fundacionjala.coding.steps;

import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.fundacionjala.coding.EndpointHelper;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.fundacionjala.coding.ScenarioContext;
import org.testng.Assert;

public class RequestSteps {

	private Response response;
	private ScenarioContext context;

	public RequestSteps(ScenarioContext context) {
		this.context = context;
	}

	@Given("I send a {string} request to {string} with json body")
	public void iSendARequestToWithJsonBody(String httpMethod, String endpoint, String jsonBody) {
		endpoint = EndpointHelper.buildEndpoint(context, endpoint);
		if ("POST".equalsIgnoreCase(httpMethod)) {
			response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					jsonBody);
		} else {
			response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					jsonBody);
		}
	}

	@Given("I send {string} request to {string}")
	public void iSendARequestTo(String httpMethod, String endpoint) {
		endpoint = EndpointHelper.buildEndpoint(context, endpoint);
		if ("DELETE".equalsIgnoreCase(httpMethod)) {
			response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint);
		} else {
			response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint);
		}
	}

	@Then("I validate the response has status code {int}")
	public void iValidateTheResponseHasStatusCode(int expectedStatusCode) {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, expectedStatusCode);
	}

	@And("I validate the response contains {string} equals {string}")
	public void iValidateTheResponseContainsEquals(String attribute, String expectedValue) {
		String actualProjectName = response.jsonPath().getString(attribute);
		Assert.assertEquals(actualProjectName, expectedValue);
	}

	@And("I save the response as {string}")
	public void iSaveTheResponseAs(String key) {
		context.set(key, response);
	}

	@Given("I send a {string} request to {string}")
	public void iSendARequestTo(String httpMethod, String endpoint, Map<String, String> body) {
		endpoint = EndpointHelper.buildEndpoint(context, endpoint);
		if ("POST".equalsIgnoreCase(httpMethod)) {
			response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					body);
		} else {
			response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					body);
		}
	}
}
