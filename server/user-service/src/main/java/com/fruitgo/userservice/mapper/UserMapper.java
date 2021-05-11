package com.fruitgo.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruitgo.userservice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where tel = #{tel}")
    List<User> selectByTel(String tel);
}
