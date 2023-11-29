package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    UserMapper userMapper;

    @Mock
    UserService userService;

    @Test
    public void getById() {
        // Arrange
        Long id = 1L;
        User user = new User();
        user.setId(id);
        UserDto userDto = new UserDto();
        userDto.setId(id);
        when(userService.findById(id)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.findById(id.toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), userDto);
    }

    @Test
    public void getByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(userService.findById(id)).thenReturn(null);

        // Act
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.findById(id.toString());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {
        // Arrange
        Long id = 1L;
        String email = "lol@gmail.com";
        String lastname = "Marniquet";
        String firstname = "Paul";
        String password = "password";
        User user = User.builder().id(id).email(email).lastName(lastname).firstName(firstname).password(password).build();

        UserDetailsImpl userDetails = UserDetailsImpl.builder().id(id).username(email).firstName(firstname)
                .lastName(lastname).password(password).admin(true).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userService.findById(id)).thenReturn(user);
        doNothing().when(userService).delete(id);

        // Act
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.save(id.toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).findById(id);
        verify(userService).delete(id);
    }

    @Test
    public void saveNotFound() {
        // Arrange
        Long id = 1L;
        when(userService.findById(id)).thenReturn(null);

        // Act
        UserController userController = new UserController(userService, userMapper);
        ResponseEntity<?> response = userController.save(id.toString());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService).findById(id);
        verify(userService, never()).delete(id);
    }
}