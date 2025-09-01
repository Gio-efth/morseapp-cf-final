package gr.aueb.cf.morseapp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();
        setPrivate(jwtService, "secret",
                "THIS_IS_A_VERY_LONG_TEST_SECRET_KEY_32+_BYTES");
        setPrivate(jwtService, "expirationMs", 2000L); // 2s
    }

    @Test
    void generate_and_validate_token_ok() {
        User user = new User("morseUser", "pwd", Collections.emptyList());
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertEquals("morseUser", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void tampered_token_throws_on_parse() {
        User user = new User("morseUser", "pwd", Collections.emptyList());
        String token = jwtService.generateToken(user);

        String tampered = token.substring(0, token.length() - 2) + "xx";
        assertThrows(JwtException.class, () -> jwtService.extractUsername(tampered));
    }

    @Test
    void expired_token_throws_on_validate() throws Exception {
        User user = new User("morseUser", "pwd", Collections.emptyList());
        setPrivate(jwtService, "expirationMs", 0L);
        String token = jwtService.generateToken(user);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(token, user));
    }

    private static void setPrivate(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
