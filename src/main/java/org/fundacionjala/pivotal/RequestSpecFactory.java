package org.fundacionjala.pivotal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private static final Map<String, Supplier<RequestSpecification>> REQUEST_SPEC_MAP = new HashMap<>();
    static {
        REQUEST_SPEC_MAP.put("pivotal", RequestSpecFactory::getRequestSpecPivotal);
        REQUEST_SPEC_MAP.put("trello", RequestSpecFactory::getRequestSpecTrello);
        REQUEST_SPEC_MAP.put("sfdc", RequestSpecFactory::getRequestSpecSFDC);
    }

    private RequestSpecFactory() {
    }

    private static RequestSpecification getRequestSpecPivotal() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getValue("pivotal.baseUri"))
                .addHeader("X-TrackerToken", Environment.getInstance().getValue("pivotal.credentials.owner.token"))
                .build();
        return requestSpecification
                .log().method()
                .log().uri()
                .log().params()
                .log().body();
    }

    private static RequestSpecification getRequestSpecTrello() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getValue("trello.baseUri"))
                .addQueryParam("key", Environment.getInstance().getValue("trello.credentials.owner.key"))
                .addQueryParam("token", Environment.getInstance().getValue("trello.credentials.owner.token"))
                .build();
        return requestSpecification
                .log().method()
                .log().uri()
                .log().params()
                .log().body();
    }

    private static RequestSpecification getRequestSpecSFDC() {
        Response response = RestAssured.given()
                .param("grant_type", "password")
                .param("client_id", Environment.getInstance().getValue("sfdc.credentials.admin.clientId"))
                .param("client_secret", Environment.getInstance().getValue("sfdc.credentials.admin.clientSecret"))
                .param("username", Environment.getInstance().getValue("sfdc.credentials.admin.userName"))
                .param("password", Environment.getInstance().getValue("sfdc.credentials.admin.password")
                        .concat(Environment.getInstance().getValue("sfdc.credentials.admin.securityToken")))
                .when()
                .post("https://login.salesforce.com/services/oauth2/token");

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(response.jsonPath().getString("instance_url").concat("/services/data/v39.0"))
                .addHeader("Authorization", "Bearer " + response.jsonPath().getString("access_token"))
                .build();
        return requestSpecification
                .log().method()
                .log().uri()
                .log().params()
                .log().body();
    }

    public static RequestSpecification getRequestSpec(final String serviceName) {
        return REQUEST_SPEC_MAP.get(serviceName).get();
    }

}
