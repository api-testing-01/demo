package org.fundacionjala.coding.tdd;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETProjectTest {

    private String projectId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Get Project Test";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testGETProject() {
        //When
        String expectedProjectName = "Get Project Test";
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));

        //Then
        Assert.assertEquals(response.jsonPath().getString("name"), expectedProjectName);
        Assert.assertEquals(response.jsonPath().getString("kind"), "project");
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
