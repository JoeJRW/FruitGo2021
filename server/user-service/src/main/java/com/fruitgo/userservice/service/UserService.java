package com.fruitgo.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fruitgo.userservice.entity.User;


import java.util.List;

public interface UserService extends IService<User> {

    List<User> getUserByTel(String tel);
}
