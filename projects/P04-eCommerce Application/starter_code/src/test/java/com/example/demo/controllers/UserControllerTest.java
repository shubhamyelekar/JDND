package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_positive() {

        when(encoder.encode("test1234")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("test1234");
        createUserRequest.setConfirmPassword("test1234");

        ResponseEntity<User> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);

        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void should_find_user_by_id() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        when(userRepository.findById((long) 0)).thenReturn(java.util.Optional.ofNullable(user));
        ResponseEntity<User> byId = userController.findById((long) 0);
        assertEquals(200, byId.getStatusCodeValue());
    }

    @Test
    public void should_find_by_username() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        when(userRepository.findByUsername("test")).thenReturn(user);
        final ResponseEntity<User> byUserName = userController.findByUserName("test");
        assertEquals(200, byUserName.getStatusCodeValue());
    }
}
