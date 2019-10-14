package org.fundacionjala.coding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StoryTest {

    private String projectId;
    private String storyId;

    @BeforeTest
    public void setUp() {

        String expectedProjectName = "ProjectStory2";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + expectedProjectName + "\"}")
                .post("/projects");
        projectId = response.jsonPath().getString("id");

        String storyName = "Story1";
        response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + storyName + "\"}")
                .post(String.format("/projects/%s/stories", projectId));
        storyId = response.jsonPath().getString("id");
    }

    //default fields
    @Test
    public void testPUTStory() {
        String expectedNewStoryName= "Story1Updated";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + expectedNewStoryName + "\"}")
                .put(String.format("/projects/%s/stories/%s", projectId, storyId));
        response.prettyPrint();
        Assert.assertEquals(response.jsonPath().getString("name"), expectedNewStoryName);
    }

    //with more fields
    @Test
    public void testPUTStoryMoreFields() {
        String expectedNewStoryName = "StoryAUpdated";
        String expectedDesc = "this is a short description";
        String expectedStoryType = "feature";
        String expectedCurrentState = "started";
        float expectedEstimate = 2f;

        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + expectedNewStoryName +"\"," +
                        "\"description\":\"" + expectedDesc+ "\"," +
                        "\"story_type\":\"" + expectedStoryType+ "\"," +
                        "\"current_state\":\"" + expectedCurrentState+ "\"," +
                        "\"estimate\":" + expectedEstimate+ "}")
                .put(String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.jsonPath().getString("name"), expectedNewStoryName);
        Assert.assertEquals(response.jsonPath().getString("description"), expectedDesc);
        Assert.assertEquals(response.jsonPath().getString("story_type"), expectedStoryType);
        Assert.assertEquals(response.jsonPath().getString("current_state"), expectedCurrentState);
        Assert.assertEquals(Float.parseFloat(response.jsonPath().getString("estimate")), expectedEstimate);
    }

    //Single Story
    @Test
    public void testGETStory(){
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .get(String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("current_state"),"unscheduled");
        Assert.assertEquals(response.jsonPath().getString("story_type"), "feature");
    }

    //post story
    @Test
    public void testPOSTStory(){
        String storyName = "testPOSTStory";
        String current_state = "unstarted";
        String story_type = "release";
        String deadline = "2019-12-12T04:25:15Z";

        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"name\":\"" + storyName + "\"," +
                        "\"current_state\":\"" + current_state + "\"," +
                        "\"story_type\":\"" + story_type + "\"," +
                        "\"deadline\":\"" + deadline + "\"}")
                .post(String.format("/projects/%s/stories", projectId));

        Assert.assertEquals(response.getStatusCode(), 200);
        String story_id = response.jsonPath().getString("id");

        response =  RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, story_id));

        Assert.assertEquals(response.jsonPath().getString("name"), storyName);
        Assert.assertEquals(response.jsonPath().getString("current_state"), current_state);
        Assert.assertEquals(response.jsonPath().getString("story_type"), story_type);
        Assert.assertEquals(response.jsonPath().getString("deadline"), deadline);

    }

    @Test(priority = 1)
    public void testDELETEStory(){
        Response response = RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.getStatusCode(), 204);

        response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s", projectId, storyId));

        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.jsonPath().getString("code"), "unfound_resource");
        Assert.assertEquals(response.jsonPath().getString("kind"), "error");
    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
