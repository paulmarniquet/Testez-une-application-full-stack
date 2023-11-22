package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Test
    public void findById() {

        // Arrange
        Long id = 2L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));

        // Act
        UserService userService = new UserService(userRepository);
        User foundUser = userService.findById(id);

        // Assert
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void delete() {
        // Arrange
        User user = new User();
        user.setId(1L);
        doNothing().when(userRepository).deleteById(anyLong());

        // Act
        UserService userService = new UserService(userRepository);
        userService.delete(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

}