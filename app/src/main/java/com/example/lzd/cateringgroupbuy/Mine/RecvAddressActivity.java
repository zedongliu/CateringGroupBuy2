package com.example.lzd.cateringgroupbuy.Mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lzd.cateringgroupbuy.Adapter.AddressAdapter;
import com.example.lzd.cateringgroupbuy.Adapter.OrderAdapter;
import com.example.lzd.cateringgroupbuy.MyAddress;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.OrderClass;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldhns on 2018/3/13.
 */

public class RecvAddressActivity extends Activity implements View.OnClickListener{

    ImageView back,add,edit;
    private MyDBHelper dbHelper;
    private List<MyAddress> addressList = new ArrayList<MyAddress>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        back = (ImageView) findViewById(R.id.btn_back_address);
        add = (ImageView) findViewById(R.id.btn_add_address);
       // edit = (ImageView) findViewById(R.id.btn_edit);

        back.setOnClickListener(this);
        add.setOnClickListener(this);
       // edit.setOnClickListener(this);

        dbHelper = new MyDBHelper(this,"AddressStore.db",null,1);
        initAddress();
        AddressAdapter adapter = new AddressAdapter(this,R.layout.address_item,addressList);
        ListView addressListView = (ListView) findViewById(R.id.listview_address);
        addressListView.setAdapter(adapter);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        addressList.clear();
        initAddress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_address:{
                finish();
                break;
            }
            case R.id.btn_add_address:{
                Intent intent = new Intent(RecvAddressActivity.this,NewAddressActivity.class);
                startActivity(intent);
                break;
            }default:{
                break;
            }
        }
    }

    public void initAddress(){
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //扫描数据库,将当前用户的地址信息放入addresslist
        Cursor cursor = db.rawQuery("select * from addressTable where user="+sp.getString("username", ""),null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String room = cursor.getString(cursor.getColumnIndex("room"));

            MyAddress myAddress = new MyAddress(name,phone,address,room);    //存一个条目的数据
            addressList.add(myAddress);//把数据库的每一行加入数组中
        }

    }

}
