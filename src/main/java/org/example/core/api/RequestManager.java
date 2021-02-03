package org.example.core.api;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public final class RequestManager {

    private RequestManager() {
    }

    public static Response doRequest(final String httpMethod, final RequestSpecification requestSpecification,
                                     final String endpoint, final String jsonBody) {
        if ("POST".equalsIgnoreCase(httpMethod)) {
            return post(requestSpecification, endpoint, jsonBody);
        } else if ("PUT".equalsIgnoreCase(httpMethod)) {
            return put(requestSpecification, endpoint, jsonBody);
        }
        return patch(requestSpecification, endpoint, jsonBody);
    }

    public static Response doRequest(final String httpMethod, final RequestSpecification requestSpecification,
                                     final String endpoint, final Map<String, String> body) {
        if ("POST".equalsIgnoreCase(httpMethod)) {
            return post(requestSpecification, endpoint, body);
        } else if ("PUT".equalsIgnoreCase(httpMethod)) {
            return put(requestSpecification, endpoint, body);
        }
        return patch(requestSpecification, endpoint, body);
    }

    public static Response doRequest(final String httpMethod, final RequestSpecification requestSpecification,
                                     final String endpoint) {
        if ("GET".equalsIgnoreCase(httpMethod)) {
            return get(requestSpecification, endpoint);
        }
        return delete(requestSpecification, endpoint);
    }

    public static Response post(final RequestSpecification requestSpec, final String endpoint,
                                final String body) {
        final Response response = RestAssured.given(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response post(final RequestSpecification requestSpec, final String endpoint,
                                final Map<String, String> body) {
        final Response response = RestAssured.given(requestSpec)
                .params(body)
                .when()
                .post(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response put(final RequestSpecification requestSpec, final String endpoint,
                                final String body) {
        final Response response = RestAssured.given(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .put(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response put(final RequestSpecification requestSpec, final String endpoint,
                               final Map<String, String> body) {
        final Response response = RestAssured.given(requestSpec)
                .params(body)
                .when()
                .put(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response patch(final RequestSpecification requestSpec, final String endpoint,
                               final String body) {
        final Response response = RestAssured.given(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .patch(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response patch(final RequestSpecification requestSpec, final String endpoint,
                                 final Map<String, String> body) {
        final Response response = RestAssured.given(requestSpec)
                .params(body)
                .when()
                .patch(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response delete(final RequestSpecification requestSpec, final String endpoint) {
        final Response response = RestAssured.given(requestSpec)
                .when()
                .delete(endpoint);
        return getResponseWithLogger(response);
    }

    public static Response get(final RequestSpecification requestSpec, final String endpoint) {
        final Response response = RestAssured.given(requestSpec)
                .when()
                .get(endpoint);
        return getResponseWithLogger(response);
    }

    private static Response getResponseWithLogger(final Response response) {
        response.then()
                .log().status()
                .log().body();
        return response;
    }
}
