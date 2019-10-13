package org.fundacionjala.coding;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    private static final Map<String, Supplier<RequestSpecification>> REQUEST_SPEC_MAP = new HashMap<>();
    static {
        REQUEST_SPEC_MAP.put("pivotal", RequestSpecFactory::getRequestSpec);
//        REQUEST_SPEC_MAP.put("trello", RequestSpecFactory::getRequestSpecTrello);
    }

    private static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getValue("baseUri"))
                .addHeader("X-TrackerToken", Environment.getInstance().getValue("credentials.owner.token"))
                .build();
    }

//    private static RequestSpecification getRequestSpecTrello() {
//        return new RequestSpecBuilder()
//                .setBaseUri(Environment.getInstance().getValue("baseUri"))
//                .addHeader("Token", Environment.getInstance().getValue("credentials.owner.token"))
//                .build();
//    }

    public static RequestSpecification getRequestSpec(final String serviceName) {
        return REQUEST_SPEC_MAP.get(serviceName).get();
    }

}