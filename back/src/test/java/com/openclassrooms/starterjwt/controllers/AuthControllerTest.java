package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;


    @Test
    public void authenticateUser() {

        // Arrange
        Long id = 1L;
        String email = "paulzer@gmail.com";
        String password = "password";
        String firstname = "Paul";
        String lastname = "Marniquet";
        boolean isAdmin = false;

        UserDetailsImpl user = UserDetailsImpl.builder().username(email).firstName(firstname).lastName(lastname)
                .id(id).password(password).build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().id(id).email(email)
                .password(password).firstName(firstname).lastName(lastname).admin(isAdmin).build()));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        // Act
        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils,
                userRepository);
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
        JwtResponse responseBody = (JwtResponse) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(email, responseBody.getUsername());
        assertEquals(firstname, responseBody.getFirstName());
        assertEquals(lastname, responseBody.getLastName());
        assertEquals(id, responseBody.getId());
        assertEquals(isAdmin, responseBody.getAdmin());
        assertEquals("Bearer", responseBody.getType());
        assertNotNull(responseBody.getToken());
    }

    @Test
    public void registerUser() {

        // Arrange
        String email = "paul@newmember.com";
        String password = "password";
        String firstname = "Paul";
        String lastname = "Marniquet";
        boolean isAdmin = false;

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(User.builder().email(email).password("encodedPassword").firstName(firstname)
                .lastName(lastname).admin(isAdmin).build())).thenReturn(User.builder().email(email)
                .password("encodedPassword").firstName(firstname).lastName(lastname).admin(isAdmin).build());

        // Act
        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils,
                userRepository);
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstname);
        signupRequest.setLastName(lastname);
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void registerButUserAlreadyExists() {

        // Arrange
        String email = "paul@newmember.com";
        String password = "password";
        String firstname = "Paul";
        String lastname = "Marniquet";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act
        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils,
                userRepository);
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstname);
        signupRequest.setLastName(lastname);
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
