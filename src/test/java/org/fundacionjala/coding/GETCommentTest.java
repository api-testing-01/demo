package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETCommentTest {
    private String projectId;
    private String storyId;
    private String commentId;
    String textComment = "this is comment text";
    @BeforeTest
    public void setUp() {
        String expectedProjectName = "Rest Assured new";
        String expectedStoryName = "Rest new Story 1";

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
    public void testGETComment(){
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));
        String actualProjectName = response.jsonPath().getString("text");
        Assert.assertEquals(actualProjectName, textComment);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
