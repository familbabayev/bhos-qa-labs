package com.springproj7.springproj7;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class Springproj7ApplicationTests {

	String FIREBASE_SIGNIN = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
	String WEB_API_KEY = System.getenv("FIREBASE_WEB_API_KEY");
	String FIRESTORE_USERS = "https://firestore.googleapis.com/v1/projects/springproj3/databases/(default)/documents/users2";

	@Test
	public void test1() throws IOException, InterruptedException, JSONException {
		JSONObject authData = new JSONObject();

		authData.put("email", "bobweir@gmail.com");
		authData.put("password", "bobweir");
		authData.put("returnSecureToken", "true");

		String authRes = UtilityFunctions.post(FIREBASE_SIGNIN + WEB_API_KEY, authData);

		JSONObject authResJson = new JSONObject(authRes);
		String idToken = authResJson.getString("idToken");
		String userId = authResJson.getString("localId");

		String userAvatarUrl = String.format("%s/%s?updateMask.fieldPaths=avatar", FIRESTORE_USERS, userId);

		JSONObject data = new JSONObject();
		JSONObject fieldsObject = new JSONObject();
		JSONObject avatarObject = new JSONObject();

		avatarObject.put("stringValue", "");
		fieldsObject.put("avatar", avatarObject);
		data.put("fields", fieldsObject);

		UtilityFunctions.patch(userAvatarUrl, idToken, data.toString());

		String userUrl = String.format("%s/%s", FIRESTORE_USERS, userId);
		String userDetails = UtilityFunctions.get(userUrl, idToken);
		String avatarLink = new JSONObject(userDetails).getJSONObject("fields").getJSONObject("avatar").getString("stringValue");
		if (!avatarLink.isEmpty()) fail();

		idToken = "";

		String userDetailsLoggedOut = UtilityFunctions.get(userUrl, idToken);

		assertEquals("Forbidden", userDetailsLoggedOut);
	}

}