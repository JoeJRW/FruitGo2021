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
        list.add(new PictureBean("https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg", null, 1));
        list.add(new PictureBean("https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg", null, 1));
        list.add(new PictureBean("https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg", null, 1));
        list.add(new PictureBean("https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg", null, 1));
        list.add(new PictureBean("https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg", null, 1));
        return list;
    }
}
