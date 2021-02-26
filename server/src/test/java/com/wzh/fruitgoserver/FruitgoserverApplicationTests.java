package com.wzh.fruitgoserver;

import com.wzh.fruitgoserver.Bean.User;
import com.wzh.fruitgoserver.Dao.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class FruitgoserverApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }


}
