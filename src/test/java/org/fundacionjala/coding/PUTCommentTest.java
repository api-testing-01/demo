package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PUTCommentTest {
    private String projectId;
    private String storyId;
    private String commentId;

    @BeforeTest
    public void setUp() {
        String expectedProjectName = "Rest Assured new";
        String expectedStoryName = "Rest new Story 1";
        String textComment = "this is comment text";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

        Response resStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\""+expectedStoryName+"\"}");
        storyId = resStory.jsonPath().getString("id");

        Response comment = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments", projectId, storyId),
                "{\"text\":\""+textComment+"\"}");
        commentId = comment.jsonPath().getString("id");
    }
    @Test
    public void testPUTComment(){
        String commentEdited = "new comment edited";
        Response editResponse = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId),
                "{\"name\":\""+commentEdited+"\"}");
        Assert.assertEquals(editResponse.getStatusCode(), 200);
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));
        String actualProjectName = response.jsonPath().getString("text");
        Assert.assertEquals(editResponse.getStatusCode(), 200);
        Assert.assertEquals(actualProjectName, commentEdited);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
