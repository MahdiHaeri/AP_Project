package org.example.controllers;

import org.example.models.User;

import java.util.Date;

public class UserController {
    public UserController() {

    }

    public void createUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
    }

}