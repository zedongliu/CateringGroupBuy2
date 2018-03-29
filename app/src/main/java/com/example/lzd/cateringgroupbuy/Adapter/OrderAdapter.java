package com.example.lzd.cateringgroupbuy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.lzd.cateringgroupbuy.OrderClass;
import com.example.lzd.cateringgroupbuy.R;

/**
 * Created by ldhns on 2018/3/16.
 */

public class OrderAdapter extends ArrayAdapter<OrderClass> {
    private int resourceId;

    public OrderAdapter(Context context,int textViewResourceId,List<OrderClass> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        OrderClass order = getItem(position);
        //View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        //ListView需要优化，getView()方法每次都将布局重新加载一遍，在快速滚动时会成为性能的瓶颈，导致闪退
        View view;
        ViewHolder viewHolder;
        //converView参数用于将之前加载好的布局进行缓存，以便进行复用
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.restaurantImage = (ImageView) view.findViewById(R.id.order_rest_image);
            viewHolder.orderNameType = (TextView) view.findViewById(R.id.order_name);
            viewHolder.num = (TextView) view.findViewById(R.id.order_num);
            viewHolder.totalMoney = view.findViewById(R.id.order_totalmoney);

            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else{
            view = converView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }

        viewHolder.restaurantImage.setImageResource(order.getRestaurant().getImageId());
        viewHolder.orderNameType.setText(order.getRestaurant().getName()+" 单人餐 "+order.getFoodtype());
        viewHolder.num.setText("数量："+Integer.toString(order.getNum()));
        viewHolder.totalMoney.setText("总价："+order.getTotalmoney().substring(2,order.getTotalmoney().length()));


        return view;
    }

    class ViewHolder{
        ImageView restaurantImage;
        TextView orderNameType;
        TextView num;
        TextView totalMoney;
    }
}
