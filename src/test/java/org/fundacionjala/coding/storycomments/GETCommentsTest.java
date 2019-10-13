package org.fundacionjala.coding.storycomments;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETCommentsTest {
    private String projectId;
    private String storyId;
    private String commentId;
    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new Story";
        String StoryName = "StoryTest0001";
        String expectedComment = "Comment test 01";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects", "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
        Response storyresponse = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects/"+ projectId +"/stories", "{\"name\":\"" + StoryName + "\"}");
        storyId = storyresponse.jsonPath().getString("id");
        Response commentresponse = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects/"+ projectId +"/stories/" + storyId + "/comments", "{\"text\":\"" + expectedComment + "\"}");
        commentId = commentresponse.jsonPath().getString("id");

    }
    @Test
    public void testGETComment() {
        //When
        String expectedComment = "Comment test 01";
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s", projectId, storyId, commentId));
        //Then
        String actualComment = response.jsonPath().getString("text");
        Assert.assertEquals(actualComment, expectedComment);
    }
    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
