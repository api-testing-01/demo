package org.fundacionjala.coding.story;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTStoryTest {
    private String projectId;
//    private String storyId;
    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new Story";
        String StoryName = "StoryTest0001";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects", "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }
    @Test
    public void testPOSTStory() {
        //When
        String expectedStoryName = "Test PUT 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + expectedStoryName + "\"}");
        //Then
        String actualStoryName = response.jsonPath().getString("name");
        Assert.assertEquals(actualStoryName, expectedStoryName);
    }
    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
