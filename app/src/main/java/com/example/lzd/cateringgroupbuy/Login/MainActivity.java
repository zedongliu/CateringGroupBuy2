package com.example.lzd.cateringgroupbuy.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Homepage.HomePageActivity;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDBHelper dbHelper;
    private EditText username;
    private EditText userpassword;
    private TextView register;
    private Button log;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.registerbtn) ;
        register.setOnClickListener(this);

        username=(EditText)findViewById(R.id.logid);
        userpassword=(EditText)findViewById(R.id.logpsw);

        log = (Button)findViewById(R.id.logBtn) ;
        log.setOnClickListener(this);

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);

        //readAccount();
    }

    //读取保存在本地的用户名和密码,并自动登录
    public void readAccount() {

        //创建SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

        //获得保存在SharedPredPreferences中的用户名和密码
        String name = sp.getString("username", "");
        String password = sp.getString("password", "");

        //在用户名和密码的输入框中显示用户名和密码
        username.setText(name);
        userpassword.setText(password);

        //自动登录
        if(login(name,password)){
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            //intent.putExtra("username",name);
            startActivity(intent);
        }
    }

    //点击注册按钮进入注册页面
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.registerbtn) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.logBtn){

            //获得用户输入的用户名和密码
            String userName=username.getText().toString();
            String passWord=userpassword.getText().toString();

            if (login(userName,passWord)) {
                //创建sharedPreference对象，info表示文件名，MODE_PRIVATE表示访问权限为私有的
                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences sp2 = getSharedPreferences("logstate", MODE_PRIVATE);
                //获得sp的编辑器
                SharedPreferences.Editor ed = sp.edit();
                SharedPreferences.Editor ed2 = sp2.edit();
                //以键值对的显示将用户名和密码保存到sp中
                ed.putString("username", userName);
                ed.putString("password", passWord);
                //登录状态设置为true
                ed2.putBoolean("state",true);

                ed.commit();
                ed2.commit();
                //Toast.makeText(MainActivity.this, "登陆成功（ZY，111）", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                intent.putExtra("username",userName);
                startActivity(intent);
                finish();
            }
            else if(userName.isEmpty() || passWord.isEmpty()){
                Toast.makeText(MainActivity.this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //验证登录
    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
