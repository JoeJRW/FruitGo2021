package com.fruitgo.bonusservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fruitgo.bonusservice.entity.Bonus;
import com.fruitgo.bonusservice.mapper.BonusMapper;
import com.fruitgo.bonusservice.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IBonusService extends ServiceImpl<BonusMapper, Bonus> implements BonusService {

    @Autowired
    BonusMapper bonusMapper;

    @Override
    public List<Bonus> selectBonusByUserId(Long userId) {
        QueryWrapper<Bonus> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        List<Bonus> bonuses = bonusMapper.selectList(wrapper);
        return bonuses;
    }

    @Override
    public List<Bonus> selectTodayBonusByUserId(Long userId) {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        Calendar presentTime = Calendar.getInstance();
        Calendar aDayBefore = Calendar.getInstance();
        presentTime.setTime(date);
        aDayBefore.setTime(date);
        aDayBefore.add(Calendar.DATE, -1);
        Date oneDayBefore = aDayBefore.getTime();

        QueryWrapper<Bonus> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId)
                .eq("status", 0)
                .ge("createTime", dateFormat.format(oneDayBefore))
                .le("createTime", dateFormat.format(date));
        List<Bonus> bonuses = bonusMapper.selectList(wrapper);
        return bonuses;
    }
}
