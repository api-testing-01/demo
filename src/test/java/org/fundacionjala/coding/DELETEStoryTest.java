package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DELETEStoryTest {
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

        Response resStory = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories", projectId),
                "{\"name\":\""+expectedStoryName+"\"}");
        storyId = resStory.jsonPath().getString("id");
    }

    @Test
    public void testDELETEStory(){
        String expectedNewStoryName = "new story name";
        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));
        Assert.assertEquals(response.getStatusCode(), 204);
        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));
        Assert.assertEquals(response.getStatusCode(), 404);

    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
