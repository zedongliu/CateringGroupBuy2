package com.example.lzd.cateringgroupbuy.Homepage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;
import com.example.lzd.cateringgroupbuy.Adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Created by ldhns on 2018/1/8.
 */

public class HomepageFragment extends Fragment{

    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    private View xiaochi,chayin,kuaican,lingshi,huoguo,zizhu,shaokao,meishi;
    private MyDBHelper dbHelper;
    private ImageView location;
    TextView location_text;
    EditText search;
    String cityName;
    RestaurantAdapter adapter;

    //private MyLocationListener myLocationListener;
   // public LocationClient mLocationClient = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);

        cityName = getActivity().getIntent().getStringExtra("cityname");
        dbHelper = new MyDBHelper(getActivity(),"RestStore.db",null,1);
        initRestaurant();
        //insertRest();
        adapter = new RestaurantAdapter(getActivity(),R.layout.restaurant_item,restaurantList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);
                //获取当前选择的餐厅，将该餐厅作为对象传递到下一个页面
                Intent intent = new Intent(getActivity(),InfoRestActivity.class);
                intent.putExtra("restaurant_data", restaurant);
                //由于Restaurant 类实现了Serializable 接口，所以才可以这样写。
                startActivity(intent);

                //Toast.makeText(RestaurantActivity.this,restaurant.getImageId(),Toast.LENGTH_LONG).show();
            }
        });

        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Button button = (Button) getActivity().findViewById(R.id.testbt);

        //cityName = getActivity().getIntent().getStringExtra("cityname");
        location_text = (TextView) getActivity().findViewById(R.id.text_location);
        //location_text.setText(cityName);

        //回车键搜索
        search = (EditText) getActivity().findViewById(R.id.homepage_search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    String text = search.getText().toString();
                    String Text = text.substring(0,text.length()-1);
                    final  List<Restaurant> restList = new ArrayList<Restaurant>();
                    for(int i=0;i<restaurantList.size();i++) {
                        String restayrantName = restaurantList.get(i).getName();
                        String address = restaurantList.get(i).getAddress();
                        int imageId = restaurantList.get(i).getImageId();
                        String leibie = restaurantList.get(i).getLeibie();

                        if (restayrantName.contains(Text) || address.contains(Text) || leibie.contains(Text)) {
                            // searched = true;
                            //restaurantList.clear();
                            Restaurant rest = new Restaurant(restayrantName, address, leibie, imageId);
                            restList.add(rest);

                        }
                    }
                    adapter = new RestaurantAdapter(getActivity(),R.layout.restaurant_item,restList);
                    ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    //添加点击事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            Restaurant restaurant = restList.get(position);
                            Intent intent = new Intent(getActivity(),InfoRestActivity.class);
                            intent.putExtra("restaurant_data", restaurant);//由于Restaurant 类实现了Serializable 接口，所以才可以这样写。
                            startActivity(intent);
                        }
                    });
                }
                return false;
            }
        });

        xiaochi = (View) getActivity().findViewById(R.id.tuangou);
        chayin = (View) getActivity().findViewById(R.id.dingzuo);
        kuaican = (View) getActivity().findViewById(R.id.kuaican);
        lingshi = (View) getActivity().findViewById(R.id.waimai);
        huoguo = (View) getActivity().findViewById(R.id.huoguo);
        zizhu = (View) getActivity().findViewById(R.id.zizhu);
        shaokao = (View) getActivity().findViewById(R.id.shaokao);
        meishi = (View) getActivity().findViewById(R.id.youhui);

        location = (ImageView) getActivity().findViewById(R.id.btn_getlocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivityForResult(intent,1);
                //getActivity().finish();
            }
        });

        xiaochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "小吃";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        chayin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "茶饮";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        kuaican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "快餐";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        lingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "零食";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        huoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "火锅";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        zizhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "自助";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        shaokao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "烧烤";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
        meishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RestaurantActivity.class);
                String category = "美食";
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端
            restaurantList.clear();
            initRestaurant();
            adapter = new RestaurantAdapter(getActivity(),R.layout.restaurant_item,restaurantList);
            ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:{
                if(resultCode == RESULT_OK){
                    String city =data.getStringExtra("data_return");
                    location_text.setText(city.substring(0,city.length()-1));
                    break;
                }
            }
            default:
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        search.getText().clear();
        restaurantList.clear();
        initRestaurant();
        adapter = new RestaurantAdapter(getActivity(),R.layout.restaurant_item,restaurantList);
        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }


    private void initRestaurant(){
        Restaurant liangpinpuzi = new Restaurant("良品铺子","博大广场","零食","30",R.drawable.lingshi_liangpinpuzi);
        restaurantList.add(liangpinpuzi);
        Restaurant yangmingyu = new Restaurant("杨铭宇黄焖鸡米饭","星光广场","快餐","16",R.drawable.kuaican_yangmingyu);
        restaurantList.add(yangmingyu);
        Restaurant nduoshousi = new Restaurant("N多寿司","花园路","小吃","22",R.drawable.xiaochi_nduoshousi);
        restaurantList.add(nduoshousi);
        Restaurant pengdekai = new Restaurant("彭德楷黄焖鸡米饭","星光广场","快餐","17",R.drawable.kuaican_pengdekai);
        restaurantList.add(pengdekai);
        Restaurant kuaileningmeng = new Restaurant("快乐柠檬","解放路","茶饮","12",R.drawable.chayin_kuaileningmeng);
        restaurantList.add(kuaileningmeng);
        Restaurant kendeji = new Restaurant("肯德基","海岸城","快餐","30",R.drawable.kuaican_kendeji);
        restaurantList.add(kendeji);

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
