package com.wzh.fruitgoserver.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

//    private Long id;
//    private String name;
//    private Integer age;
//    private String email;

    @TableId(type = IdType.INPUT)
    private Long id;
    private String tel;
    private String password;
    private String name;
    private Integer gender;//0为女，1为男
    private Date birthday;
    private Integer bonus;
    //todo 头像
}
