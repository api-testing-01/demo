package org.fundacionjala.coding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PUTStoryTest {

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
    public void testPUTStory() {

        //When
        String expectedNewStoryName= "New Story Name 1";
        Response response = RequestManager.put(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s",projectId,storyId),
                "{\"name\":\"" + expectedNewStoryName + "\"}");
        String actualStoryName = response.jsonPath().getString("name");
        //actualStoryName = actualStoryName.substring(1,actualStoryName.length()-1);
       Assert.assertEquals(actualStoryName, expectedNewStoryName);
    }
}
