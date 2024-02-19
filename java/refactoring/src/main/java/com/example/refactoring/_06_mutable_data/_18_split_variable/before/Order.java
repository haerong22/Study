package com.example.refactoring._06_mutable_data._18_split_variable.before;

public class Order {

    public double discount(double inputValue, int quantity) {
        if (inputValue > 50) inputValue = inputValue - 2;
        if (quantity > 100) inputValue = inputValue - 1;
        return inputValue;
    }
}
