package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SessionControllerTest {

    @Mock
    SessionMapper sessionMapper;
    @Mock
    SessionService sessionService;

    @Test
    public void getById() {
        // Arrange
        Long id = 1L;
        Session session = new Session();
        session.setId(id);
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(id);
        when(sessionService.getById(id)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // Act
        SessionController sessionController = new SessionController(sessionService, sessionMapper);
        ResponseEntity<?> response = sessionController.findById(id.toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), sessionDto);
    }

    @Test
    public void getAll() {
        // Arrange
        List<Session> session = new ArrayList<>();
        Session session1 = new Session();
        session1.setName("SessionUno");
        session.add(session1);

        List<SessionDto> sessionDto = new ArrayList<>();
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setName("SessionUno");
        sessionDto.add(sessionDto1);

        when(sessionService.findAll()).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // Act
        SessionController sessionController = new SessionController(sessionService, sessionMapper);
        ResponseEntity<?> response = sessionController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), sessionDto);
    }

    @Test
    public void create() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("SessionUno");
        Session session = new Session();
        session.setName("SessionUno");
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // Act
        SessionController sessionController = new SessionController(sessionService, sessionMapper);
        ResponseEntity<?> response = sessionController.create(sessionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), sessionDto);
    }

    @Test
    public void update() {
        // Arrange
        Long id = 1L;
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("SessionUno");
        Session session = new Session();
        session.setName("SessionUno");
        when(sessionService.update(id, session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // Act
        SessionController sessionController = new SessionController(sessionService, sessionMapper);
        ResponseEntity<?> response = sessionController.update(id.toString(), sessionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), sessionDto);
    }

    @Test
    public void delete() {
        // Arrange
/*        Long id = 1L;
        Session session = new Session();

        when(sessionService.getById(id)).thenReturn(session);

        // Act
        SessionController sessionController = new SessionController(sessionService, sessionMapper);
        Session sessionFound = sessionService.getById(id);
        sessionService.delete(id);

        // Assert
        verify(sessionService).getById(id);
        verify(sessionService).delete(id);*/
    }
}
