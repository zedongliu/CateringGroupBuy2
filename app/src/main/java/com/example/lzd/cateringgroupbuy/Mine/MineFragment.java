package com.example.lzd.cateringgroupbuy.Mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Login.MainActivity;
import com.example.lzd.cateringgroupbuy.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ldhns on 2018/3/11.
 */

public class MineFragment extends Fragment {

    String userConut;
    TextView username,usercount;
    View address,exit,changePsw,eleme,wallet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        //userConut = getActivity().getIntent().getStringExtra("username");
        SharedPreferences sp =getActivity().getSharedPreferences("info", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名
        userConut = sp.getString("username", "");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        username = (TextView) getActivity().findViewById(R.id.user_name);
        usercount = (TextView) getActivity().findViewById(R.id.user_count);
        usercount.setText(userConut);

        address = (View) getActivity().findViewById(R.id.btn_address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RecvAddressActivity.class);
                startActivity(intent);
            }
        });

        eleme = (View) getActivity().findViewById(R.id.btn_eleme);
        eleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ElemeActivity.class);
                startActivity(intent);
            }
        });

        wallet = (View)getActivity().findViewById(R.id.btn_mywallet);
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        exit = (View) getActivity().findViewById(R.id.btn_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog();
            }
        });

        changePsw = (View) getActivity().findViewById(R.id.btn_changepsw);
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ChangePswActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        //normalDialog.setIcon(R.drawable.icon_dialog);
        //normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("确认退出当前登录账号?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getActivity().getSharedPreferences("logstate", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putBoolean("state",false);//登录状态设置为false
                        ed.commit();

                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

}
