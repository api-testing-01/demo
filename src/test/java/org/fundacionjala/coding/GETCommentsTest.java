package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETCommentsTest {

    private String storyId;
    private String projectId;
//    private String commentId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new1";

        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

        String storyName = "My Story";


        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");

        storyId = response.jsonPath().getString("id");

        String commentText = "This is a comment text";

        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories/%s/comments", projectId, storyId),
                "{\"text\":\"" + commentText + "\"}");

//        commentId = response.jsonPath().getString("id");

    }

    @Test
    public void testGETtComments() {
        // When
        String expectedNewCommentText = "[This is a comment text]";

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories/%s/comments", projectId, storyId));

        //Then
        Assert.assertEquals(response.jsonPath().getString("text"), expectedNewCommentText);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
