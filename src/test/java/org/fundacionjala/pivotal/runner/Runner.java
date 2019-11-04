package org.fundacionjala.pivotal.runner;

import java.util.List;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;

@CucumberOptions(
        glue = {"org.fundacionjala.pivotal"},
        features = "src/test/resources/features",
        plugin = "pretty"
)
public class Runner extends AbstractTestNGCucumberTests {

    @BeforeTest
    public void beforeAllScenarios() {
        // clean data
        RequestSpecification requestSpec = RequestSpecFactory.getRequestSpec("pivotal", "owner");
        Response response = RequestManager.get(requestSpec, "/projects");
        List<Integer> allProjectIds = response.jsonPath().getList("id");
        for (Integer id : allProjectIds) {
            RequestManager.delete(requestSpec, String.format("/projects/%d", id));
        }
        // Restore flag by default
    }

    @AfterTest
    public void afterAllScenarios() {
        // clean data
        // Restore flag by default
    }
}
