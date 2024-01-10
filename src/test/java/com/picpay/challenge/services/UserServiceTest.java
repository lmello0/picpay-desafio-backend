package com.picpay.challenge.services;


import com.picpay.challenge.DTO.user.GetUserDTO;
import com.picpay.challenge.DTO.user.PostUserDTO;
import com.picpay.challenge.DTO.user.UpdateUserDTO;
import com.picpay.challenge.domain.user.Type;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import com.picpay.challenge.domain.user.exception.UserInactiveException;
import com.picpay.challenge.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a new user when user not exists")
    void testCreateUser() {
        String name = "Test";
        String document = "11122233300";
        String email = "email@email.com";
        String password = "123456";
        Type userType = Type.COMUM;

        PostUserDTO postUserDTO = new PostUserDTO(
                name,
                null,
                document,
                email,
                password,
                userType
        );

        when(passwordEncoder.encode(any())).thenReturn("123456");
        when(userRepository.save(any())).thenReturn(new User(postUserDTO));

        User createdUser = userService.createUser(postUserDTO);

        verify(userRepository, times(1)).save(any());

        assertNotNull(createdUser);

        assertEquals(name, createdUser.getFirstName());
        assertEquals(document, createdUser.getDocument());
        assertEquals(email, createdUser.getEmail());
        assertEquals(password, createdUser.getPassword());
        assertEquals(userType, createdUser.getType());
        assertEquals(0, createdUser.getWallet());

        assertTrue(createdUser.isActive());
    }

    @Test
    @DisplayName("Should get a user if it exists")
    void testFindOneUserExistsCase1() {
        User user = new User();
        user.setId("userId");

        when(userRepository.findById("userId")).thenReturn(Optional.of(user));

        User foundUser = userService.findOne("userId");

        assertNotNull(foundUser);
        assertEquals("userId", foundUser.getId());

        verify(userRepository, times(1)).findById("userId");
    }

    @Test
    @DisplayName("Should throw an error when user not exists")
    void testFindOneUserExistsCase2() {
        String userId = "userId";

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findOne(userId);

            verify(userRepository, times(1)).findById(userId);
        });
    }

    @Test
    @DisplayName("Should get only active users")
    void testFindAllActive() {
        Pageable pageable = Pageable.unpaged();
        PostUserDTO postUserDTO = new PostUserDTO(
                "Test",
                null,
                "11122233300",
                "email@email.com",
                "123456",
                Type.COMUM
        );

        Page<User> returnPage = createPage(List.of(
                new User(postUserDTO),
                new User(postUserDTO))
        );

        when(userRepository.findAllByActiveTrue(pageable)).
                thenReturn(returnPage);

        Page<GetUserDTO> result = userService.findAllActive(pageable);

        verify(userRepository, times(1)).findAllByActiveTrue(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Should successfully update a user")
    void testUpdateUser() {
        String userId = "testUserId";

        String firstName = "Test";
        String lastName = "Last Name";

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                firstName,
                lastName,
                null,
                null
        );

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.getReferenceById(userId)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userId, updateUserDTO);

        assertNotNull(updatedUser);

        verify(userRepository, times(1)).getReferenceById(userId);

        assertEquals(userId, updatedUser.getId());
        assertEquals("Test", updatedUser.getFirstName());
        assertEquals("Last Name", updatedUser.getLastName());
    }

    @Test
    @DisplayName("Should inactivate a active user")
    void testDeleteUserActiveUser() {
        // given
        String userId = "testUserId";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setActive(true);

        // when
        when(userRepository.getReferenceById(userId)).thenReturn(existingUser);

        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).getReferenceById(userId);

        assertFalse(existingUser.isActive());
    }

    @Test
    @DisplayName("Should throw a error when try to inactivate a inactivated user")
    void testDeleteUserInactiveUser() {
        String userId = "testUserId";

        User existingUser = new User();

        existingUser.setId(userId);
        existingUser.setActive(false);

        when(userRepository.getReferenceById(userId)).thenReturn(existingUser);

        assertThrows(UserInactiveException.class, () -> {
            userService.deleteUser(userId);

            verify(userRepository, times(1)).getReferenceById(userId);
        });
    }

    private Page<User> createPage(List<User> list) {
        PageRequest pageRequest = PageRequest.of(0, 2);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<User> pageContent = list.subList(start, end);

        return new PageImpl<User>(pageContent, pageRequest, list.size());
    }
}
