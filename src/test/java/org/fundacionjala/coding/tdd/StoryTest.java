package org.fundacionjala.coding.tdd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoryTest {

    @Test
    public void testPUTStory() {

        //Given
        String expectedProjectName = "Rest Assured new";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + expectedProjectName + "\"}")
                .post("/projects");
        String projectId = response.jsonPath().getString("id");

        String storyName = "My Story";
        response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + storyName + "\"}")
                .post(String.format("/projects/%s/stories", projectId));
        String storyId = response.jsonPath().getString("id");

        // When
        String expectedNewStoryName= "New Story Name";
        response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + expectedNewStoryName + "\"}")
                .put(String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.jsonPath().getString("name"), expectedNewStoryName);
    }
}