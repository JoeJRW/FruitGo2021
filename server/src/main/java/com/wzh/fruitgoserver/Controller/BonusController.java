package com.wzh.fruitgoserver.Controller;

import com.wzh.fruitgoserver.Bean.Bonus;
import com.wzh.fruitgoserver.Service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bonus")
public class BonusController {

    @Autowired
    BonusService bonusService;

    /**
     * 添加积分奖励记录
     * @param bonus
     * @return
     */
    @PostMapping("/bonus")
    public boolean addBonus(Bonus bonus){
        return bonusService.save(bonus);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 获取用户所有积分奖励记录
     * @param userId
     * @return
     */
    @GetMapping("/bonus/{id}")
    public List<Bonus> getUserBonus(@PathVariable("id") Long userId){
        return bonusService.selectBonusByUserId(userId);
    }

    /**
     * 获取当前时间24h内所有未获取的积分记录
     * @param userId
     * @return
     */
    @GetMapping("/tbonus/{id}")
    public List<Bonus> getUserTodayBonus(@PathVariable("id") Long userId){
        return bonusService.selectTodayBonusByUserId(userId);
    }

    /**
     * 更新积分获取情况
     * @param bonus
     * @return
     */
    @PutMapping("/bonus")
    public boolean updateBonusStatus(Bonus bonus){
        return bonusService.updateById(bonus);
    }
}
