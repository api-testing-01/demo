package org.fundacionjala.coding.project;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTProjectTest {

    private String projectId;

//    @BeforeTest
//    public void setUp() {
//        //Given
//        String expectedProjectName = "Rest Assured new 1";
//        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
//                "/projects",
//                "{\"name\":\"" + expectedProjectName + "\"}");
//        projectId = response.jsonPath().getString("id");
//    }

    @Test
    public void testPOSTProject() {
        //Given
        String expectedProjectName = "Rest Assured new 2";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

        //Then
        String actualProjectName = response.jsonPath().getString("name");
        Assert.assertEquals(actualProjectName, expectedProjectName);

    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
