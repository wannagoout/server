package com.wannago.user.controller;

import com.wannago.user.dto.User;
import com.wannago.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/user/add")
    public String addUser(@RequestBody User newUser){
        return userService.addUser(newUser);
    }
}
