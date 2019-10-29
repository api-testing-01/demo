package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class POSTStoriesTest {
    private String projectId;
    private String storyId;
    String expectedStoryName = "Rest Story Name1";

    @BeforeTest
    public void setUp() {
        String expectedProjectName = "Rest Assured new 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

    }

    @Test
    public void testPOSTStory(){
        Response resStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\""+expectedStoryName+"\"}");
        storyId = resStory.jsonPath().getString("id");
        Assert.assertEquals(resStory.getStatusCode(), 200);

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        String actualProjectName = response.jsonPath().getString("name");
        Assert.assertEquals(actualProjectName, expectedStoryName);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
