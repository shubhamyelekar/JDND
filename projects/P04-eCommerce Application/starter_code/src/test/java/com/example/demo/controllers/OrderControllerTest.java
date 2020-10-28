package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void should_submit_order() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        Cart cart = new Cart();
        cart.setId((long) 10);
        cart.setTotal(BigDecimal.valueOf(100));
        cart.setItems(new ArrayList<>());
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void should_get_orders_for_user() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        when(userRepository.findByUsername("test")).thenReturn(user);
        assertEquals(200, orderController.getOrdersForUser("test").getStatusCodeValue());
    }
}
