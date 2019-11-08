package org.fundacionjala.pivotal.runner;

import java.util.List;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import org.fundacionjala.core.api.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;

@CucumberOptions(
        glue = {"org.fundacionjala"},
        features = "src/test/resources/features",
        plugin = "pretty"
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeTest
    public void beforeAllScenarios() {

        System.setProperty("dataproviderthreadcount", "5");

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
