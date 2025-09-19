package sto.study_plaza.unit.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sto.study_plaza.config.props.JwtProperties;
import sto.study_plaza.util.JwtUtil;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecretKey("12345678901234567890123456789012"); // 32바이트 이상
        jwtProperties.setExpirationTime(1000L * 60 * 60); // 1시간

        jwtUtil = new JwtUtil(jwtProperties);
        jwtUtil.init();
    }

    @Test
    void generateAndValidateToken_Success() {
        // given
        String username = "testUser";
        UUID memberId = UUID.randomUUID();

        // when
        String token = jwtUtil.generateToken(username, memberId);
        String extractedUsername = jwtUtil.validateToken(token);

        // then
        assertNotNull(token);
        assertEquals(username, extractedUsername);

        Claims claims = jwtUtil.getClaimsFromToken(token);
        assertEquals(username, claims.getSubject());
        assertEquals(memberId.toString(), claims.get("memberId"));
    }

    @Test
    void getMemberIdFromToken_ShouldReturnUUID() {
        // given
        UUID memberId = UUID.randomUUID();
        String token = jwtUtil.generateToken("user", memberId);

        // when
        UUID extractedId = jwtUtil.getMemberIdFromToken(token);

        // then
        assertEquals(memberId, extractedId);
    }

    @Test
    void validateToken_InvalidToken_ShouldThrowException() {
        // given
        String invalidToken = "abc.def.ghi";

        // when & then
        assertThrows(RuntimeException.class, () -> jwtUtil.validateToken(invalidToken));
    }
}
