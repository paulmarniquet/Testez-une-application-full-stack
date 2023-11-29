package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Test
    @Transactional
    public void loadUserByUsername() {

        // Arrange
        String username = "Paul";
        User user = User.builder()
                .id(1L)
                .email("paul.marniquet@gmail.com")
                .firstName("Paul")
                .lastName("Marniquet")
                .password("password").build();

        when(userRepository.findByEmail(username)).thenReturn(Optional.ofNullable(user));

        // Act
        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;

        // Assert
        assert userDetailsImpl.getId().equals(user.getId());
    }
}
