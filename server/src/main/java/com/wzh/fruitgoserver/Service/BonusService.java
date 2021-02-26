package com.wzh.fruitgoserver.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.fruitgoserver.Bean.Bonus;

import java.util.List;

public interface BonusService extends IService<Bonus> {

    List<Bonus> selectBonusByUserId(Long id);

    List<Bonus> selectTodayBonusByUserId(Long userId);
}
