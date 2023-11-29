package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        // Arrange
        userDto = new UserDto();
        userDto.setEmail("email@gmail.com");
        userDto.setLastName("Marniquet");
        userDto.setFirstName("Paul");
        userDto.setPassword("password");
        userDto.setId(1L);

        user = new User();
        user.setEmail("email@gmail.com");
        user.setLastName("Marniquet");
        user.setFirstName("Paul");
        user.setPassword("password");
        user.setId(1L);
    }
    
    @Test
    public void testToEntity() {
        // Act
        User userEntity = userMapper.toEntity(userDto);

        // Assert
        assertEquals(user, userEntity);
    }

    @Test
    public void testToDto() {
        // Act
        UserDto userDtoEntity = userMapper.toDto(user);

        // Assert
        assertEquals(userDto, userDtoEntity);
    }

    @Test
    public void testToDtoList() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        // Act
        List<UserDto> userDtoListMapped = userMapper.toDto(userList);

        // Assert
        assertEquals(userDtoList, userDtoListMapped);
    }

    @Test
    public void testToEntityList() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        // Act
        List<User> userListMapped = userMapper.toEntity(userDtoList);

        // Assert
        assertEquals(userList, userListMapped);
    }
}

