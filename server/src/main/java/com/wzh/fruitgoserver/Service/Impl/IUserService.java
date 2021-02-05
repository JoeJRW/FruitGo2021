package com.wzh.fruitgoserver.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.fruitgoserver.Bean.User;
import com.wzh.fruitgoserver.Dao.UserMapper;
import com.wzh.fruitgoserver.Service.UserService;
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
