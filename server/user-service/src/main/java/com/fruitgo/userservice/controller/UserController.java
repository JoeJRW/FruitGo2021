package com.fruitgo.userservice.controller;


import com.fruitgo.userservice.entity.User;
import com.fruitgo.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public boolean addUser(User user){
        return userService.save(user);
    }

    @PostMapping("/getUser")
    public List<User> getUser(String tel){
        return userService.getUserByTel(tel);
    }

    @GetMapping("/getAll")
    public List<User> getAllUser(){
        return userService.list();
    }
}
