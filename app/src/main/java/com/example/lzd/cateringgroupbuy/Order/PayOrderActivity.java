package com.example.lzd.cateringgroupbuy.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Homepage.HomePageActivity;
import com.example.lzd.cateringgroupbuy.R;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldhns on 2018/3/12.
 */

public class PayOrderActivity extends Activity implements View.OnClickListener{

    private TextView money;
    private ImageView back,wotuan,weixin,alipay,yinlian,backhome;
    String Money;
    View pay;

    private boolean Wotuan = true,Weixin = false, Alipay = false,Yinlian = false;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message message)
        {
            switch (message.what)
            {
                case R.id.wotuan:{
                    wotuan.setImageResource(R.drawable.selected);
                    weixin.setImageResource(R.drawable.disselected);
                    alipay.setImageResource(R.drawable.disselected);
                    yinlian.setImageResource(R.drawable.disselected);
                    break;
                }
                case R.id.weixin:{
                    wotuan.setImageResource(R.drawable.disselected);
                    weixin.setImageResource(R.drawable.selected);
                    alipay.setImageResource(R.drawable.disselected);
                    yinlian.setImageResource(R.drawable.disselected);
                    break;
                }
                case R.id.alipay:{
                    wotuan.setImageResource(R.drawable.disselected);
                    weixin.setImageResource(R.drawable.disselected);
                    alipay.setImageResource(R.drawable.selected);
                    yinlian.setImageResource(R.drawable.disselected);
                    break;
                }
                case R.id.yinlian:{
                    wotuan.setImageResource(R.drawable.disselected);
                    weixin.setImageResource(R.drawable.disselected);
                    alipay.setImageResource(R.drawable.disselected);
                    yinlian.setImageResource(R.drawable.selected);
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);

        Money =  getIntent().getStringExtra("food_money").substring(2,getIntent().getStringExtra("food_money").length());

        money = (TextView)findViewById(R.id.pay_money);
        money.setText("¥ "+Money);

        back = (ImageView) findViewById(R.id.back_pay_order);
        back.setOnClickListener(this);

        backhome = (ImageView) findViewById(R.id.btn_backhome);
        backhome.setOnClickListener(this);

        wotuan = (ImageView) findViewById(R.id.wotuan);
        wotuan.setOnClickListener(this);

        weixin = (ImageView) findViewById(R.id.weixin);
        weixin.setOnClickListener(this);

        alipay = (ImageView) findViewById(R.id.alipay);
        alipay.setOnClickListener(this);

        yinlian = (ImageView) findViewById(R.id.yinlian);
        yinlian.setOnClickListener(this);

        pay = (View) findViewById(R.id.comfir_pay);
        pay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_pay_order:{
                finish();
                break;
            }
            case R.id.btn_backhome:{
               Intent intent = new Intent(PayOrderActivity.this, HomePageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.wotuan:{
                Wotuan = true;
                Weixin = false;
                Alipay = false;
                Yinlian = false;
                sendUpdateMessage(R.id.wotuan);
                break;
            }
            case R.id.weixin:{
                Wotuan = false;
                Weixin = true;
                Alipay = false;
                Yinlian = false;
                sendUpdateMessage(R.id.weixin);
                break;
            }
            case R.id.alipay:{
                Wotuan = false;
                Weixin = false;
                Alipay = true;
                Yinlian = false;
                sendUpdateMessage(R.id.alipay);
                break;
            }
            case R.id.yinlian:{
                Wotuan = false;
                Weixin = false;
                Alipay = false;
                Yinlian = true;
                sendUpdateMessage(R.id.yinlian);
                break;
            }case R.id.comfir_pay:{
                if(Wotuan) {
                    Toast.makeText(this, "我团支付 成功支付" + Money + "元", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(Weixin) {
                    Toast.makeText(this, "微信支付 成功支付" + Money + "元", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(Alipay) {
                    String ALIPAY_SHOP = "https://qr.alipay.com/stx05107r5oaa4fyofbkh24";//商户
                    String ALIPAY_PERSON = "https://qr.alipay.com/a6x072239lowi6sxnbcmx5c";//个人(支付宝里面我的二维码)
                    String ALIPAY_PERSON_2_PAY = "HTTPS://QR.ALIPAY.COM/FKX05679AILRTCV5XDKFAC";//个人(支付宝里面我的二维码,然后提示让用的收款码)
                    openAliPay2Pay(ALIPAY_PERSON_2_PAY);
                    break;
                }
                if(Yinlian) {
                    Toast.makeText(this, "银联 成功支付" + Money + "元", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            }

        }

    }

    private void openAliPay2Pay(String qrCode) {
        if (openAlipayPayPage(this, qrCode)) {
            Toast.makeText(this, "打开支付宝", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "您的手机尚未安装支付宝", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
        }
        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            openUri(context, alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送一个intent
     *
     * @param context
     * @param s
     */
    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }

    //发送更新信息,开启子线程更新UI
    private void sendUpdateMessage( final int i){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = i;
                mHandler.sendMessage(message); // 将Message对象发送出去
            }
        }).start();
    }
}
