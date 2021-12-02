package com.springprog8.springprog8;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}