package io.obouh.backend.xpensetrackr.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public final class JwtTokenUtil {

    // In a real scenario, store this securely (e.g. in Vault, environment variable, config server, etc.)
    private final String JWT_SECRET = "secret-key";
    // for demonstration, 30 minutes
    private final long JWT_EXPIRATION = 30 * 60 * 1000;

    private JwtTokenUtil(){

    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // log token parsing error
            return false;
        }
    }
}

