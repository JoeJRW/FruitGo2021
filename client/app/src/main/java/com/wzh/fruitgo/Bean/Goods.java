package com.wzh.fruitgo.Bean;

/**
 * @author: wzh
 * @date：2021/4/30 9:22
 */
public class Goods {
    private Long id;
    private String name;
    private String imgUrl;
    private Double price;
    private String storeUrl;

    public Goods(){

    }

    public Goods(Long id, String name, String imgUrl, Double price, String storeUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.storeUrl = storeUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", storeUrl='" + storeUrl + '\'' +
                '}';
    }
}
