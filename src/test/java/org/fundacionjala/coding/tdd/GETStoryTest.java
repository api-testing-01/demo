package org.fundacionjala.coding.tdd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETStoryTest {

    private String projectId;
    private String storyId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Get Story Project Test";
        Response responseId = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = responseId.jsonPath().getString("id");
        String storyName = "Get Story Test";
        Response responseStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");
        storyId = responseStory.jsonPath().getString("id");
    }

    @Test
    public void testGETStory() {
        String expectedNewStoryName= "Get Story Test";

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.jsonPath().getString("name"), expectedNewStoryName);
        Assert.assertEquals(response.jsonPath().getString("kind"), "story");
        Assert.assertEquals(response.jsonPath().getString("story_type"), "feature");
        Assert.assertEquals(response.jsonPath().getString("current_state"), "unscheduled");
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
