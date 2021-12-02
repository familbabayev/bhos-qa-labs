package com.springprog8.springprog8;

import com.nimbusds.srp6.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

@RestController
public class Service {

    static String salt = "4375690236459345";
    static String verifier = "40410091193732835514331284577916509186278450408915431268416033839937158822775" +
            "21454902090487629502998609843231480079299896404786048219361200503321759336259";

    public static SRP6CryptoParams config;
    public static SRP6ServerSession serverSession;

    @PostMapping("/new_session")
    public String new_session(@RequestBody String userID) {

        config = SRP6CryptoParams.getInstance();
        serverSession = new SRP6ServerSession(config);

        BigInteger saltS = new BigInteger(salt);
        BigInteger verifierV = new BigInteger(verifier);
        String serverPublicValue_B = serverSession.step1(userID, saltS, verifierV).toString();

        return serverPublicValue_B;
    }

    @PostMapping("/compute_values")
    public String compute_values(@RequestBody CompValReqBody compValReqBody) {

        try{
            return serverSession.step2(compValReqBody.A, compValReqBody.M1).toString();
        } catch (SRP6Exception e) {
            return "";
        }

    }

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/srpLoadTest")
    public ResponseEntity<Object> srpLoadTest() throws SRP6Exception {
        // New client session
        SRP6ClientSession Client = new SRP6ClientSession();
        String userID = "famil";
        String password = ";;01?property?DESTROY?build?96;;";
        Client.step1(userID, password);

        HttpEntity<String> newSesEntity = new HttpEntity<>(userID, headers);
        String newSesResponse = restTemplate.exchange("http://localhost:8080/new_session",
                HttpMethod.POST, newSesEntity, String.class).getBody();

        BigInteger serverPublicB = new BigInteger(newSesResponse);

        // Compute values
        SRP6CryptoParams defaultCryptoParams = SRP6CryptoParams.getInstance();
        BigInteger saltS = new BigInteger(salt);
        SRP6ClientCredentials clientPublicAEvidenceM1 = Client.step2(defaultCryptoParams, saltS, serverPublicB);

        CompValReqBody compValBody = new CompValReqBody(clientPublicAEvidenceM1.A, clientPublicAEvidenceM1.M1);
        HttpEntity<CompValReqBody> compValEntity = new HttpEntity<>(compValBody, headers);

        String serverEvidenceM2 = restTemplate.postForEntity("http://localhost:8080/compute_values",
                compValEntity, String.class).getBody();


        if (serverEvidenceM2 == "") {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(serverEvidenceM2);
        }
    }

}