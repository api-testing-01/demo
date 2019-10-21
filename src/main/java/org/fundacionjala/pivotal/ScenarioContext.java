package org.fundacionjala.pivotal;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class ScenarioContext {

	private Map<String, Response> map;

	public ScenarioContext() {
		map = new HashMap<>();
	}

	public Response get(String key) {
		return map.get(key);
	}

	public void set(String key, Response response) {
		map.put(key, response);
	}
}
