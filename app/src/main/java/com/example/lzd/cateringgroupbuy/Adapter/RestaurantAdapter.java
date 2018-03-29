package com.example.lzd.cateringgroupbuy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.Restaurant;
import com.example.lzd.cateringgroupbuy.R;

import java.util.List;

/**
 * Created by ldhns on 2018/3/8.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurant>{
    private int resourceId;

    public RestaurantAdapter(Context context,int textViewResourceId,List<Restaurant> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View converView,ViewGroup parent){
        Restaurant restaurant = getItem(position);     //获取当前项的restaurant实例
        //View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        //ListView需要优化，getView()方法每次都将布局重新加载一遍，在快速滚动时会成为性能的瓶颈，导致闪退
        View view;
        ViewHolder viewHolder;
        //converView参数用于将之前加载好的布局进行缓存，以便进行复用
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

            viewHolder = new ViewHolder();
            viewHolder.restaurantImage = (ImageView) view.findViewById(R.id.restaurant_image);
            viewHolder.restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            viewHolder.restaurantAddress = (TextView) view.findViewById(R.id.restaurant_address);
            viewHolder.restaurantLeibie = view.findViewById(R.id.restaurant_leibie);
            viewHolder.restaurantRenjun = view.findViewById(R.id.restaurant_renjun);

            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else{
            view = converView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }

        viewHolder.restaurantImage.setImageResource(restaurant.getImageId());
        viewHolder.restaurantName.setText(restaurant.getName());
        viewHolder.restaurantAddress.setText(restaurant.getAddress());
        viewHolder.restaurantLeibie.setText(restaurant.getLeibie());
        viewHolder.restaurantRenjun.setText("人均："+ restaurant.getAverage());
        return view;
    }

    class ViewHolder{
        ImageView restaurantImage;
        TextView restaurantName;
        TextView restaurantAddress;
        TextView restaurantLeibie;
        TextView restaurantRenjun;
    }
}
