package org.example.pivotal.steps;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import io.automation.ScenarioContext;
import org.example.pivotal.RequestSpecFactory;

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
}
