package sto.study_plaza.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sto.study_plaza.config.props.JwtProperties;

import java.security.Key;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final Key key;

    @Autowired
    public JwtUtil(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public String generateToken(String userName, UUID memberId) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration expirationDuration = Duration.ofMillis(jwtProperties.getExpirationTime());
        Date issuedAt = Date.from(now.toInstant());
        ZonedDateTime expirationZonedDateTime = now.plus(expirationDuration);
        Date expiration = Date.from(expirationZonedDateTime.toInstant());

        return Jwts.builder()
                .setSubject(userName)
                .claim("memberId", memberId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (JwtException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UUID getMemberIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Object memberIdObj = claims.get("memberId");

        if (memberIdObj instanceof UUID) {
            // 이미 UUID 객체로 들어온 경우
            return (UUID) memberIdObj;
        } else if (memberIdObj instanceof String) {
            // 문자열 형태인 경우
            return UUID.fromString((String) memberIdObj);
        }
        throw new IllegalArgumentException("Invalid memberId type in token");
    }
}
