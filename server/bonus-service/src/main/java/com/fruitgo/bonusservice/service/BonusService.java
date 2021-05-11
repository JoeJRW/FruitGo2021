package com.fruitgo.bonusservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fruitgo.bonusservice.entity.Bonus;

import java.util.List;

public interface BonusService extends IService<Bonus> {

    List<Bonus> selectBonusByUserId(Long id);

    List<Bonus> selectTodayBonusByUserId(Long userId);
}
