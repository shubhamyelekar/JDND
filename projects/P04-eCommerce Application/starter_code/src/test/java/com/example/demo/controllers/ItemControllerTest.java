package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void should_get_item_by_id() {
        Item item = new Item();
        when(itemRepository.findById((long) 0)).thenReturn(java.util.Optional.ofNullable(item));
        ResponseEntity<Item> itemById = itemController.getItemById((long) 0);
        assertEquals(200, itemById.getStatusCodeValue());
    }

    @Test
    public void should_get_items() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Item>> items = itemController.getItems();
        assertEquals(200, items.getStatusCodeValue());

    }

    @Test
    public void should_get_item_by_name() {
        Item item = new Item();
        when(itemRepository.findByName("test")).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> test = itemController.getItemsByName("test");
        assertEquals(200, test.getStatusCodeValue());
    }

}
