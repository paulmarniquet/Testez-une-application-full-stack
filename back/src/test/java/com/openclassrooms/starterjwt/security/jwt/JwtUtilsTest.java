package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class JwtUtilsTest {


    @Autowired
    JwtUtils jwtUtils;

    @Test
    public void generateJwtTokenTest() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        // Act
        String token = jwtUtils.generateJwtToken(authentication);

        // Assert
        assertFalse(token.isEmpty());
        assertEquals("Paulo", jwtUtils.getUserNameFromJwtToken(token));
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void getUserNameFromJwtTokenTest() {
        // Arrange
        String username = "username";
        UserDetails userDetails = new UserDetailsImpl(
                null,
                username,
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        // Act
        String token = jwtUtils.generateJwtToken(authentication);
        String usernameFromToken = jwtUtils.getUserNameFromJwtToken(token);

        // Assert
        assertEquals(username, usernameFromToken);
    }

    @Test
    public void validateJwtTokenTest() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void invalidToken() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        String invalidToken = token + "invalid";
        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void expiredToken() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 0);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void malformedToken() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        String malformedToken = token.substring(0, 10);
        boolean isValid = jwtUtils.validateJwtToken(malformedToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void emptyToken() {
        // Arrange
        UserDetails userDetails = new UserDetailsImpl(
                null,
                "Paulo",
                "Paul",
                "Marniquet",
                null,
                "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        String emptyToken = "";
        boolean isValid = jwtUtils.validateJwtToken(emptyToken);

        // Assert
        assertFalse(isValid);
    }
}
