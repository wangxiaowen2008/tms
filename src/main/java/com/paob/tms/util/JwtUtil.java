package com.paob.tms.util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

@Component
public class JwtUtil {
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 10;
    public static final String JWT_KEY = "SDF6jhdsafsdhfFdsJkdsfds121232131afasdfac";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSha256");
    }

//    public static Claims parseJWT(String jwt) throws Exception {
//        SecretKey secretKey = generalKey();
//        return Jwts.parserBuilder()
//               .verifyWith(secretKey)
//               .build()
//               .parseSignedClaims(jwt)
//               .getPayload();
//    }
}