package org.fundacionjala.coding.tdd;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTProjectTest {

    private String projectId;

    @Test
    public void testPOSTProject() {
        //When
        String expectedNewProjectName = "Test PUT 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedNewProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

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
