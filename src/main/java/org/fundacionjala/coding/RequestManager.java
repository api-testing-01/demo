package org.fundacionjala.coding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestManager {

    public static Response post(final RequestSpecification requestSpec, final String endpoint,
                                final String body) {
        final Response response = RestAssured.given(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post(endpoint);
        System.out.println("***POST");
        System.out.println(response.prettyPrint());
        return response;
    }

    public static Response put(final RequestSpecification requestSpec, final String endpoint,
                                final String body) {
        final Response response = RestAssured.given(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .put(endpoint);
        System.out.println("***PUT");
        System.out.println(response.prettyPrint());
        return response;
    }

    public static Response delete(final RequestSpecification requestSpec, final String endpoint) {
        final Response response = RestAssured.given(requestSpec)
                .when()
                .delete(endpoint);
        System.out.println("***DELETE");
        System.out.println(response.prettyPrint());
        return response;
    }

    public static Response get(final RequestSpecification requestSpec, final String endpoint) {
        final Response response = RestAssured.given(requestSpec)
                .when()
                .get(endpoint);
        System.out.println("***GET");
        System.out.println(response.prettyPrint());
        return response;
    }
}
