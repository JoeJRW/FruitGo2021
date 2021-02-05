package com.wzh.fruitgoserver.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.fruitgoserver.Bean.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> getUserByTel(String tel);
}
