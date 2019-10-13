package org.fundacionjala.coding.story;

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
    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new Story";
        String StoryName = "StoryTest0001";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects","{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + StoryName + "\"}");
        storyId = response.jsonPath().getString("id");
//        Response storyresponse = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
//                "/projects/"+ projectId +"/stories", "{\"name\":\"" + StoryName + "\"}");
//        storyId = storyresponse.jsonPath().getString("id");

    }
    @Test
    public void testGETStory() {
        //When
        String expectedStoryName = "StoryTest0001";
        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        //Then
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);

    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
