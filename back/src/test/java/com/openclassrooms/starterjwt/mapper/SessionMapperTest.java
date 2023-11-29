package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SessionMapperTest {

    private final SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);
    private SessionDto sessionDto;
    private Session session;

    @BeforeEach
    public void setUp() {
        // Arrange
        sessionDto = new SessionDto();
        sessionDto.setDescription("description");
        sessionDto.setName("New session");
        sessionDto.setUsers(new ArrayList<>());

        session = new Session();
        session.setDescription("description");
        session.setName("New session");
        session.setUsers(new ArrayList<>());
    }

    @Test
    public void testToEntity() {
        // Act
        Session sessionEntity = sessionMapper.toEntity(sessionDto);

        // Assert
        assertEquals(session, sessionEntity);
    }

    @Test
    public void testToDto() {
        // Act
        SessionDto sessionDtoEntity = sessionMapper.toDto(session);

        // Assert
        assertEquals(sessionDto, sessionDtoEntity);
    }

    @Test
    public void testToDtoList() {
        // Arrange
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        List<SessionDto> sessionDtoList = new ArrayList<>();
        sessionDtoList.add(sessionDto);

        // Act
        List<SessionDto> sessionDtoListMapped = sessionMapper.toDto(sessionList);

        // Assert
        assertEquals(sessionDtoList, sessionDtoListMapped);
    }

    @Test
    public void testToEntityList() {
        // Arrange
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        List<SessionDto> sessionDtoList = new ArrayList<>();
        sessionDtoList.add(sessionDto);

        // Act
        List<Session> sessionListMapped = sessionMapper.toEntity(sessionDtoList);

        // Assert
        assertEquals(sessionList, sessionListMapped);
    }
}
