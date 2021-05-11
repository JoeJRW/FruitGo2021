package com.fruitgo.goodsservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: wzh
 * @date: 2021/5/10 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String imgUrl;
    private Double price;
    private String storeUrl;
}
