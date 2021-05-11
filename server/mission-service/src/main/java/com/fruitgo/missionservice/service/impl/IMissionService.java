package com.fruitgo.missionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fruitgo.missionservice.entity.Mission;
import com.fruitgo.missionservice.mapper.MissionMapper;
import com.fruitgo.missionservice.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMissionService extends ServiceImpl<MissionMapper, Mission> implements MissionService {

    @Autowired
    MissionMapper missionMapper;

    @Override
    public List<Mission> selectByUserId(Long userId) {
        QueryWrapper<Mission> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        List<Mission> missions = missionMapper.selectList(wrapper);
        return missions;
    }

    @Override
    public int updateCompNumById(Long mId, Integer num) {
        Mission m = missionMapper.selectById(mId);
        m.setCompNum(num);

        UpdateWrapper<Mission> wrapper = new UpdateWrapper<>();
        wrapper.eq("Id", mId);
        return missionMapper.update(m, wrapper);
    }

    @Override
    public int updateDurationById(Long mId, Integer duration) {
        Mission m = missionMapper.selectById(mId);
        m.setMissionDuration(duration);

        UpdateWrapper<Mission> wrapper = new UpdateWrapper<>();
        wrapper.eq("Id", mId);
        return missionMapper.update(m, wrapper);
    }

    @Override
    public int updateNameById(Long mId, String mName) {
        Mission m = missionMapper.selectById(mId);
        m.setMissionName(mName);

        UpdateWrapper<Mission> wrapper = new UpdateWrapper<>();
        wrapper.eq("Id", mId);
        return missionMapper.update(m, wrapper);
    }
}
