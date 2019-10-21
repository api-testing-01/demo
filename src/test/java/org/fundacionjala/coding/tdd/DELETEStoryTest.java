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

public class DELETEStoryTest {

    private String projectId;
    private String storyId;
    private int statusCode;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Test Delete Story";
        Response responseId = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = responseId.jsonPath().getString("id");
        String storyName = "My Story";
        Response responseStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");
        storyId = responseStory.jsonPath().getString("id");
    }

    @Test
    public void testDELETEStory() {
        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);

        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));
        Assert.assertEquals(response.jsonPath().getString("code"), "unfound_resource");
        Assert.assertEquals(response.jsonPath().getString("kind"), "error");
        Assert.assertEquals(response.jsonPath().getString("error"), "The object you tried to access could not be found.  It may have been removed by another user, you may be using the ID of another object type, or you may be trying to access a sub-resource at the wrong point in a tree.");
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
