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

    private int expectedProjectAccountId = 1103421;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured Get";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"," +
                        " \"account_id\": " + expectedProjectAccountId + " }");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testGETProject() {
        //When

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));

        //Then
        String actualProjectName = response.jsonPath().getString("name");
        String expectedProjectName = "Rest Assured Get";
        int actualProjectAccountId = response.jsonPath().getInt("account_id");
        Assert.assertEquals(actualProjectName, expectedProjectName);
        Assert.assertEquals(actualProjectAccountId, expectedProjectAccountId);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
