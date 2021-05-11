package com.fruitgo.bonusservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruitgo.bonusservice.entity.Bonus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface BonusMapper extends BaseMapper<Bonus> {

}
