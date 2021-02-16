package com.wzh.fruitgoserver.Controller;

import com.wzh.fruitgoserver.Bean.Mission;
import com.wzh.fruitgoserver.Service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    MissionService missionService;

    /**
     * 添加任务
     * @param mission
     * @return
     */
    @PostMapping("/mission")
    public boolean addMission(Mission mission){
        return missionService.save(mission);
    }

    /**
     * 通过用户id获取用户的所有任务
     * @param userId
     * @return
     */
    @GetMapping("/mission/{id}")
    public List<Mission> getUserMission(@PathVariable("id")Long userId){

        return missionService.selectByUserId(userId);
    }

    /**
     * 修改任务的完成次数
     * @param mId
     * @param num
     * @return
     */
    @PutMapping("/mission/{id}/{num}")
    public boolean updateCompleteNum(@PathVariable("id")Long mId, @PathVariable("num")Integer num){
        int updateNum = missionService.updateCompNumById(mId, num);
        if (updateNum == 1)
            return true;
        else if (updateNum == 0)
            return false;
        else
            return false;
    }

    /**
     * 修改任务时长
     * @param mId
     * @param duration
     * @return
     */
    @PutMapping("/missionDuration/{id}/{duration}")
    public boolean updateDuration(@PathVariable("id")Long mId, @PathVariable("duration")Integer duration){
        int updateNum = missionService.updateDurationById(mId, duration);
        if (updateNum == 1)
            return true;
        else if (updateNum == 0)
            return false;
        else
            return false;
    }

    /**
     * 修改任务名
     * @param mId
     * @param mName
     * @return
     */
    @PutMapping("/missionName/{id}/{name}")
    public boolean updateName(@PathVariable("id")Long mId, @PathVariable("name")String mName){
        int updateNum = missionService.updateNameById(mId, mName);
        if (updateNum == 1)
            return true;
        else if (updateNum == 0)
            return false;
        else
            return false;
    }

    /**
     * 删除任务
     * @param mId
     * @return
     */
    @DeleteMapping("/mission")
    public boolean deleteMission(Long mId){
        return missionService.removeById(mId);
    }
}
