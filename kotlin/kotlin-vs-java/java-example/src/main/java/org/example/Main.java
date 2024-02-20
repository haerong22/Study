package org.example;

import org.example.user.UserModel;

public class Main {
    public static void main(String[] args) {

        UserModel user = new UserModel(
                "홍길동", 20, "email@email.com"
        );

        System.out.println("user = " + user);
    }
}