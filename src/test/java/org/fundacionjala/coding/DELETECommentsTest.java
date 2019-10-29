package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DELETECommentsTest {

    private String storyId;
    private String projectId;
    private String commentId;
    private int statusCode;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new1";

        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

        String storyName = "My Story";


        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");

        storyId = response.jsonPath().getString("id");

        String commentText = "This is a comment text";

        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments", projectId, storyId),
                "{\"text\":\"" + commentText + "\"}");

        commentId = response.jsonPath().getString("id");

    }

    @Test
    public void testDELETEComments() {
        // When

        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));

        //Then
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);

        // Using rest api
        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));
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
