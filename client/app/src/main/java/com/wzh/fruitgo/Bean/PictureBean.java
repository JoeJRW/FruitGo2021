package com.wzh.fruitgo.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wzh
 * @date：2021/4/28 19:43
 */
public class PictureBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;

    public PictureBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public PictureBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    /**
     * 测试使用
     * @return
     */
    public static List<PictureBean> getTestData() {
        List<PictureBean> list = new ArrayList<>();
        list.add(new PictureBean("https://aimg8.dlssyht.cn/ev_user_module_content_tmp/2019_05_09/tmp1557385644_1662130_s.jpg", null, 1));
        list.add(new PictureBean("https://aimg8.dlszyht.net.cn/ev_user_module_content_tmp/2019_02_25/tmp1551077482_1662130_s.jpg", null, 1));
        return list;
    }
}
