package com.fruitgo.goodsservice.controller;

import com.fruitgo.goodsservice.entity.Goods;
import com.fruitgo.goodsservice.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther: wzh
 * @date: 2021/5/10 15:21
 */
@Api(
        tags = "商品模块",
        value = "商品模块"
)
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @PostMapping("/goods")
    @ApiOperation(notes = "新增商品", value = "新增商品")
    public boolean addGoods(Goods goods){
        return goodsService.save(goods);
    }

    @GetMapping("/getAll")
    @ApiOperation(notes = "获取所有商品", value = "获取所有商品")
    public List<Goods> getAllGoods(){
        return goodsService.list();
    }
}
