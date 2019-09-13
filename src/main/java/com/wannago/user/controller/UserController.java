package com.wannago.user.controller;

import com.wannago.user.dto.User;
import com.wannago.user.service.PushService;
import com.wannago.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PushService pushService;

    @PostMapping("/api/user/add")
    public String addUser(@RequestBody User newUser){
        return userService.addUser(newUser);
    }

    @Scheduled(cron = "0 0,30 * * * ?")
    public void fcmTest() throws Exception {
        pushService.sendFcm();
    }
}
