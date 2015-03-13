package com.qiaoxin.myappdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.qiaoxin.myappdemo.widget.AnimTextView;

public class AnimActivity extends Activity {

    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;
    private Button btn;
    private AnimTextView animTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        textview1 = (TextView) findViewById(R.id.text1);
        textview2 = (TextView) findViewById(R.id.text2);

        textview3 = (TextView) findViewById(R.id.text3);
        textview4 = (TextView) findViewById(R.id.text4);
        animTextView = (AnimTextView) findViewById(R.id.animtext);
        animTextView.setVisiText("今天天气晴");
        animTextView.setInvisiText("适宜洗车");
        btn = (Button) findViewById(R.id.start_anim);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                animTextView.start();
            }
        });

        // final Animation animation1 = new AlphaAnimation(0.0f, 1.0f);
        // animation1.setDuration(1000);
        // animation1.setStartOffset(5000);
        //
        // final Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        // animation2.setDuration(1000);
        // animation2.setStartOffset(5000);

        // animation1 AnimationListener
        // animation1.setAnimationListener(new AnimationListener() {
        //
        // @Override
        // public void onAnimationEnd(Animation arg0) {
        // // start animation2 when animation1 ends (continue)
        // textview3.startAnimation(animation2);
        // textview4.startAnimation(animation1);
        // }
        //
        // @Override
        // public void onAnimationRepeat(Animation arg0) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void onAnimationStart(Animation arg0) {
        // }
        //
        // });
        //
        // // animation2 AnimationListener
        // animation2.setAnimationListener(new AnimationListener() {
        //
        // @Override
        // public void onAnimationEnd(Animation arg0) {
        // // start animation1 when animation2 ends (repeat)
        // textview4.startAnimation(animation2);
        // textview3.startAnimation(animation1);
        // }
        //
        // @Override
        // public void onAnimationRepeat(Animation arg0) {
        //
        // }
        //
        // @Override
        // public void onAnimationStart(Animation arg0) {
        // }
        //
        // });
        // textview3.startAnimation(animation2);

        final Animation lOutAnim1 = AnimationUtils.loadAnimation(AnimActivity.this,
                R.anim.push_left_out); // 1左出渐变效果
        final Animation rInAnim1 = AnimationUtils.loadAnimation(AnimActivity.this,
                R.anim.push_right_in); // 1右进渐变效果
        final Animation lOutAnim2 = AnimationUtils.loadAnimation(AnimActivity.this,
                R.anim.push_left_out); // 2左出渐变效果
        final Animation rInAnim2 = AnimationUtils.loadAnimation(AnimActivity.this,
                R.anim.push_right_in); // 2右进渐变效果

        lOutAnim1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                textview2.startAnimation(rInAnim2);
                textview2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                textview1.setVisibility(View.INVISIBLE);
            }
        });
        rInAnim1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                textview1.startAnimation(lOutAnim1);
            }
        });

        lOutAnim2.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                textview1.startAnimation(rInAnim1);
                textview1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                textview2.setVisibility(View.INVISIBLE);
            }
        });
        rInAnim2.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                textview2.startAnimation(lOutAnim2);
            }
        });
        textview2.setVisibility(View.VISIBLE);
        textview2.startAnimation(lOutAnim2);

    }

}
