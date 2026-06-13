package controller;

import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.Totp;

public class OTPGenerator {

    private String secret;

    public OTPGenerator() {
        this.secret = Base32.random();
    }

    public String generateOTP() {
        Totp totp = new Totp(secret);
        return totp.now();
    }

    public boolean verifyOTP(String otp) {
        Totp totp = new Totp(secret);
        return totp.verify(otp);
    }

    public String getSecret() {
        return secret;
    }
}


