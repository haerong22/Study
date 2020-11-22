package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurantList = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        restaurantList.add(restaurant);
        return restaurantList;
    }
    @GetMapping("restaurants/{id}")
    public Restaurant detail(@PathVariable Long id) {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1004L, "Bob zip", "Seoul"));
        restaurantList.add(new Restaurant(2020L, "Cyber Food", "Seoul"));
        Restaurant restaurant = restaurantList.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null) ;

        return restaurant;
    }
}
