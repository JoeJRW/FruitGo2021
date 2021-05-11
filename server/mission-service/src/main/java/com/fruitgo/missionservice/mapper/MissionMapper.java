package com.fruitgo.missionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruitgo.missionservice.entity.Mission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MissionMapper extends BaseMapper<Mission> {

}
