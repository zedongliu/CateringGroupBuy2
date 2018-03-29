package com.example.lzd.cateringgroupbuy.Homepage;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.Login.MainActivity;
import com.example.lzd.cateringgroupbuy.Mine.MineFragment;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.Order.OrderFragment;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

/**
 * Created by ldhns on 2018/1/7.
 */

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView homepage_image,order_image,mine_image;
    private TextView homepage_text,order_text,mine_text;

    private View orderBtn,mineBtn;
    private View homepageBtn;

    private Fragment firstFragment, homepageFragment, orderFragment, mineFragment;
    private MyDBHelper dbHelper;
    String userName,cityName;

    //handler更新UI
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message message)
        {
            changeColor(message.what);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        cityName = getIntent().getStringExtra("cityname");

        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
            //获得保存在SharedPredPreferences中的用户名
        userName = sp.getString("username", "");

        homepageBtn = (View) findViewById(R.id.btn_homepage);
        homepageBtn.setOnClickListener(this);

        orderBtn = (View) findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(this);

        mineBtn = (View) findViewById(R.id.btn_mine);
        mineBtn.setOnClickListener(this);

        homepage_image = (ImageView) findViewById(R.id.homepage_image);
        order_image = (ImageView) findViewById(R.id.order_image);
        mine_image = (ImageView) findViewById(R.id.mine_image);

        homepage_text = (TextView) findViewById(R.id.homepage_text);
        order_text = (TextView) findViewById(R.id.order_text);
        mine_text = (TextView) findViewById(R.id.mine_text);

        initFragment();
        getLogState();

        dbHelper = new MyDBHelper(this,"RestStore.db",null,1);
        if(RestDatabaseNull()){
            insertRest();
        }
    }

    //读取保存在本地的登录状态
    public void getLogState() {

        //创建SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("logstate", MODE_PRIVATE);

        //获得保存在SharedPredPreferences中的用户名和密码

        boolean STATE = sp.getBoolean("state",false);
        if(!STATE) {
            Intent intent = new Intent(HomePageActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void initFragment() {
        firstFragment = new HomepageFragment();
        homepageFragment = firstFragment;
        // 最先加载的 fragment
        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_layout, homepageFragment).commit();

    }

    /**
     * 判断是否添加了界面，以保存当前状态
     */
    public void switchContent(Fragment from, Fragment to, FragmentTransaction transaction) {

        if (!to.isAdded()) { // 先判断是否被add过
            transaction.hide(from).add(R.id.fragment_layout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }


    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.btn_homepage:{

                if(homepageFragment == null){
                    homepageFragment = new HomepageFragment();
                }
                switchContent(firstFragment, homepageFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = homepageFragment;

                sendUpdateMessage(R.id.btn_homepage);
                break;
            }
            case R.id.btn_order:{

                if(orderFragment == null){
                    orderFragment = new OrderFragment();
                }
                switchContent(firstFragment, orderFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = orderFragment;

                sendUpdateMessage(R.id.btn_order);
                break;
            }case R.id.btn_mine:{

                if(mineFragment == null){
                    mineFragment = new MineFragment();
                }
                switchContent(firstFragment, mineFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = mineFragment;

                sendUpdateMessage(R.id.btn_mine);
                break;
            }
        }
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

    //改变导航栏图标颜色
    public void changeColor(int fragment){
        switch (fragment){
            case R.id.btn_homepage:{
                homepage_image.setImageResource(R.drawable.ishomepage);
                homepage_text.setTextColor(this.getResources().getColor(R.color.selected));

                order_image.setImageResource(R.drawable.order);
                order_text.setTextColor(this.getResources().getColor(R.color.disselected));

                mine_image.setImageResource(R.drawable.mine);
                mine_text.setTextColor(this.getResources().getColor(R.color.disselected));
                break;
            }
            case R.id.btn_order:{
                homepage_image.setImageResource(R.drawable.homepage);
                homepage_text.setTextColor(this.getResources().getColor(R.color.disselected));

                order_image.setImageResource(R.drawable.isorder);
                order_text.setTextColor(this.getResources().getColor(R.color.selected));

                mine_image.setImageResource(R.drawable.mine);
                mine_text.setTextColor(this.getResources().getColor(R.color.disselected));
                break;
            }
            case R.id.btn_mine:{

                homepage_image.setImageResource(R.drawable.homepage);
                homepage_text.setTextColor(this.getResources().getColor(R.color.disselected));

                order_image.setImageResource(R.drawable.order);
                order_text.setTextColor(this.getResources().getColor(R.color.disselected));

                mine_image.setImageResource(R.drawable.ismine);
                mine_text.setTextColor(this.getResources().getColor(R.color.selected));
                break;
            }

        }
    }

    //判断RestTable是否为空表
    public boolean RestDatabaseNull(){
        int num=0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from restTable",null);
        while (cursor.moveToNext()){
            num++;
        }
        if(num<=0){
            return true;
        }
        return false;
    }

    //添加餐厅信息到数据库
    public void insertRest(){

        SQLiteDatabase db= dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name","麻辣到底");values.put("address","博大广场");
        values.put("average","23.8");values.put("category","小吃");
        values.put("imageId",R.drawable.xiaochi_maladaodi);
        db.insert("restTable",null,values);

        ContentValues values1 = new ContentValues();
        values1.put("name","N多寿司");values1.put("address","花园路");
        values1.put("average","25");values1.put("category","小吃");
        values1.put("imageId",R.drawable.xiaochi_nduoshousi);
        db.insert("restTable",null,values1);

        ContentValues values2 = new ContentValues();
        values2.put("name","绝味鸭脖");values2.put("address","花园路");
        values2.put("average","23");values2.put("category","小吃");
        values2.put("imageId",R.drawable.xiaochi_yabo);
        db.insert("restTable",null,values2);

        ContentValues values3 = new ContentValues();
        values3.put("name","潼关肉夹馍");values3.put("address","星光广场");
        values3.put("average","23.5");values3.put("category","小吃");
        values3.put("imageId",R.drawable.xiaochi_roujiamo);
        db.insert("restTable",null,values3);

        ContentValues values4 = new ContentValues();
        values4.put("name","八婆婆");values4.put("address","星光广场");
        values4.put("average","20.5");values4.put("category","小吃");
        values4.put("imageId",R.drawable.xiaochi_bapopo);
        db.insert("restTable",null,values4);

        ContentValues values5 = new ContentValues();
        values5.put("name","快乐柠檬");values5.put("address","解放路");
        values5.put("average","20");values5.put("category","茶饮");
        values5.put("imageId",R.drawable.chayin_kuaileningmeng);
        db.insert("restTable",null,values5);

        ContentValues values6 = new ContentValues();
        values6.put("name","一点点");values6.put("address","解放路");
        values6.put("average","25");values6.put("category","茶饮");
        values6.put("imageId",R.drawable.chayin_yidiandian);
        db.insert("restTable",null,values6);

        ContentValues values7 = new ContentValues();
        values7.put("name","星巴克");values7.put("address","花园路");
        values7.put("average","55");values7.put("category","茶饮");
        values7.put("imageId",R.drawable.chayin_xingbake);
        db.insert("restTable",null,values7);

        ContentValues values8 = new ContentValues();
        values8.put("name","CoCo都可");values8.put("address","博大");
        values8.put("average","15");values8.put("category","茶饮");
        values8.put("imageId",R.drawable.chayin_duke);
        db.insert("restTable",null,values8);

        ContentValues values9 = new ContentValues();
        values9.put("name","世界茶饮");values9.put("address","海岸城");
        values9.put("average","22");values9.put("category","茶饮");
        values9.put("imageId",R.drawable.chayin_shijiechayin);
        db.insert("restTable",null,values9);

        ContentValues values10 = new ContentValues();
        values10.put("name","太平洋咖啡");values10.put("address","星光广场");
        values10.put("average","32");values10.put("category","茶饮");
        values10.put("imageId",R.drawable.chayin_taipingyang);
        db.insert("restTable",null,values10);

        ContentValues values11 = new ContentValues();
        values11.put("name","杨铭宇黄焖鸡米饭");values11.put("address","星光广场");
        values11.put("average","16");values11.put("category","快餐");
        values11.put("imageId",R.drawable.kuaican_yangmingyu);
        db.insert("restTable",null,values11);

        ContentValues values12 = new ContentValues();
        values12.put("name","肯德基");values12.put("address","海岸城");
        values12.put("average","36");values12.put("category","快餐");
        values12.put("imageId",R.drawable.kuaican_kendeji);
        db.insert("restTable",null,values12);

        ContentValues values13 = new ContentValues();
        values13.put("name","必胜客餐厅");values13.put("address","蠡湖大道");
        values13.put("average","38");values13.put("category","快餐");
        values13.put("imageId",R.drawable.kuaican_bishengke);
        db.insert("restTable",null,values13);

        ContentValues values14 = new ContentValues();
        values14.put("name","彭德楷黄焖鸡米饭");values14.put("address","星光广场");
        values14.put("average","18");values14.put("category","快餐");
        values14.put("imageId",R.drawable.kuaican_pengdekai);
        db.insert("restTable",null,values14);

        ContentValues values15 = new ContentValues();
        values15.put("name","德克士");values15.put("address","万象城");
        values15.put("average","30");values15.put("category","快餐");
        values15.put("imageId",R.drawable.kuaican_dekeshi);
        db.insert("restTable",null,values15);

        ContentValues values16 = new ContentValues();
        values16.put("name","麻椒小厨");values16.put("address","花园路");
        values16.put("average","30");values16.put("category","快餐");
        values16.put("imageId",R.drawable.kuaican_majiao);
        db.insert("restTable",null,values16);

        ContentValues values17 = new ContentValues();
        values17.put("name","良品铺子");values17.put("address","博大广场");
        values17.put("average","30");values17.put("category","零食");
        values17.put("imageId",R.drawable.lingshi_liangpinpuzi);
        db.insert("restTable",null,values17);

        ContentValues values18 = new ContentValues();
        values18.put("name","老婆大人");values18.put("address","解放路");
        values18.put("average","25");values18.put("category","零食");
        values18.put("imageId",R.drawable.lingshi_laopo);
        db.insert("restTable",null,values18);

        ContentValues values19 = new ContentValues();
        values19.put("name","一口零食");values19.put("address","花园路");
        values19.put("average","28");values19.put("category","零食");
        values19.put("imageId",R.drawable.lingshi_yikou);
        db.insert("restTable",null,values19);

        ContentValues values20 = new ContentValues();
        values20.put("name","非常食客");values20.put("address","万象城");
        values20.put("average","38");values20.put("category","零食");
        values20.put("imageId",R.drawable.lingshi_feichangshike);
        db.insert("restTable",null,values20);

        ContentValues values21 = new ContentValues();
        values21.put("name","多彩零食");values21.put("address","海岸城");
        values21.put("average","36");values21.put("category","零食");
        values21.put("imageId",R.drawable.lingshi_duocai);
        db.insert("restTable",null,values21);

        ContentValues values22 = new ContentValues();
        values22.put("name","七月初七");values22.put("address","星光广场");
        values22.put("average","32");values22.put("category","零食");
        values22.put("imageId",R.drawable.lingshi_qiyueqi);
        db.insert("restTable",null,values22);

        ContentValues values23 = new ContentValues();
        values23.put("name","巴奴毛肚火锅");values23.put("address","博大广场");
        values23.put("average","88");values23.put("category","火锅");
        values23.put("imageId",R.drawable.huoguo_banu);
        db.insert("restTable",null,values23);

        ContentValues values24 = new ContentValues();
        values24.put("name","巴蜀印象");values24.put("address","星光广场");
        values24.put("average","78");values24.put("category","火锅");
        values24.put("imageId",R.drawable.huoguo_bashuyinxiang);
        db.insert("restTable",null,values24);

        ContentValues values25 = new ContentValues();
        values25.put("name","海底捞");values25.put("address","海岸城");
        values25.put("average","76");values25.put("category","火锅");
        values25.put("imageId",R.drawable.huoguo_haidilao);
        db.insert("restTable",null,values25);

        ContentValues values26 = new ContentValues();
        values26.put("name","新辣道");values26.put("address","万象城");
        values26.put("average","86");values26.put("category","火锅");
        values26.put("imageId",R.drawable.huoguo_xinladao);
        db.insert("restTable",null,values26);

        ContentValues values27 = new ContentValues();
        values27.put("name","纳西印象");values27.put("address","博大广场");
        values27.put("average","82");values27.put("category","火锅");
        values27.put("imageId",R.drawable.huoguo_naxiyinxiang);
        db.insert("restTable",null,values27);

        ContentValues values28 = new ContentValues();
        values28.put("name","欢乐牧场");values28.put("address","博大广场");
        values28.put("average","62");values28.put("category","自助");
        values28.put("imageId",R.drawable.zizhu_huanlemuchang);
        db.insert("restTable",null,values28);

        ContentValues values29 = new ContentValues();
        values29.put("name","金汉森自助餐");values29.put("address","星光广场");
        values29.put("average","64");values29.put("category","自助");
        values29.put("imageId",R.drawable.zizhu_jinhansen);
        db.insert("restTable",null,values29);

        ContentValues values30 = new ContentValues();
        values30.put("name","木林森自助餐");values30.put("address","海岸城");
        values30.put("average","66");values30.put("category","自助");
        values30.put("imageId",R.drawable.zizhu_mulinsen);
        db.insert("restTable",null,values30);

        ContentValues values31 = new ContentValues();
        values31.put("name","金钱豹自助餐");values31.put("address","万象城");
        values31.put("average","68");values31.put("category","自助");
        values31.put("imageId",R.drawable.zizhu_jinqianbao);
        db.insert("restTable",null,values31);

        ContentValues values32 = new ContentValues();
        values32.put("name","深海800米");values32.put("address","博大广场");
        values32.put("average","69");values32.put("category","自助");
        values32.put("imageId",R.drawable.zizhu_shenhai);
        db.insert("restTable",null,values32);

        ContentValues values33 = new ContentValues();
        values33.put("name","巴楚烧烤");values33.put("address","博大广场");
        values33.put("average","59");values33.put("category","烧烤");
        values33.put("imageId",R.drawable.shaokao_bachu);
        db.insert("restTable",null,values33);

        ContentValues values34 = new ContentValues();
        values34.put("name","木屋烧烤");values34.put("address","海岸城");
        values34.put("average","59");values34.put("category","烧烤");
        values34.put("imageId",R.drawable.shaokao_muwu);
        db.insert("restTable",null,values34);

        ContentValues values35 = new ContentValues();
        values35.put("name","蒙卡尔烧烤");values35.put("address","星光广场");
        values35.put("average","69");values35.put("category","烧烤");
        values35.put("imageId",R.drawable.shaokao_mengkaer);
        db.insert("restTable",null,values35);

        ContentValues values36 = new ContentValues();
        values36.put("name","木香然烧烤");values36.put("address","星光广场");
        values36.put("average","66");values36.put("category","烧烤");
        values36.put("imageId",R.drawable.shaokao_muxiangran);
        db.insert("restTable",null,values36);

        ContentValues values360 = new ContentValues();
        values360.put("name","好HIGH串烧烤");values360.put("address","海岸城");
        values360.put("average","68");values360.put("category","烧烤");
        values360.put("imageId",R.drawable.shaokao_haohaichuan);
        db.insert("restTable",null,values360);

        ContentValues values37 = new ContentValues();
        values37.put("name","源古烧烤");values37.put("address","万象城");
        values37.put("average","68");values37.put("category","烧烤");
        values37.put("imageId",R.drawable.shaokao_yuangu);
        db.insert("restTable",null,values37);

        ContentValues values38 = new ContentValues();
        values38.put("name","潮客");values38.put("address","博大广场");
        values38.put("average","68");values38.put("category","美食");
        values38.put("imageId",R.drawable.meishi_chaoke);
        db.insert("restTable",null,values38);

        ContentValues values39 = new ContentValues();
        values39.put("name","外婆家");values39.put("address","星光广场");
        values39.put("average","70");values39.put("category","美食");
        values39.put("imageId",R.drawable.meishi_waipojia);
        db.insert("restTable",null,values39);

        ContentValues values40 = new ContentValues();
        values40.put("name","重庆渔码头");values40.put("address","星光广场");
        values40.put("average","76");values40.put("category","美食");
        values40.put("imageId",R.drawable.meishi_cqyumatou);
        db.insert("restTable",null,values40);

        ContentValues values41 = new ContentValues();
        values41.put("name","炉鱼");values41.put("address","海岸城");
        values41.put("average","67");values41.put("category","美食");
        values41.put("imageId",R.drawable.meishi_luyu);
        db.insert("restTable",null,values41);

        ContentValues values42 = new ContentValues();
        values42.put("name","掌柜的店");values42.put("address","博大广场");
        values42.put("average","62");values42.put("category","美食");
        values42.put("imageId",R.drawable.meishi_zhanggui);
        db.insert("restTable",null,values42);

        ContentValues values43 = new ContentValues();
        values43.put("name","小雨餐厅");values43.put("address","万象城");
        values43.put("average","72");values43.put("category","美食");
        values43.put("imageId",R.drawable.meishi_xiaoyu);
        db.insert("restTable",null,values43);

        db.close();

    }


}
