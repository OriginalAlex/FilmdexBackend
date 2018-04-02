package io.github.originalalex.filmdex.utils.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TokenUtils {

    private static final String SECRET = "sldkyfgq3oih sdkhgbsadkfhbsadf328qy87sdaf as830"; // not easily brute forced!
    private static Map<String, Object> defaultTokenHeader;
    private static JWTVerifier verifier;

    static {
        defaultTokenHeader = new HashMap<>();
        defaultTokenHeader.put("alg", "HS256");
        defaultTokenHeader.put("typ", "JWT");
        try {
            verifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer("auth0")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String issueUserToken(long userId, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withIssuer("auth0")
                    .withHeader(defaultTokenHeader)
                    .withClaim("userId", userId)
                    .withClaim("role", role)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DecodedJWT decodeToken(String token) {
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
