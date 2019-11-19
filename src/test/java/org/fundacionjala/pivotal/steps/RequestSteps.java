package org.fundacionjala.pivotal.steps;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import org.fundacionjala.core.ScenarioContext;
import org.fundacionjala.pivotal.RequestSpecFactory;

public class RequestSteps {

    private ScenarioContext context;

    public RequestSteps(final ScenarioContext context) {
        this.context = context;
    }

    @Given("I use the {string} service and the {string} account")
    public void iUseTheService(final String serviceName, final String accountName) {
        RequestSpecification requestSpecification = RequestSpecFactory.getRequestSpec(serviceName, accountName);
        context.set("REQUEST_SPEC", requestSpecification);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(final String endpoint) {
        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                EndpointHelper.buildEndpoint(context, endpoint));
    }
}
