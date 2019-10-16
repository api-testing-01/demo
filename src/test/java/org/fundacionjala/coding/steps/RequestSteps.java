package org.fundacionjala.coding.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;

public class RequestSteps {

	private Response response;

	@Given("I send a {string} request to {string}")
	public void iSendARequestTo(String httpMethod, String endpoint, String jsonBody) {
		if ("POST".equalsIgnoreCase(httpMethod)) {
			response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					jsonBody);
		} else {
			endpoint = endpoint.replace("{projectId}", response.jsonPath().getString("id"));
			response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
					endpoint,
					jsonBody);
		}
	}

	@Given("I send a DELETE request to {string}")
	public void iSendARequestTo(String endpoint) {
		endpoint = endpoint.replace("{projectId}", response.jsonPath().getString("id"));
		response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
				endpoint);
	}

	@When("I send a GET request to {string}")
	public void iSendGETRequestTo(String endpoint) {
		endpoint = endpoint.replace("{projectId}", response.jsonPath().getString("id"));
		response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
				endpoint);
	}

	@Then("I validate the response has status code {string}")
	public void iValidateTheResponseHasStatusCode(String expectedStatusCode) {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, Integer.parseInt(expectedStatusCode));
	}

	@And("I validate the response contains {string} equals {string}")
	public void iValidateTheResponseContainsEquals(String attribute, String expectedValue) {
		String actualProjectName = response.jsonPath().getString(attribute);
		Assert.assertEquals(actualProjectName, expectedValue);
	}
}
