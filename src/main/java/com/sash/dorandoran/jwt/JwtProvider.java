package com.sash.dorandoran.jwt;

import com.sash.dorandoran.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtResponse generateToken(User user) {

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("authProvider", user.getAuthProvider())
                .claim("auth", user.getRole())
                .setExpiration(new Date(now + 1800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setExpiration(new Date(now + 604800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
//            throw InvalidTokenException.EXCEPTION;
        } catch (ExpiredJwtException e) {
//            throw ExpiredTokenException.EXCEPTION;
        } catch (UnsupportedJwtException e) {
//            throw new GeneralException(ErrorStatus.TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
//            throw new GeneralException(ErrorStatus.TOKEN_CLAIMS_EMPTY);
        }
        return true;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}