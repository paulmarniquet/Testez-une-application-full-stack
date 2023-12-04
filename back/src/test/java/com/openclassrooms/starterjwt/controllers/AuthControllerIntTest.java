package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testLoginSuccessfull() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk());
    }

    @Test
    public void testLoginFailed() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("BADPASSWORD");

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterSuccessfull() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("william@gmail.com");
        signupRequest.setFirstName("Damso");
        signupRequest.setLastName("William");
        signupRequest.setPassword("demsdems");

        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest))).andExpect(status().isOk());
    }

    @Test
    public void accountAlreadyExisting() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@studio.com");
        signupRequest.setFirstName("Damso");
        signupRequest.setLastName("William");
        signupRequest.setPassword("demsdems");

        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest))).andExpect(status().isBadRequest());
    }
}
