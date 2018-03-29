package com.example.lzd.cateringgroupbuy.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzd.cateringgroupbuy.Order.PayOrderActivity;
import com.example.lzd.cateringgroupbuy.R;
import com.example.lzd.cateringgroupbuy.Restaurant;

/**
 * Created by ldhns on 2018/3/10.
 */

public class PayActivity extends AppCompatActivity implements View.OnClickListener {

    Restaurant restaurant ;
    EditText paymoney;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant_data");

        ImageView back =(ImageView) findViewById(R.id.back_pay);
        back.setOnClickListener(this);

        Button pay = (Button) findViewById(R.id.btn_pay);
        pay.setOnClickListener(this);

        TextView name = (TextView) findViewById(R.id.pay_name);
        name.setText(restaurant.getName());

        paymoney =(EditText) findViewById(R.id.pay_count);

    }

    @Override
    public void onClick(View view){
        if(view.getId() == (R.id.btn_pay)){
            if(paymoney.getText().toString().isEmpty()){
                Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(PayActivity.this, PayOrderActivity.class);
                intent.putExtra("food_money","  "+paymoney.getText().toString());
                startActivity(intent);
            }
        }else if(view.getId() == (R.id.back_pay)){
            this.finish();
        }
    }

}
