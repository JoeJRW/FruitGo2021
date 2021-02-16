package com.wzh.fruitgoserver.Controller;

import com.wzh.fruitgoserver.Bean.User;
import com.wzh.fruitgoserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
