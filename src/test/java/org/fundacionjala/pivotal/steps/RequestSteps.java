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

    // Rosario Falconi
    @And("I validate the response contains {string} is not null")
    public void iValidateTheResponseContainsIsNotNull(final String attribute) {
        String actualProjectId = response.jsonPath().getString(attribute);
        Assert.assertNotNull(actualProjectId);
    }
}
