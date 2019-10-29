package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETProjectTest {
    private String projectId;
    private String expectedProjectName = "Rest Assured new 1";

    @BeforeTest
    public void setUp() {
        //Given
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testGETProject() {
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
        //Then
        String actualProjectName = response.jsonPath().getString("name");
        String kindProject = response.jsonPath().getString("kind");
        Assert.assertEquals(actualProjectName, expectedProjectName);
        Assert.assertEquals(kindProject, "project");
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
