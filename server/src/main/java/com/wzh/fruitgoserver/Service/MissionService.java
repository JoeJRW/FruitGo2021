package com.wzh.fruitgoserver.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.fruitgoserver.Bean.Mission;

import java.util.List;

public interface MissionService extends IService<Mission> {

    List<Mission> selectByUserId(Long userId);

    int updateCompNumById(Long mId, Integer num);

    int updateDurationById(Long mId, Integer duration);

    int updateNameById(Long mId, String mName);
}
