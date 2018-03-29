package com.example.lzd.cateringgroupbuy;

/**
 * Created by ldhns on 2018/3/17.
 */

public class MyAddress {
    private String name;
    private String phone;
    private String address;
    private String room;

    //订单参数：restaurant,foodtype,num,totalmoney.

    public MyAddress(String name, String phone, String address, String room){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRoom() {
        return room;
    }
}
