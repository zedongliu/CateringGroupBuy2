package com.example.lzd.cateringgroupbuy.Homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.Restaurant;
import com.example.lzd.cateringgroupbuy.R;


/**
 * Created by ldhns on 2018/3/9.
 */

public class InfoRestActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView restimage,restimage1,restimage2,restimage3,back,collect,phone;
    Restaurant restaurant ;
    TextView renjun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant_data");

        restimage = (ImageView) findViewById(R.id.rest_image);
        restimage1 = (ImageView) findViewById(R.id.rest_image1);
        restimage2 = (ImageView) findViewById(R.id.rest_image2);
        restimage3 = (ImageView) findViewById(R.id.rest_image3);


        restimage.setImageResource(restaurant.getImageId());
        restimage1.setImageResource(restaurant.getImageId());
        restimage2.setImageResource(restaurant.getImageId());
        restimage3.setImageResource(restaurant.getImageId());

        TextView restname = (TextView) findViewById(R.id.rest_name);
        restname.setText(restaurant.getName());

        TextView restaddress =(TextView) findViewById(R.id.rest_address);
        restaddress.setText(restaurant.getAddress());

        renjun = (TextView) findViewById(R.id.rest_info_renjun);
        renjun.setText("人均："+restaurant.getAverage()+"元");

        back = (ImageView) findViewById(R.id.infoback);
        back.setOnClickListener(this);
       // collect = (ImageView) findViewById(R.id.btn_collect);
        //collect.setOnClickListener(this);
        phone = (ImageView) findViewById(R.id.phone);
        phone.setOnClickListener(this);
        Button maidan = (Button) findViewById(R.id.maidan);
        maidan.setOnClickListener(this);
        Button xuangou1 = (Button) findViewById(R.id.xuangou1);
        xuangou1.setOnClickListener(this);
        Button xuangou2 = (Button) findViewById(R.id.xuangou2);
        xuangou2.setOnClickListener(this);
        Button xuangou3 = (Button) findViewById(R.id.xuangou3);
        xuangou3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        String dafen = "25",zhongfen = "20",xiaofen = "15";
        String Dafen = "大份",Zhongfen = "中份",Xiaofen = "小份";

        if(view.getId()== R.id.infoback)
        {
            this.finish();
        }
        if(view.getId()== R.id.maidan)
        {

            Intent intent = new Intent(InfoRestActivity.this,PayActivity.class);
            intent.putExtra("restaurant_data", restaurant);
            startActivity(intent);
        }
        if(view.getId()== R.id.phone)
        {
            //跳转系统拨号界面
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "12345678910"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(view.getId() == R.id.xuangou1){
            Intent intent = new Intent(InfoRestActivity.this,PayFoodActivity.class);
            intent.putExtra("food_money", dafen);
            intent.putExtra("food_type", Dafen);
            intent.putExtra("restaurant_data", restaurant);
            startActivity(intent);
        }
        if(view.getId() == R.id.xuangou2){
            Intent intent = new Intent(InfoRestActivity.this,PayFoodActivity.class);
            intent.putExtra("food_money", zhongfen);
            intent.putExtra("food_type", Zhongfen);
            intent.putExtra("restaurant_data", restaurant);
            startActivity(intent);
        }
        if(view.getId() == R.id.xuangou3){
            Intent intent = new Intent(InfoRestActivity.this,PayFoodActivity.class);
            intent.putExtra("food_money", xiaofen);
            intent.putExtra("food_type", Xiaofen);
            intent.putExtra("restaurant_data", restaurant);
            startActivity(intent);
        }
    }
}
