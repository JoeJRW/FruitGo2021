package com.wzh.fruitgoserver.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bonus {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("bonusType")
    private Integer bonusType;//1当日首次登录，2完成任务，3步数奖励
    @TableField("value")
    private Integer value;
    @TableField("status")
    private Integer status;//0未领取，1已领取
    @TableField("createTime")
    private Date createTime;
}
