package com.example.lzd.cateringgroupbuy.Homepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lzd.cateringgroupbuy.R;

import java.util.List;

/**
 * Created by ldhns on 2018/3/16.
 */

public class LocationActivity extends Activity {

    private ImageView back;
    private TextView myLocation;
    private MapView mapview;
    private BaiduMap baiduMap;
    private View cmf;
    private boolean isFirstLocate = true;//第一次定位时 地图中心移动到我的位置
    private String cityname;
    public LocationClient mLocationClient ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //city = (Button) findViewById(R.id.bdmap_cityname);
        mLocationClient = new LocationClient(this);

        mLocationClient.registerLocationListener(new MyLocationListener());

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        mapview = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapview.getMap();
        baiduMap.setMyLocationEnabled(true);

        initLocation();
        mLocationClient.start();

        myLocation = (TextView) findViewById(R.id.title_location);

        cmf = (View) findViewById(R.id.btn_cmfLocation);
        cmf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data_return", cityname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);//打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(17)
                    .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);

            /*
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            */
            isFirstLocate = false;
            cityname = location.getCity().toString();
            myLocation.setText(location.getCity().toString()+location.getDistrict().toString()+location.getStreet().toString());
        }
        //标记自身位置
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapview.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapview.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", cityname);
        setResult(RESULT_OK, intent);
        finish();
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
            //myLocation.setText();
            Log.d("cityname",bdLocation.getCity().toString());

        }
    }
}
