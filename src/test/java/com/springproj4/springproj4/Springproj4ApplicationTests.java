package com.springproj4.springproj4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springproj4ApplicationTests {

	@Test
	void test() throws JSONException {

		String url = "https://api.nytimes.com/svc/books/v3/lists.json?list=" +
				"Combined%20Print%20and%20E-Book%20Nonfiction&api-key=TyUjATDGfj4rcmGaM73J2XiYJLYkRhmD";

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		JSONObject jsonObj = new JSONObject(response.getBody());
		JSONArray books = jsonObj.getJSONArray("results");

		int counter = 1; boolean isComplete = true;
		for ( int i = 0; i < books.length(); i++ ) {
			JSONObject book = (JSONObject) books.get(i);

			JSONArray bookDetailsArr = book.getJSONArray("book_details");
			JSONObject bookDetails = (JSONObject) bookDetailsArr.get(0);

			int bookRank = (int) book.get("rank");
			String bookTitle = bookDetails.getString("title");

			if ( bookTitle.isEmpty() || bookRank != counter ) {
				isComplete = false;
				break;
			}
		}

		assertEquals(true, isComplete);
	}

}
