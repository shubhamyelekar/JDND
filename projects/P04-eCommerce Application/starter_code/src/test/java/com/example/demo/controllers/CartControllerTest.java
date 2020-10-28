package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void should_add_to_cart() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        user.setCart(new Cart());
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(10);
        modifyCartRequest.setQuantity(0);
        modifyCartRequest.setUsername("test");

        when(itemRepository.findById((long) 10)).thenReturn(java.util.Optional.of(new Item()));
        when(userRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);

        assertEquals(200, cartResponseEntity.getStatusCodeValue());

    }

    @Test
    public void should_remove_from_cart() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setId(0);
        user.setCart(new Cart());
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(10);
        modifyCartRequest.setQuantity(0);
        modifyCartRequest.setUsername("test");
        when(itemRepository.findById((long) 10)).thenReturn(java.util.Optional.of(new Item()));
        when(userRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        assertEquals(200, cartResponseEntity.getStatusCodeValue());
    }
}
