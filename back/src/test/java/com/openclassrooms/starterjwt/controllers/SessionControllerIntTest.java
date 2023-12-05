package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.sql.Date;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtUtils jwtUtils;
    private String jwt;

    @BeforeEach
    public void setup() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().username("yoga@studio.com").build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        jwt = jwtUtils.generateJwtToken(authentication);
    }


    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/api/session/5")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdBadRequest() throws Exception {
        mockMvc.perform(get("/api/session/notAnId")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/session/0")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/api/session")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("test");
        sessionDto.setDescription("test");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(Date.valueOf("2023-05-01"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SessionDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("updated");
        sessionDto.setDescription("updated session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(Date.valueOf("2023-05-01"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/5")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/0")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SessionDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/notAnId")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SessionDto())))
                .andExpect(status().isBadRequest());
    }

/*    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/4")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }*/

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/0")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/notAnId")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testParticipate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/5/participate/1")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    public void testParticipateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/0/participate/0")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testParticipateBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/notAnId/participate/notAnId")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNoLongerParticipate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/5/participate/3")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}
