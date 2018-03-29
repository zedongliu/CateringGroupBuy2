package com.example.lzd.cateringgroupbuy.Homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.OrderClass;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;
import com.example.lzd.cateringgroupbuy.Adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldhns on 2018/3/8.
 */

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private ImageView back,search;
    private EditText searchText;
    private MyDBHelper dbHelper;
    RestaurantAdapter adapter;
    String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reataurant);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        searchText = (EditText)findViewById(R.id.edit_search);
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    searchRestEnter();
                }
                return false;
            }
        });

        search = (ImageView) findViewById(R.id.btn_search);
        search.setOnClickListener(this);

        Category = (String) getIntent().getStringExtra("category");

        dbHelper = new MyDBHelper(this,"RestStore.db",null,1);


        initRestaurant();
        adapter = new RestaurantAdapter(this,R.layout.restaurant_item,restaurantList);
        ListView listView = (ListView) findViewById(R.id.list_restaurant);
        listView.setAdapter(adapter);
        //添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);
                //获取当前选择的餐厅，将该餐厅作为对象传递到下一个页面
                Intent intent = new Intent(RestaurantActivity.this,InfoRestActivity.class);
                intent.putExtra("restaurant_data", restaurant);
                //由于Restaurant 类实现了Serializable 接口，所以才可以这样写。
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view){
        if(view.getId()== R.id.back)
        {
            this.finish();
        }
        if(view.getId()==R.id.btn_search){
            searchRest();
        }
    }

    //回车键搜索餐厅
    public void searchRestEnter(){
        String text = searchText.getText().toString();
        String Text = text.substring(0,text.length()-1);
        final List<Restaurant> restList = new ArrayList<Restaurant>();
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
        adapter = new RestaurantAdapter(this,R.layout.restaurant_item,restList);
        ListView listView = (ListView) findViewById(R.id.list_restaurant);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Restaurant restaurant = restList.get(position);
                Intent intent = new Intent(RestaurantActivity.this,InfoRestActivity.class);
                intent.putExtra("restaurant_data", restaurant);
                startActivity(intent);
            }
        });
    }

    //按钮搜索餐厅
    public void searchRest(){
        String text = searchText.getText().toString();
        if(text.isEmpty()){
            Toast.makeText(this,"请输入搜索内容",Toast.LENGTH_SHORT).show();
        }else{
            final List<Restaurant> restList = new ArrayList<Restaurant>();
            for(int i=0;i<restaurantList.size();i++) {
                String restayrantName = restaurantList.get(i).getName();
                String address = restaurantList.get(i).getAddress();
                int imageId = restaurantList.get(i).getImageId();
                String leibie = restaurantList.get(i).getLeibie();

                if (restayrantName.contains(text) || address.contains(text)||leibie.contains(text)) {
                    // searched = true;
                    //restaurantList.clear();
                    Restaurant rest = new Restaurant(restayrantName, address, leibie, imageId);
                    restList.add(rest);

                }
            }
            adapter = new RestaurantAdapter(this,R.layout.restaurant_item,restList);
            ListView listView = (ListView) findViewById(R.id.list_restaurant);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Restaurant restaurant = restList.get(position);
                    Intent intent = new Intent(RestaurantActivity.this,InfoRestActivity.class);
                    intent.putExtra("restaurant_data", restaurant);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        searchText.getText().clear();
        restaurantList.clear();
        initRestaurant();
        adapter = new RestaurantAdapter(this,R.layout.restaurant_item,restaurantList);
        ListView listView = (ListView) findViewById(R.id.list_restaurant);
        listView.setAdapter(adapter);
        //添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);
                //获取当前选择的餐厅，将该餐厅作为对象传递到下一个页面
                Intent intent = new Intent(RestaurantActivity.this,InfoRestActivity.class);
                intent.putExtra("restaurant_data", restaurant);
                //由于Restaurant 类实现了Serializable 接口，所以才可以这样写。
                startActivity(intent);
            }
        });
    }

    public void initRestaurant(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //扫描数据库,将当前类别的餐厅信息放入list
        Cursor cursor = db.rawQuery("select * from restTable where category='"+Category+"'",null);

        while (cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String leibie = cursor.getString(cursor.getColumnIndex("category"));
            String average = cursor.getString(cursor.getColumnIndex("average"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            Restaurant rest = new Restaurant(name,address,leibie,average,imageId);

            restaurantList.add(rest);//把数据库的每一行加入数组中
        }
        //int c =orderList.size();
        //Toast.makeText(getActivity(), String.valueOf(c),Toast.LENGTH_SHORT).show();
    }

    /*  //该方法由于未使用数据库已经被替换
    private void initRestaurant1(){
        switch (Category){
            case "小吃" :{
                Restaurant maladaodi = new Restaurant("麻辣到底","博大广场","小吃",R.drawable.xiaochi_maladaodi);
                restaurantList.add(maladaodi);
                Restaurant nduoshousi = new Restaurant("N多寿司","花园路","小吃",R.drawable.xiaochi_nduoshousi);
                restaurantList.add(nduoshousi);
                Restaurant yabo = new Restaurant("绝味鸭脖","花园路","小吃",R.drawable.xiaochi_yabo);
                restaurantList.add(yabo);
                Restaurant roujiamo = new Restaurant("潼关肉夹馍","星光广场","小吃",R.drawable.xiaochi_roujiamo);
                restaurantList.add(roujiamo);
                Restaurant bapopo = new Restaurant("八婆婆","星光广场","小吃",R.drawable.xiaochi_bapopo);
                restaurantList.add(bapopo);
                break;
            }
            case "茶饮" :{
                Restaurant kuaileningmeng = new Restaurant("快乐柠檬","解放路","茶饮",R.drawable.chayin_kuaileningmeng);
                restaurantList.add(kuaileningmeng);
                Restaurant yidiandian = new Restaurant("一点点","解放路","茶饮",R.drawable.chayin_yidiandian);
                restaurantList.add(yidiandian);
                Restaurant xingbake = new Restaurant("星巴克","花园路","茶饮",R.drawable.chayin_xingbake);
                restaurantList.add(xingbake);
                Restaurant duke = new Restaurant("CoCo都可","博大","茶饮",R.drawable.chayin_duke);
                restaurantList.add(duke);
                Restaurant shijie = new Restaurant("世界茶饮","海岸城","茶饮",R.drawable.chayin_shijiechayin);
                restaurantList.add(shijie);
                Restaurant tai = new Restaurant("太平洋咖啡","星光广场","茶饮",R.drawable.chayin_taipingyang);
                restaurantList.add(tai);
                break;
            }
            case "快餐" :{
                Restaurant yangmingyu = new Restaurant("杨铭宇黄焖鸡米饭","星光广场","快餐",R.drawable.kuaican_yangmingyu);
                restaurantList.add(yangmingyu);
                Restaurant kendeji = new Restaurant("肯德基","海岸城","快餐",R.drawable.kuaican_kendeji);
                restaurantList.add(kendeji);
                Restaurant bishengke = new Restaurant("必胜客餐厅","蠡湖大道","快餐",R.drawable.kuaican_bishengke);
                restaurantList.add(bishengke);
                Restaurant pengdekai = new Restaurant("彭德楷黄焖鸡米饭","星光广场","快餐",R.drawable.kuaican_pengdekai);
                restaurantList.add(pengdekai);
                Restaurant dekeshi = new Restaurant("德克士","万象城","快餐",R.drawable.kuaican_dekeshi);
                restaurantList.add(dekeshi);
                Restaurant majiao = new Restaurant("麻椒小厨","花园路","快餐",R.drawable.kuaican_majiao);
                restaurantList.add(majiao);
                break;
            }
            case "零食" :{
                Restaurant liangpinpuzi = new Restaurant("良品铺子","博大广场","零食",R.drawable.lingshi_liangpinpuzi);
                restaurantList.add(liangpinpuzi);
                Restaurant laopo = new Restaurant("老婆大人","解放路","零食",R.drawable.lingshi_laopo);
                restaurantList.add(laopo);
                Restaurant yikou = new Restaurant("一口零食","花园路","零食",R.drawable.lingshi_yikou);
                restaurantList.add(yikou);
                Restaurant feichang = new Restaurant("非常食客","万象城","零食",R.drawable.lingshi_feichangshike);
                restaurantList.add(feichang);
                Restaurant duocai = new Restaurant("多彩零食","海岸城","零食",R.drawable.lingshi_duocai);
                restaurantList.add(duocai);
                Restaurant qiyueqi = new Restaurant("七月初七","星光广场","零食",R.drawable.lingshi_qiyueqi);
                restaurantList.add(qiyueqi);
                break;
            }
            case "火锅" :{
                Restaurant banu = new Restaurant("巴奴毛肚火锅","博大广场","零食",R.drawable.huoguo_banu);
                restaurantList.add(banu);
                Restaurant bashu = new Restaurant("巴蜀印象","星光广场","火锅",R.drawable.huoguo_bashuyinxiang);
                restaurantList.add(bashu);
                Restaurant haidilao = new Restaurant("海底捞","海岸城","火锅",R.drawable.huoguo_haidilao);
                restaurantList.add(haidilao);
                Restaurant xinladao = new Restaurant("新辣道","万象城","火锅",R.drawable.huoguo_xinladao);
                restaurantList.add(xinladao);
                Restaurant naxi = new Restaurant("纳西印象","博大广场","火锅",R.drawable.huoguo_naxiyinxiang);
                restaurantList.add(naxi);
                break;
            }case "自助" :{
                Restaurant huanle = new Restaurant("欢乐牧场","博大广场","自助餐",R.drawable.zizhu_huanlemuchang);
                restaurantList.add(huanle);
                Restaurant jinhansen = new Restaurant("金汉森自助餐","星光广场","自助餐",R.drawable.zizhu_jinhansen);
                restaurantList.add(jinhansen);
                Restaurant mulinsen = new Restaurant("木林森自助餐","海岸城","自助餐",R.drawable.zizhu_mulinsen);
                restaurantList.add(mulinsen);
                Restaurant jinqianbao = new Restaurant("金钱豹自助餐","万象城","自助餐",R.drawable.zizhu_jinqianbao);
                restaurantList.add(jinqianbao);
                Restaurant shenhai = new Restaurant("深海800米","博大广场","自助餐",R.drawable.zizhu_shenhai);
                restaurantList.add(shenhai);
                break;
            }
            case "烧烤" :{
                Restaurant bachu = new Restaurant("巴楚烧烤","博大广场","烧烤",R.drawable.shaokao_bachu);
                restaurantList.add(bachu);
                Restaurant muwu = new Restaurant("木屋烧烤","海岸城","烧烤",R.drawable.shaokao_muwu);
                restaurantList.add(muwu);
                Restaurant mengkaer = new Restaurant("蒙卡尔烧烤","星光广场","烧烤",R.drawable.shaokao_mengkaer);
                restaurantList.add(mengkaer);
                Restaurant muxaingran = new Restaurant("木香然烧烤","万象城","烧烤",R.drawable.shaokao_muxiangran);
                restaurantList.add(muxaingran);
                Restaurant yuangu = new Restaurant("源古烧烤","博大广场","烧烤",R.drawable.shaokao_yuangu);
                restaurantList.add(yuangu);
                Restaurant haohaichuan = new Restaurant("好HIGH串烧烤","博大广场","烧烤",R.drawable.shaokao_haohaichuan);
                restaurantList.add(haohaichuan);
                break;
            }
            case "美食" :{
                Restaurant chaoke = new Restaurant("潮客","博大广场","美食",R.drawable.meishi_chaoke);
                restaurantList.add(chaoke);
                Restaurant waipo = new Restaurant("外婆家","星光广场","美食",R.drawable.meishi_waipojia);
                restaurantList.add(waipo);
                Restaurant matou = new Restaurant("重庆渔码头","博大广场","美食",R.drawable.meishi_cqyumatou);
                restaurantList.add(matou);
                Restaurant luyu = new Restaurant("炉鱼","海岸城","美食",R.drawable.meishi_luyu);
                restaurantList.add(luyu);
                Restaurant zhanggui = new Restaurant("掌柜的店","博大广场","美食",R.drawable.meishi_zhanggui);
                restaurantList.add(zhanggui);
                Restaurant xiaoyu = new Restaurant("小雨餐厅","万象城","美食",R.drawable.meishi_xiaoyu);
                restaurantList.add(xiaoyu);

                break;
            }
            default:
                break;

        }


    }
    */
}
