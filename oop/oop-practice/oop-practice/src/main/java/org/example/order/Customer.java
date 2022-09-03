package org.example.order;

public class Customer {

    public void order(String name, Menu menu, Cooking cooking) {
        MenuItem menuItem = menu.choose(name);
        Cook cook = cooking.makeCook(menuItem);

    }
}
