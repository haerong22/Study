package com.example.eatgo.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    private List<Restaurant> restaurantList = new ArrayList<>();

    public RestaurantRepository() {
        restaurantList.add(new Restaurant(1004L, "Bob zip", "Seoul"));
        restaurantList.add(new Restaurant(2020L, "Cyber Food", "Seoul"));
    }

    public List<Restaurant> findAll() {
        return restaurantList;
    }

    public Restaurant findById(Long id) {
        return restaurantList.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null) ;
    }
}
