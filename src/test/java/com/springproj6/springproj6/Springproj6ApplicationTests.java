package com.springproj6.springproj6;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springproj6ApplicationTests {

	String FIREBASE_SIGNIN = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
	String WEB_API_KEY = System.getenv("FIREBASE_WEB_API_KEY");
	String FIRESTORE_USERS = "https://firestore.googleapis.com/v1/projects/springproj3/databases/(default)/documents/users2";
	String FIREBASE_STORAGE = "https://firebasestorage.googleapis.com/v0/b/springproj3.appspot.com/o";

	@Test
	public void test() throws IOException, InterruptedException, JSONException {
		JSONObject authData = new JSONObject();
		authData.put("email", "bobweir@gmail.com");
		authData.put("password", "bobweir");
		authData.put("returnSecureToken", "true");
		String authRes = UtilityFunctions.post(FIREBASE_SIGNIN + WEB_API_KEY, authData);

		JSONObject authResJson = new JSONObject(authRes);
		String idToken = authResJson.getString("idToken");
		String userId = authResJson.getString("localId");

		String uploadUrl = String.format("%s/%s%%2F%sc%s", FIREBASE_STORAGE, userId, "avatar.jpg", WEB_API_KEY);
		String uploadedFile = UtilityFunctions.uploadFile(uploadUrl, idToken, "avatar.jpg");
		String fileReference = new JSONObject(uploadedFile).getString("name");

		String avatarFieldUrl = String.format("%s/%s?updateMask.fieldPaths=avatar", FIRESTORE_USERS, userId);
		JSONObject avatarData = new JSONObject();
		JSONObject fieldsObject = new JSONObject();
		JSONObject avatarObject = new JSONObject();
		avatarObject.put("stringValue", fileReference);
		fieldsObject.put("avatar", avatarObject);
		avatarData.put("fields", fieldsObject);

		UtilityFunctions.patch(avatarFieldUrl, idToken, avatarData.toString());

		String usersUrl = String.format("%s/%s", FIRESTORE_USERS, userId);
		String userDetails = UtilityFunctions.get(usersUrl, idToken);
		String avatarReference = new JSONObject(userDetails).getJSONObject("fields").getJSONObject("avatar").getString("stringValue");
		String avatarReferenceUrlEncoded = URLEncoder.encode(avatarReference, StandardCharsets.UTF_8.toString());

		String url = String.format("%s/%s?alt=media&token=%s", FIREBASE_STORAGE, avatarReferenceUrlEncoded, WEB_API_KEY);

		assertEquals(200, UtilityFunctions.check(url, idToken));
	}

}
