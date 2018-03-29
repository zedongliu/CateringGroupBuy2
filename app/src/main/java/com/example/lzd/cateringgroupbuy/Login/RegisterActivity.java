package com.example.lzd.cateringgroupbuy.Login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;

/**
 * Created by ldhns on 2018/1/6.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDBHelper dbHelper;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button)findViewById(R.id.registerBtn) ;
        register.setOnClickListener(this);

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.registerBtn) {
            //SQLiteDatabase db=dbHelper.getWritableDatabase();

            EditText editText3 = (EditText) findViewById(R.id.registerid);
            EditText editText4 = (EditText) findViewById(R.id.registerpsw);
            EditText editText5 = (EditText) findViewById(R.id.registerpsw2);
            String newname = editText3.getText().toString();
            String password = editText4.getText().toString();
            String password2 = editText5.getText().toString();
            if(newname.isEmpty()||password.isEmpty()||password2.isEmpty()){
                Toast.makeText(this, "请输入注册信息", Toast.LENGTH_SHORT).show();
            }
            else if(!password.equals(password2)){
                Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            }
            else if (CheckIsDataAlreadyInDBorNot(newname)) {
                Toast.makeText(this, "该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
            } else {

                if (register(newname, password)) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
    //向数据库插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        /*String sql = "insert into userData(name,password) value(?,?)";
        Object obj[]={username,password};
        db.execSQL(sql,obj);*/

        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("userData",null,values);

        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }
    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

}
