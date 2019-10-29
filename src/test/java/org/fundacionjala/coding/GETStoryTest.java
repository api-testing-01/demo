package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.fundacionjala.pivotal.RequestManager;
import org.fundacionjala.pivotal.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETStoryTest {

    private String storyId;
    private String projectId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured get";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");

        String storyName = "My Story";
        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories", projectId),
                "{\"name\":\"" + storyName + "\"}");
        storyId = response.jsonPath().getString("id");

    }

    @Test
    public void testGETStory() {
        // When
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"), String.format("/projects/%s/stories/%s", projectId, storyId));
        //Then
        String storyName = "My Story";
        String actualStoryName = response.jsonPath().getString("name");
        String actualProjectId = response.jsonPath().getString("project_id");
        Assert.assertEquals(actualStoryName, storyName);
        Assert.assertEquals(actualProjectId, projectId);
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
