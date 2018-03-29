package com.example.lzd.cateringgroupbuy.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.Order.PlaceOrderActivity;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

/**
 * Created by ldhns on 2018/3/10.
 */

public class PayFoodActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    TextView qianggou,money,title;
    Restaurant restaurant;
    String foodmoney,foodtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_food);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant_data");
        foodmoney =  getIntent().getStringExtra("food_money");
        foodtype = getIntent().getStringExtra("food_type");

        ImageView foodimage =(ImageView)findViewById(R.id.foodimage);
        foodimage.setImageResource(restaurant.getImageId());

        title = (TextView) findViewById(R.id.pay_name_guige);
        title.setText(restaurant.getName()+" "+foodtype);

        back = (ImageView) findViewById(R.id.back_pay_food);
        back.setOnClickListener(this);

        qianggou = (TextView) findViewById(R.id.btn_qianggou);
        qianggou.setOnClickListener(this);

        money = (TextView) findViewById(R.id.money);
        money.setText("¥ "+foodmoney);
    }

    @Override
    public void onClick(View view){

        String dafen = "25元",zhongfen = "20元",xiaofen = "15元";
        String Dafen = "大份",Zhongfen = "中份",Xiaofen = "小份";

        if(view.getId() == R.id.back_pay_food){
            finish();
        }
        if(view.getId() == R.id.btn_qianggou){
            Intent intent = new Intent(PayFoodActivity.this,PlaceOrderActivity.class);
            intent.putExtra("food_money", foodmoney);
            intent.putExtra("food_type", foodtype);
            intent.putExtra("restaurant_data", restaurant);
            startActivity(intent);
            //Toast.makeText(this,"成功抢购 "+foodtype+" "+restaurant.getName(),Toast.LENGTH_SHORT).show();
        }

    }
}
