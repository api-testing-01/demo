package org.fundacionjala.coding.tdd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTStoryTest {

    private String projectId;
    private String storyId;
    private int statusCode;

    @Test
    public void testPOSTStory() {
        //Given
        String expectedProjectName = "Rest Assured new 1";
        Response responseId = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = responseId.jsonPath().getString("id");
        String storyName = "My Story";
        Response responseStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");
        storyId = responseStory.jsonPath().getString("id");

        Assert.assertEquals(responseStory.jsonPath().getString("name"), storyName);
        Assert.assertEquals(responseStory.getStatusCode(), 200);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
