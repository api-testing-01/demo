package org.fundacionjala.coding.project;

import io.restassured.response.Response;
import org.fundacionjala.coding.RequestManager;
import org.fundacionjala.coding.RequestSpecFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GETProjectTest {

    private String projectId;
    private String WeekStartDay;
    @BeforeTest
    public void setUp() {
        //Given
        String expectedProjectName = "Rest Assured new 1";
        Response response = RequestManager.post(RequestSpecFactory.getRequestSpec("pivotal"),
                "/projects",
                "{\"name\":\"" + expectedProjectName + "\"}");
        projectId = response.jsonPath().getString("id");
    }

    @Test
    public void testGETProject() {
        //When
        String expectedWeekStartDay = "Monday";
        Response response = RequestManager.get(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));

        //Then
        WeekStartDay = response.jsonPath().getString("week_start_day");
        Assert.assertEquals(WeekStartDay, expectedWeekStartDay);

    }

    @AfterTest
    public void cleanData() {
        RequestManager.delete(RequestSpecFactory.getRequestSpec("pivotal"),
                String.format("/projects/%s", projectId));
    }
}
