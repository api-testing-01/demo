package org.fundacionjala.pivotal.steps;

import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import org.fundacionjala.pivotal.EndpointHelper;
import org.fundacionjala.pivotal.JsonHelper;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.fundacionjala.pivotal.ScenarioContext;

import static org.testng.Assert.assertEquals;

public class RequestSteps {

    private final RequestSpecification requestSpecification;
    private Response response;
    private ScenarioContext context;

    public RequestSteps(final ScenarioContext context) {
        this.context = context;
        requestSpecification = RequestSpecFactory.getRequestSpec("pivotal");
    }

    @Given("I send a {string} request to {string} with json body")
    public void iSendARequestToWithJsonBody(final String httpMethod, final String endpoint,
                                            final String jsonBody) {
        switch (httpMethod) {
            case "POST":
                response = RequestManager.post(requestSpecification,
                        EndpointHelper.buildEndpoint(context, endpoint),
                        jsonBody);
                break;
            case "PUT":
                response = RequestManager.put(requestSpecification,
                        EndpointHelper.buildEndpoint(context, endpoint),
                        jsonBody);
                break;
            case "GET":
                response = RequestManager.get(requestSpecification,
                        EndpointHelper.buildEndpoint(context, endpoint)
                );
                break;
            default:
                response = null;
                break;
        }
    }

    @Given("I send a {string} request to {string} with json file {string}")
    public void iSendARequestToWithJsonFile(final String httpMethod, final String endpoint,
                                            final String jsonPath) {
        JSONObject jsonBody = JsonHelper.getJsonObject("src/test/resources/".concat(jsonPath));
        if ("POST".equalsIgnoreCase(httpMethod)) {
            response = RequestManager.post(requestSpecification,
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        } else {
            response = RequestManager.put(requestSpecification,
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        }
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(final String endpoint) {
        response = RequestManager.get(requestSpecification,
                EndpointHelper.buildEndpoint(context, endpoint));
    }

    @Given("I send a DELETE request to {string}")
    public void iSendARequestTo(final String endpoint) {
        response = RequestManager.delete(requestSpecification,
                EndpointHelper.buildEndpoint(context, endpoint));
    }

    @Then("I validate the response has status code {int}")
    public void iValidateTheResponseHasStatusCode(int expectedStatusCode) {
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, expectedStatusCode);
    }

    @And("I validate the response contains {string} equals {string}")
    public void iValidateTheResponseContainsEquals(final String attribute, final String expectedValue) {
        String value = response.jsonPath().getString(attribute);

        if (value.contains("[")) {
            String actualValue = response.jsonPath().getList(attribute, String.class).get(0);
            assertEquals(actualValue, expectedValue);
        } else {
            assertEquals(value, expectedValue);
        }
    }

    @And("I save the response as {string}")
    public void iSaveTheResponseAs(final String key) {
        context.set(key, response);
    }

    @Given("I send a {string} request to {string}")
    public void iSendARequestTo(final String httpMethod, final String endpoint, final Map<String, String> body) {
        if ("POST".equalsIgnoreCase(httpMethod)) {
            response = RequestManager.post(requestSpecification,
                    EndpointHelper.buildEndpoint(context, endpoint),
                    body);
        } else {
            response = RequestManager.put(requestSpecification,
                    EndpointHelper.buildEndpoint(context, endpoint),
                    body);
        }
    }
}
