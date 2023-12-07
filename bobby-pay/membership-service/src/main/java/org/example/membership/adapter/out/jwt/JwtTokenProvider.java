package org.example.membership.adapter.out.jwt;

import io.jsonwebtoken.*;
import org.example.membership.application.port.out.AuthMembershipPort;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider implements AuthMembershipPort {

    private final static String SECRET_KEY = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
    private final static long ACCESS_TOKEN_EXPIRATION_IN_MS = 1000L * 20;
    private final static long REFRESH_TOKEN_EXPIRATION_IN_MS = 1000L * 60;

    @Override
    public String generateAccessToken(Long membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_IN_MS);

        return Jwts.builder()
                .setSubject(String.valueOf(membershipId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateRefreshToken(Long membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_IN_MS);

        return Jwts.builder()
                .setSubject(String.valueOf(membershipId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            // Invalid JWT token: 유효하지 않은 JWT 토큰일 때 발생하는 예외
        } catch (ExpiredJwtException ex) {
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token: 지원하지 않는 JWT 토큰일 때 발생하는 예외
        } catch (IllegalArgumentException ex) {
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
        }
        return false;
    }

    @Override
    public Long parseMembershipIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
