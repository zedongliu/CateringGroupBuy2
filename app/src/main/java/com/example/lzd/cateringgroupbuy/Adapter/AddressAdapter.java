package com.example.lzd.cateringgroupbuy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzd.cateringgroupbuy.MyAddress;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

import java.util.List;

/**
 * Created by ldhns on 2018/3/17.
 */

public class AddressAdapter extends ArrayAdapter<MyAddress> {
    private int resourceId;

    public AddressAdapter(Context context, int textViewResourceId, List<MyAddress> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        MyAddress address = getItem(position);     //获取当前项的restaurant实例
        //View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        //ListView需要优化，getView()方法每次都将布局重新加载一遍，在快速滚动时会成为性能的瓶颈，导致闪退
        View view;
        ViewHolder viewHolder;
        //converView参数用于将之前加载好的布局进行缓存，以便进行复用
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

            viewHolder = new ViewHolder();
            viewHolder.name_phone = (TextView) view.findViewById(R.id.name_phone);
            viewHolder.address_room = view.findViewById(R.id.text_address);

            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else{
            view = converView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }

        viewHolder.name_phone.setText(address.getName()+" "+address.getPhone());
        viewHolder.address_room.setText(address.getAddress()+" "+address.getRoom());

        return view;
    }

    class ViewHolder{
        TextView name_phone;
        TextView address_room;

    }
}
