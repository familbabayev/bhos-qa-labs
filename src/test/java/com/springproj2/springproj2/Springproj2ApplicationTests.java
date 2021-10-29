package com.springproj2.springproj2;

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

import javax.net.ssl.HttpsURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springproj2ApplicationTests {

	public String url = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos";

	@Test
	void test1() throws CertificateException, IOException {
		URL url = new URL(this.url);

		URLConnection con = url.openConnection();
		HttpsURLConnection securecon = (HttpsURLConnection) con;
		securecon.connect();

		Certificate[] certs = securecon.getServerCertificates();

		FileInputStream fis = new FileInputStream("mockapicert.cer");

		CertificateFactory fac = CertificateFactory.getInstance("X509");
		X509Certificate cert = (X509Certificate) fac.generateCertificate(fis);

		assertEquals(cert, certs[0]);
	}

	@Test
	void test2() throws JSONException {
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(this.url, HttpMethod.GET, entity, String.class);

		JSONArray repoList = new JSONArray(response.getBody());

		Set<String> ids = new HashSet<>();
		Set<String> names = new HashSet<>();

		for (int i = 0; i < repoList.length(); i++) {
			JSONObject item = (JSONObject) repoList.get(i);

			ids.add(item.getString("id"));
			names.add(item.getString("name"));
		}

		boolean isUnique;
		if ( ids.size() == repoList.length() && names.size() == repoList.length() ) {
			isUnique = true;
		} else {
			isUnique = false;
		}

		assertEquals(true, isUnique);
	}


}
