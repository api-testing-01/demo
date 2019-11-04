package org.fundacionjala.pivotal.hooks;

import java.util.List;

import io.cucumber.java.After;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.ScenarioContext;

public class ProjectHooks {

    private ScenarioContext context;

    public ProjectHooks(final ScenarioContext context) {
        this.context = context;
    }

    @After(value = "@cleanProject")
    public void afterScenario() {
        RequestSpecification requestSpec = (RequestSpecification) context.get("REQUEST_SPEC");
        Response response = RequestManager.get(requestSpec, "/projects");
        List<Integer> allProjectIds = response.jsonPath().getList("id");
        for (Integer id : allProjectIds) {
            RequestManager.delete(requestSpec, String.format("/projects/%d", id));
        }
    }
}
