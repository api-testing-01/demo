package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PUTProjectTest {

    private String projectId;

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
    public void testPUTProject() {
        //When
        String expectedNewProjectName = "Test PUT 1";
        Response response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId),
                "{\"name\":\"" + expectedNewProjectName + "\"}");

        //Then
        String actualProjectName = response.jsonPath().getString("name");
        Assert.assertEquals(actualProjectName, expectedNewProjectName);
    }

    @Test
    public void testGETProject() {
        //When
        String expectedNewProjectName = "testGETProject";
        RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId),
                "{\"name\":\"" + expectedNewProjectName + "\"}");

        Response response_get = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
        //Then
        Assert.assertEquals(response_get.getStatusCode(), 200);
        Assert.assertEquals(response_get.jsonPath().getString("name"), expectedNewProjectName);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
