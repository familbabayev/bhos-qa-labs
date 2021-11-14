package com.springprog5.springprog5;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springprog5ApplicationTests {

	String FIREBASE_SIGNIN = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
	String WEB_API_KEY = System.getenv("FIREBASE_WEB_API_KEY");
	String FIRESTORE_USERS = "https://firestore.googleapis.com/v1/projects/springproj3/databases/(default)/documents/users2/";

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	void test1() throws JSONException {
		HttpHeaders headers = new HttpHeaders();

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", "bobweir@gmail.com");
		jsonObj.put("password", "bobweir");
		jsonObj.put("returnSecureToken", true);

		HttpEntity<String> entity = new HttpEntity<>(jsonObj.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange( FIREBASE_SIGNIN + WEB_API_KEY, HttpMethod.POST, entity, String.class);

		JSONObject resBody = new JSONObject(response.getBody());
		String idToken = resBody.getString("idToken");
		String localId = resBody.getString("localId");


		HttpHeaders headers2 = new HttpHeaders();
		headers2.put("Authorization", Collections.singletonList("Bearer ".concat(idToken)));

		HttpEntity<String> entity2 = new HttpEntity<>(null, headers2);
		ResponseEntity<String> response2 = restTemplate.exchange(FIRESTORE_USERS + localId, HttpMethod.GET, entity2, String.class);


		JSONObject resBody2 = new JSONObject(response2.getBody());
		JSONObject fields = resBody2.getJSONObject("fields");

		JSONObject name = fields.getJSONObject("name");
		JSONObject surname = fields.getJSONObject("surname");
		JSONObject phone = fields.getJSONObject("phone");


		assertEquals("Bob", name.getString("stringValue"));
		assertEquals("Weir", surname.getString("stringValue"));
		assertEquals("123123123", phone.getString("stringValue"));
	}

}
