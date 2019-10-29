package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTCommentsTest {
    private String storyId;
    private String projectId;

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

    }

    @Test
    public void testPOSTComments() {
        // When
        String expectedCommentText = "This a comment";

        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories/%s/comments", projectId, storyId),
                "{\"text\":\"" + expectedCommentText + "\"}");

        //Then
        Assert.assertEquals(response.jsonPath().getString("text"), expectedCommentText);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
