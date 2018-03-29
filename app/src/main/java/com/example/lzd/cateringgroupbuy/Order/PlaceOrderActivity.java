package com.example.lzd.cateringgroupbuy.Order;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Homepage.PayFoodActivity;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

/**
 * Created by ldhns on 2018/3/12.
 */

public class PlaceOrderActivity extends Activity implements View.OnClickListener{

    TextView foodname,foodprice,foodnumber,totalmoney,foodguige;
    ImageView jia,jian,foodimage,back;
    Button pay;
    Restaurant restaurant;
    String foodmoney,foodtype;
    int num=1;

    private MyDBHelper dbHelper;

    //订单参数：restaurant,foodtype,num,totalmoney.
    //handler更新UI
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message message)
        {

            switch (message.what)
            {
                case R.id.btn_jia:{

                    num++;
                    changeJianIamge();
                    foodnumber.setText(Integer.toString(num));
                    totalmoney.setText("¥ "+Integer.toString(num * Integer.valueOf(foodmoney).intValue()));
                    pay.setText(totalmoney.getText()+" 提交订单");
                    break;
                }
                case R.id.btn_jian:{
                    //changeJianIamge();
                    num--;
                    changeJianIamge();
                    foodnumber.setText(Integer.toString(num));
                    totalmoney.setText("¥ "+Integer.toString(num * Integer.valueOf(foodmoney).intValue()));
                    pay.setText(totalmoney.getText()+" 提交订单");
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palceorder);

        //num = Integer.valueOf(foodnumber.getText().toString()).intValue();//数量

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant_data");
        foodmoney =  getIntent().getStringExtra("food_money");
        foodtype = getIntent().getStringExtra("food_type");

        foodname = (TextView)findViewById(R.id.food_name);
        foodname.setText(restaurant.getName());

        foodprice = (TextView)findViewById(R.id.food_price);
        foodprice.setText("¥ "+foodmoney);

        foodguige = (TextView)findViewById(R.id.food_type);
        foodguige.setText("单人餐 "+foodtype);

        totalmoney = (TextView)findViewById(R.id.money_total);
        totalmoney.setText("¥ "+foodmoney);

        foodnumber = (TextView)findViewById(R.id.food_num);
        foodnumber.setText(Integer.toString(num));

        foodimage = (ImageView)findViewById(R.id.food_image);
        foodimage.setImageResource(restaurant.getImageId());

        back = (ImageView)findViewById(R.id.back_palaceorder);
        back.setOnClickListener(this);
        jia = (ImageView)findViewById(R.id.btn_jia);
        jia.setOnClickListener(this);

        jian = (ImageView)findViewById(R.id.btn_jian);
        jian.setOnClickListener(this);

        pay = (Button)findViewById(R.id.btn_pay);
        pay.setText("¥ "+foodmoney+" 提交订单");
        pay.setOnClickListener(this);

        dbHelper = new MyDBHelper(this,"OrderStore.db",null,1);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_palaceorder:{
                finish();
                break;
            }
            case R.id.btn_jia:{
                sendUpdateMessage(R.id.btn_jia);
                break;
            }
            case R.id.btn_jian:{
                sendUpdateMessage(R.id.btn_jian);
                break;
            }
            case R.id.btn_pay:{//提交订单
                if(addOrder(restaurant,foodtype,totalmoney.getText().toString(),num)){
                    Intent intent = new Intent(PlaceOrderActivity.this,PayOrderActivity.class);
                    intent.putExtra("food_money",totalmoney.getText());
                    Toast.makeText(this,"订单提交成功",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"订单提交失败",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

    //向数据库插入数据
    public boolean addOrder(Restaurant rest,String type,String total,int number){
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名

        ContentValues values = new ContentValues();
        values.put("user",sp.getString("username", ""));
        values.put("name",rest.getName());
        values.put("address",rest.getAddress());
        values.put("leibie",rest.getLeibie());
        values.put("imageId",rest.getImageId());
        values.put("foodtype",type);
        values.put("num",number);
        values.put("money",total);
        db.insert("orderTable",null,values);


        db.close();
        return true;
    }

    //发送更新信息,开启子线程更新UI
    private void sendUpdateMessage( final int i){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = i;
                mHandler.sendMessage(message); // 将Message对象发送出去
            }
        }).start();
    }

    public void changeJianIamge(){
        /*if(Integer.valueOf(foodnumber.getText().toString()).intValue()<=0){*/
        if(num<=1){
            jian.setClickable(false);
            jian.setImageResource(R.drawable.jian0);
        }else{
            jian.setClickable(true);
            jian.setImageResource(R.drawable.jian1);
        }
    }
}
