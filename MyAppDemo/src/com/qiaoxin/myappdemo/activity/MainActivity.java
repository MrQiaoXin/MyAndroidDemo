package com.qiaoxin.myappdemo.activity;

import com.qiaoxin.myappdemo.utils.DateTimePickDialogUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button button,toCarmerSecond;
    private TextView btn_select_plate, btn_select_city;
    private TextView dataSelected;
    private Button goToLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_select_plate = (TextView) findViewById(R.id.go_to_plate);
        btn_select_plate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), PlateSelectActivity.class);
                startActivityForResult(it, 1);
            }
        });
        btn_select_city = (TextView) findViewById(R.id.go_to_city);
        btn_select_city.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), CitySelectActivity.class);
                startActivityForResult(it, 2);
            }
        });
        button = (Button) findViewById(R.id.go_to_camera);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), MyCameraActivity.class);
                startActivity(it);
                finish();
            }
        });

        dataSelected = (TextView) findViewById(R.id.editText1);
        dataSelected.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        MainActivity.this);
                dateTimePicKDialog.dateTimePicKDialog(dataSelected);
            }
        });
        goToLocationManager = (Button) findViewById(R.id.go_to_location_manager);
        goToLocationManager.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), LocationManagerActivity.class);
                startActivity(it);
                finish();
            }
        });
        toCarmerSecond=(Button)findViewById(R.id.go_to_camera_se);
        toCarmerSecond.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), MyCameraSecondActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case 1:
            if (resultCode == 2) {
                btn_select_plate.setText(data.getStringExtra("result"));
            }
            break;
        case 2:
            btn_select_city.setText(data.getStringExtra("result"));
            break;
        }
    }
}
