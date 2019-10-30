package org.fundacionjala.pivotal.steps;

import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;

import org.fundacionjala.pivotal.EndpointHelper;
import org.fundacionjala.pivotal.JsonHelper;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.fundacionjala.pivotal.ScenarioContext;

public class RequestSteps {

    private Response response;
    private ScenarioContext context;

    public RequestSteps(final ScenarioContext context) {
        this.context = context;
    }

    @Given("I send a {string} request to {string}")
    public void iSendARequestToWithJsonBody(final String httpMethod, final String endpoint,
                                            final String jsonBody) {
        if ("POST".equalsIgnoreCase(httpMethod)) {
            response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        } else {
            response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        }
    }

    @Given("I send a {string} request to {string} with json file {string}")
    public void iSendARequestToWithJsonFile(final String httpMethod, final String endpoint,
                                            final String jsonPath) {
        JSONObject jsonBody = JsonHelper.getJsonObject("src/test/resources/".concat(jsonPath));
        if ("POST".equalsIgnoreCase(httpMethod)) {
            response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        } else {
            response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    jsonBody);
        }
    }

    @Given("I send a DELETE request to {string}")
    public void iSendARequestTo(final String endpoint) {
        response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                EndpointHelper.buildEndpoint(context, endpoint));
    }

    @Then("I validate the response has status code {int}")
    public void iValidateTheResponseHasStatusCode(int expectedStatusCode) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, expectedStatusCode);
    }

    @And("I validate the response contains {string} equals {string}")
    public void iValidateTheResponseContainsEquals(final String attribute, final String expectedValue) {
        String actualProjectName = response.jsonPath().getString(attribute);
        Assert.assertEquals(actualProjectName, expectedValue);
    }

    @And("I save the response as {string}")
    public void iSaveTheResponseAs(final String key) {
        context.set(key, response);
    }

    @Given("I send a {string} request to {string}")
    public void iSendARequestTo(final String httpMethod, final String endpoint, final Map<String, String> body) {
        if ("POST".equalsIgnoreCase(httpMethod)) {
            response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    body);
        } else {
            response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                    EndpointHelper.buildEndpoint(context, endpoint),
                    body);
        }
    }

    @And("I validate the component {int} from {string} equals {string}")
    public void iValidateTheZoneFromEquals(final int component, final String attribute, final String expectedValue) {
        String string = response.jsonPath().getString(attribute);
        String[] extractZone = string.split("[\\\\.$|,|;|:]");
        String actualProjectName = extractZone[component];
        Assert.assertEquals(actualProjectName, expectedValue);
    }
}
