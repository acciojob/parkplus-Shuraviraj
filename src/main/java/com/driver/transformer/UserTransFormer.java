package com.driver.transformer;

import com.driver.model.User;

import java.util.ArrayList;

public class UserTransFormer {
    public static User toUser(String name, String phoneNumber, String password) {
        return new User(name, phoneNumber, password, new ArrayList<>());
    }
}
