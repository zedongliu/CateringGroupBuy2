package com.example.lzd.cateringgroupbuy.Mine;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

/**
 * Created by ldhns on 2018/3/13.
 */

public class NewAddressActivity extends Activity implements View.OnClickListener{

    ImageView back;
    EditText name,phone,address,room;
    Button yes;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaddress);

        name = (EditText) findViewById(R.id.new_name);
        phone = (EditText) findViewById(R.id.new_phone);
        address = (EditText) findViewById(R.id.new_address);
        room = (EditText) findViewById(R.id.new_room);

        back = (ImageView) findViewById(R.id.btn_back_newaddress);
        yes = (Button) findViewById(R.id.btn_yes);
        back.setOnClickListener(this);
        yes.setOnClickListener(this);

        dbHelper = new MyDBHelper(this,"AddressStore.db",null,1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_newaddress:{
                finish();
                break;
            }
            case R.id.btn_yes:{
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                String Address = address.getText().toString();
                String Room = room.getText().toString();
                if(Name.isEmpty()){
                    Toast.makeText(this,"请输入联系人",Toast.LENGTH_SHORT).show();
                }
                else if(Phone.isEmpty()){
                    Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }
                 else if(Address.isEmpty()){
                    Toast.makeText(this,"请输入地址",Toast.LENGTH_SHORT).show();
                }
                 else if(Room.isEmpty()){
                    Toast.makeText(this,"请输入门牌号",Toast.LENGTH_SHORT).show();
                }else{
                    if(addAddress(Name,Phone,Address,Room)) {
                        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            }default:{
                break;
            }
        }
    }

    //向数据库插入数据
    public boolean addAddress(String name, String phone, String address, String room){
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名

        ContentValues values = new ContentValues();
        values.put("user",sp.getString("username", ""));
        values.put("name",name);
        values.put("phone",phone);
        values.put("address",address);
        values.put("room",room);

        db.insert("addressTable",null,values);
        db.close();
        return true;
    }

}
