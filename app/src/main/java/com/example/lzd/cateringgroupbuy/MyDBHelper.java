package com.example.lzd.cateringgroupbuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ldhns on 2018/1/6.
 * 创建数据库
 */

public class MyDBHelper extends SQLiteOpenHelper {

    //用户信息表
    public static final String CREATE_USERDATA ="create table userData(" +
            "id integer primary key autoincrement,"
            +"nickname text,"
            +"name text,"
            +"password text)";

    //订单信息表
    public static final String CREATE_ORDERTABLE ="create table orderTable("
            + "id integer primary key autoincrement,"
            +"user text,"//用户
            +"name text,"
            +"address text,"
            +"leibie text,"
            +"imageId integer,"
            +"foodtype text,"
            +"num integer,"
            +"money text)";

    //收货地址表
    public static final String CREATE_ADDRESS="create table addressTable("
            +"id integer primary key autoincrement,"
            +"user text,"
            +"name text,"
            +"phone text,"
            +"address text,"
            +"room text)";

    //餐厅信息表
    public static final String CREATE_RESTAURANT = "create table restTable("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"address text,"
            +"average text,"
            +"category text,"
            +"imageId integer)";

    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }

    public  void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERDATA);
        db.execSQL(CREATE_ORDERTABLE);
        db.execSQL(CREATE_ADDRESS);
        db.execSQL(CREATE_RESTAURANT);

    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //onCreate(db);
    }



}
