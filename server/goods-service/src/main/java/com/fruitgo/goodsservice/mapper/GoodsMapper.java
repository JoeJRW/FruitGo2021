package com.fruitgo.goodsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruitgo.goodsservice.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther: wzh
 * @date: 2021/5/10 15:18
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
