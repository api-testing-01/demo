package org.fundacionjala.coding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CommentsTest {

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
    public void testPOSTComment() {
        String expectedComment= "This is a short comment for the story";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"text\":\"" + expectedComment + "\"}")
                .post(String.format("/projects/%s/stories/%s/comments", projectId, storyId));
        Assert.assertEquals(response.jsonPath().getString("text"), expectedComment);
    }

    //with different fields
    @Test
    public void testPOSTMoreComment() {
        String commit_identifier = "abc123";
        String commit_type = "github";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"commit_type\":\"" + commit_type + "\"," +
                        "\"commit_identifier\":\"" + commit_identifier + "\"}")
                .post(String.format("/projects/%s/stories/%s/comments", projectId, storyId));
        Assert.assertEquals(response.jsonPath().getString("commit_identifier"), commit_identifier);
    }

    @Test
    public void testGETComments(){

        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments", projectId, storyId));

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void testPUTCommentByCommitID(){
        String originalComment= "Original Comment";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"text\":\"" + originalComment + "\"}")
                .post(String.format("/projects/%s/stories/%s/comments", projectId, storyId));

        String commitID = response.jsonPath().getString("id");
        String expectedComment = "Modified Comment";

        Response response_put = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"text\":\"" + expectedComment + "\"}")
                .put(String.format("/projects/%s/stories/%s/comments/%s/", projectId, storyId, commitID));

        Assert.assertEquals(response_put.jsonPath().getString("text"), expectedComment);

    }

    @Test
    public void testGETCommentById(){
        String originalComment= "Original Comment";
        Response response = RestAssured.given(RequestSpecFactory.getRequestSpec("pivotal"))
                .contentType(ContentType.JSON)
                .when()
                .body("{\"text\":\"" + originalComment + "\"}")
                .post(String.format("/projects/%s/stories/%s/comments", projectId, storyId));

        String commitID = response.jsonPath().getString("id");

        Response response_get = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s/stories/%s/comments/%s/", projectId, storyId, commitID));

        Assert.assertEquals(response_get.getStatusCode(), 200);
        Assert.assertEquals(response_get.jsonPath().getString("text"), originalComment);

    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }

}
