package com.picpay.challenge.services;

import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should success if the user exists")
    void loadUserByUsername() {
        String userId = "userId";
        String email = "email@email.com";

        User user = new User();
        user.setId(userId);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDetails returnedUser = authService.loadUserByUsername(email);

        assertEquals(user, returnedUser);
    }
}