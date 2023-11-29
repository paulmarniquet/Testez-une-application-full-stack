package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDetailsImplTest {

    @Test
    public void isAccountNonExpiredTest() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertTrue(userDetailsImpl.isEnabled());
    }

    @Test
    public void getAuthorities() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertEquals(userDetailsImpl.getAuthorities(), new HashSet<GrantedAuthority>());
    }

    @Test
    public void equals() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertEquals(userDetailsImpl, userDetailsImpl);
    }

    @Test
    public void equalsNull() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertFalse(userDetailsImpl == null);
    }

    @Test
    public void equalsOtherObject() {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().build();
        assertFalse(userDetailsImpl.equals(new Object()));
    }
}