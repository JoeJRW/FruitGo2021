package com.wzh.fruitgoserver.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("missionName")
    private String missionName;
    @TableField("missionDuration")
    private Integer missionDuration;
    @TableField("compNum")
    private Integer compNum;//完成次数
}
