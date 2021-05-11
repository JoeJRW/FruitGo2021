package com.fruitgo.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fruitgo.userservice.entity.User;
import com.fruitgo.userservice.mapper.UserMapper;
import com.fruitgo.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IUserService extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserByTel(String tel) {
        List<User> users = userMapper.selectByTel(tel);
        return users;
    }
}
