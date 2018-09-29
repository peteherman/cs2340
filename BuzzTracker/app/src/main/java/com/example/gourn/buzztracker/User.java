package com.example.gourn.buzztracker;

public class User {
    String name;
    String email;
    String password;
    UserType userType;


    public User(String name, String email, String password, UserType userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
