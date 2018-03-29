package com.example.lzd.cateringgroupbuy.Mine;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Login.MainActivity;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;

/**
 * Created by ldhns on 2018/3/14.
 */

public class ChangePswActivity extends Activity implements View.OnClickListener{

    private ImageView back;
    private Button change;
    private EditText yuan,xin,xin2;
    private MyDBHelper dbHelper;

    String yuanpsw ;
    String xinpsw ;
    String xinpsw2;
    String userConut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepsw);

        back = (ImageView) findViewById(R.id.btn_back_changepsw);
        back.setOnClickListener(this);

        change = (Button) findViewById(R.id.btn_change);
        change.setOnClickListener(this);

        yuan = (EditText) findViewById(R.id.yuan_psw);
        xin = (EditText) findViewById(R.id.new_psw);
        xin2 = (EditText) findViewById(R.id.new_psw2);

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_changepsw:{
                finish();
                break;
            }
            case R.id.btn_change:{
                yuanpsw = yuan.getText().toString();
                xinpsw = xin.getText().toString();
                xinpsw2 = xin2.getText().toString();
                if(yuanpsw.isEmpty()){
                    Toast.makeText(this,"请输入原密码",Toast.LENGTH_SHORT).show();
                }
                if(xinpsw.isEmpty()){
                    Toast.makeText(this,"请输入新密码",Toast.LENGTH_SHORT).show();
                }
                if(xinpsw2.isEmpty()){
                    Toast.makeText(this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                }
                if(!xinpsw.equals(xinpsw2)){
                    Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
                }else {
                    if (!CheckIsRightPsw(yuanpsw)) {
                        Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
                    }
                    if (CheckIsRightPsw(yuanpsw)) {
                        if (ChangeDone(userConut, xinpsw)) {
                            Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePswActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                break;
            }
        }
    }

    //向数据库插入数据
    public boolean ChangeDone(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        //update <表名> set <列名=更新值> [where <更新条件>]
        //update tongxunlu set 年龄=18 where 姓名=’蓝色小名’
        String sql = "update userData set password = "+password+" where name="+username;

        db.execSQL(sql);

        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }

    //检验原密码是否正确
    public boolean CheckIsRightPsw(String value){
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名
        userConut = sp.getString("username", "");
//name ="+userConut+"and
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where password =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
}
