package com.example.lzd.cateringgroupbuy;

import java.io.Serializable;

/**
 * Created by ldhns on 2018/3/16.
 */

public class OrderClass implements Serializable {
    private Restaurant restaurant;//名称
    private String foodtype;//规格
    private String totalmoney;//总价
    private int num;//数量

    //订单参数：restaurant,foodtype,num,totalmoney.

    public OrderClass(Restaurant restaurant, String foodtype, String totalmoney, int num){
        this.restaurant = restaurant;
        this.foodtype = foodtype;
        this.totalmoney = totalmoney;
        this.num = num;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public int getNum() {
        return num;
    }
}
