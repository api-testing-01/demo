package org.fundacionjala.coding.tdd;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTCommentTest {

    private String projectId;
    private String storyId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Story Comments";
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
    public void testPUTComment() {
        //Given
        String expectedNewComment = "Rest Assured new 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments", projectId, storyId),
                "{\"text\":\"" + expectedNewComment + "\"}");

        Assert.assertEquals(response.jsonPath().getString("text"), expectedNewComment);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
