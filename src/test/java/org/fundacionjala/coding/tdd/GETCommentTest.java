package org.fundacionjala.coding.tdd;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETCommentTest {

    private String projectId;
    private String storyId;
    private String commentId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Comment Test Project";
        Response responseId = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = responseId.jsonPath().getString("id");
        String storyName = "Comment Test Story";
        Response responseStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");
        storyId = responseStory.jsonPath().getString("id");
        String commentText = "Get Comment Test";
        Response responseComment = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects/"+ projectId +"/stories/" + storyId + "/comments", "{\"text\":\"" + commentText + "\"}");
        commentId = responseComment.jsonPath().getString("id");
    }

    @Test
    public void testGETComment() {
        String expectedCommentText = "Get Comment Test";

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));

        Assert.assertEquals(response.jsonPath().getString("text"), expectedCommentText);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
