package org.fundacionjala.coding.tdd;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PUTProjectTest {

    private String projectId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Put Project Test";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testPUTProject() {
        //When
        String expectedNewProjectName = "New Put Project Test";
        Response response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId),
                "{\"name\":\"" + expectedNewProjectName + "\"}");

        //Then
        String actualProjectName = response.jsonPath().getString("name");
        Assert.assertEquals(actualProjectName, expectedNewProjectName);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
