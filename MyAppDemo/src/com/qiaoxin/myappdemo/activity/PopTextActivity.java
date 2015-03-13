package com.qiaoxin.myappdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PopTextActivity extends Activity {
    private ImageButton arrow;
    private TextView con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poptext);

        con = (TextView) findViewById(R.id.tv_main);

        arrow = (ImageButton) findViewById(R.id.ib_arrow);
        arrow.setOnClickListener(new OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View arg0) {
                if (flag) {

                    flag = false;
                    con.setEllipsize(null); // 展开
                    con.setSingleLine(flag);
                } else {
                    flag = true;
                    con.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    con.setLines(2);
                }
            }
        });

    }

}
