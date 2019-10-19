package org.fundacionjala.coding;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETStoryTest {

    private String projectId;
    private String storyId;

    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
        //And
        String expectedStoryName = "Story Name 1";
        response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories",projectId),
                "{\"name\":\"" + expectedStoryName + "\"}");
        storyId = response.jsonPath().getString("id");
    }


    @Test
    public void testGETStory() {

        //When
        String expectedNewStoryName= "Story Name 1";
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories",projectId));

       Assert.assertEquals(response.jsonPath().getString("name"), expectedNewStoryName);
    }
}
