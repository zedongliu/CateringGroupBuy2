package com.example.lzd.cateringgroupbuy;

import java.io.Serializable;

/**
 * Created by ldhns on 2018/3/8.
 */

public class Restaurant implements Serializable {
    private String name;
    private String address;
    private String leibie;
    private String average;
    private int imageId;

    public Restaurant(String name,String address,String leibie,String average,int imageId){
        this.name = name;
        this.address = address;
        this.leibie = leibie;
        this.average = average;
        this.imageId = imageId;
    }

    public Restaurant(String name,String address,String leibie,int imageId){
        this.name = name;
        this.address = address;
        this.leibie = leibie;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }

    public String getLeibie() {
        return leibie;
    }

    public String getAverage() {
        return average;
    }
}
