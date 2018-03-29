package com.example.lzd.cateringgroupbuy.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Homepage.InfoRestActivity;
import com.example.lzd.cateringgroupbuy.MyDBHelper;
import com.example.lzd.cateringgroupbuy.Adapter.OrderAdapter;
import com.example.lzd.cateringgroupbuy.OrderClass;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ldhns on 2018/3/11.
 */

public class OrderFragment extends Fragment {

    private List<OrderClass> orderList = new ArrayList<OrderClass>();
    private MyDBHelper dbHelper;
    ListView orderListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container,false);
        dbHelper = new MyDBHelper(getActivity(),"OrderStore.db",null,1);
        initOrder();
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        OrderAdapter adapter = new OrderAdapter(getActivity(),R.layout.order_item,orderList);
        orderListView = (ListView) getActivity().findViewById(R.id.listview_order);
        orderListView.setAdapter(adapter);
       // orderList.clear();
        //initOrder();
        //添加点击事件
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                OrderClass orderClass = orderList.get(position);
                //获取当前选择的餐厅，将该餐厅作为对象传递到下一个页面
                Intent intent = new Intent(getActivity(),InfoRestActivity.class);
                intent.putExtra("restaurant_data", orderClass.getRestaurant());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            orderList.clear();
            initOrder();
            OrderAdapter adapter = new OrderAdapter(getActivity(),R.layout.order_item,orderList);
            orderListView = (ListView) getActivity().findViewById(R.id.listview_order);
            orderListView.setAdapter(adapter);
        }
    }

    public void initOrder(){
        SharedPreferences sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //扫描数据库,将当前用户的订单信息放入orderlist
        Cursor cursor = db.rawQuery("select * from orderTable where user="+sp.getString("username", ""),null);
       /* Long c =cursor.getLong(0);
        Toast.makeText(getActivity(), String.valueOf(c),Toast.LENGTH_SHORT).show();*/
        while (cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String leibie = cursor.getString(cursor.getColumnIndex("leibie"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            Restaurant rest = new Restaurant(name,address,leibie,imageId);

            String foodtype = cursor.getString(cursor.getColumnIndex("foodtype"));
            int num = cursor.getInt(cursor.getColumnIndex("num"));
            String totalmoney = cursor.getString(cursor.getColumnIndex("money"));

            OrderClass order = new OrderClass(rest,foodtype,totalmoney,num);    //存一个条目的数据
            orderList.add(order);//把数据库的每一行加入数组中
        }
       /* int c =orderList.size(); //订单数量
        Toast.makeText(getActivity(), String.valueOf(c),Toast.LENGTH_SHORT).show();*/
    }

}
