package com.openclassrooms.starterjwt.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void create() {
        // Arrange
        Session session = new Session();
        session.setId(9L);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session createdSession = sessionService.create(session);

        // Assert
        assertEquals(session.getId(), createdSession.getId());
    }

    @Test
    public void delete() {
        // Arrange
        Session session = new Session();
        session.setId(1L);
        doNothing().when(sessionRepository).deleteById(anyLong());

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.delete(session.getId());

        // Assert
        verify(sessionRepository, times(1)).deleteById(session.getId());
    }

    @Test
    public void findAllSessions() {
        // Arrange
        List<Session> sessions = new ArrayList<>();
        Session session1 = new Session();
        session1.setId(2L);
        Session session2 = new Session();
        session2.setId(3L);
        sessions.add(session1);
        sessions.add(session2);
        when(sessionRepository.findAll()).thenReturn(sessions);

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        List<Session> sessionList = sessionService.findAll();

        // Assert
        assertEquals(2, sessionList.size());
    }

    @Test
    public void getById() {
        // Arrange
        Long id = 1L;
        Session session = new Session();
        session.setId(id);
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session sessionFound = sessionService.getById(id);

        // Assert
        assertEquals(sessionFound.getId(), session.getId());
    }

    @Test
    public void update() {
        // Arrange
        Long id = 3L;
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session updatedSession = sessionService.update(id, session);

        // Assert
        assertEquals(id, updatedSession.getId());
    }

    @Test
    public void participate() {

        // Arrange
        Long id = 1L;
        Long userId = 9L;
        Session session = Session.builder().name("Session Test").id(id).users(new ArrayList<>()).build();
        User user = User.builder().id(userId).email("yoga@studio.com").lastName("Paul").firstName("Marniquet")
                .password("password").build();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act

        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.participate(id, userId);

        // Assert

        assertEquals(session.getUsers().size(), 1);
        assertEquals(session.getUsers().get(0).getId(), userId);
    }

    @Test
    public void noLongerParticipate() {

        // Arrange
        Long id = 1L;
        Long userId = 9L;
        Session session = Session.builder().name("Session Test").id(id).users(new ArrayList<>()).build();
        User user = User.builder().id(userId).email("yoga@studio.com").lastName("Paul").firstName("Marniquet")
                .password("password").build();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        session.getUsers().add(user);

        // Act
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.noLongerParticipate(id, userId);

        // Assert
        assertEquals(session.getUsers().size(), 0);
    }
}