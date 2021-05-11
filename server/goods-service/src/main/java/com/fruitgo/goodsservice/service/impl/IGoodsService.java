package com.fruitgo.goodsservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fruitgo.goodsservice.entity.Goods;
import com.fruitgo.goodsservice.mapper.GoodsMapper;
import com.fruitgo.goodsservice.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: wzh
 * @date: 2021/5/10 15:20
 */
@Service
public class IGoodsService extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

}
