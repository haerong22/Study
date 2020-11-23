package com.example.eatgo.domain;

import java.util.ArrayList;
import java.util.List;

public class MenuItemRepositoryImpl implements MenuItemRepository {
    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("kimchi"));
        return menuItems;
    }
}
