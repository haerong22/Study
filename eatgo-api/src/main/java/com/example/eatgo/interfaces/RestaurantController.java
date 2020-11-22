package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.domain.RestaurantRepository;
import com.example.eatgo.domain.RestaurantRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepository repository;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        return repository.findAll();
    }
    @GetMapping("restaurants/{id}")
    public Restaurant detail(@PathVariable Long id) {
        return repository.findById(id);
    }
}
