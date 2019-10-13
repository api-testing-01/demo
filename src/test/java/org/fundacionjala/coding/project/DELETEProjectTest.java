package org.fundacionjala.coding.project;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DELETEProjectTest {

    private String projectId;
    private int statusCode;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testDELETEProject() {
        //When
        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));

        //Then
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);

        // Using rest api
        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
        Assert.assertEquals(response.jsonPath().getString("code"), "unauthorized_operation");
        Assert.assertEquals(response.jsonPath().getString("kind"), "error");
        Assert.assertEquals(response.jsonPath().getString("error"), "Authorization failure.");
        Assert.assertEquals(response.jsonPath().getString("general_problem"), "You aren't authorized to access the requested resource.");

        // Using DB
    }

    @AfterTest
    public void cleanData() {
        if (statusCode != 204) {
            // clean using DB
        }
    }

}
