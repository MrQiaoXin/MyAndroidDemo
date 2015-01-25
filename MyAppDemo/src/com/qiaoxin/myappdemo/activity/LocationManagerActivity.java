package com.qiaoxin.myappdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.*;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.LatLng;

public class LocationManagerActivity extends Activity implements AMapLocationListener {

    private LocationManager locationManager;
    private LocationManagerProxy mLocationManagerProxy;
    private OnLocationChangedListener mListener;
    private TextView lat, lng;
    private TextView lat2, lng2;
    private TextView distance;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LatLng latlng1 = null, latlng2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        lat = (TextView) findViewById(R.id.lat);
        lng = (TextView) findViewById(R.id.lng);
        lat2 = (TextView) findViewById(R.id.lat2);
        lng2 = (TextView) findViewById(R.id.lng2);
        distance = (TextView) findViewById(R.id.distance);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        init();
        initTwo();
    }

    private void initTwo() {
        LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: "
                            + location.getLongitude());
                }
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0,
                locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude(); // 经度
            longitude = location.getLongitude(); // 纬度
            lat2.setText(latitude + "");
            lng2.setText(longitude + "");
            latlng2 = new LatLng(latitude, longitude);
            if (latlng1 != null) {
                float dis = AMapUtils.calculateLineDistance(latlng1, latlng2);
                distance.setText(dis + "米");
            }
        }
    }

    /**
     * 初始化定位
     */
    private void init() {

        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 15,
                this);

        mLocationManagerProxy.setGpsEnable(false);

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO this function is disable
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO this function is disable
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO this function is disable
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        // TODO this function is get the location lat and lng
        if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
            // 获取位置信息
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();
            lat.setText(geoLat + "");
            lng.setText(geoLng + "");
            latlng1 = new LatLng(geoLat, geoLng);
            if (latlng2 != null) {
                float dis = AMapUtils.calculateLineDistance(latlng1, latlng2);
                distance.setText(dis + "米");
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "获取失败" + amapLocation.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void deactivate() {
        // TODO:stop the location
        mListener = null;
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    protected void onPause() {
        deactivate();
        super.onPause();
    }

}
