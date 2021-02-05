package com.wzh.fruitgoserver.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzh.fruitgoserver.Bean.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where tel = #{tel}")
    List<User> selectByTel(String tel);
}
