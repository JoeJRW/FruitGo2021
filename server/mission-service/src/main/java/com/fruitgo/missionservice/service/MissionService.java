package com.fruitgo.missionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fruitgo.missionservice.entity.Mission;

import java.util.List;

public interface MissionService extends IService<Mission> {

    List<Mission> selectByUserId(Long userId);

    int updateCompNumById(Long mId, Integer num);

    int updateDurationById(Long mId, Integer duration);

    int updateNameById(Long mId, String mName);
}
