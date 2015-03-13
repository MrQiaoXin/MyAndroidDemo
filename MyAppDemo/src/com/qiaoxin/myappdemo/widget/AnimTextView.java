package com.qiaoxin.myappdemo.widget;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.qiaoxin.myappdemo.activity.R;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnimTextView extends RelativeLayout {
    private TextView firstText;
    private TextView secondText;
    private View visiObj;
    private View invisiObj;
    private boolean flag = true;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if(flag){
                visiObj=firstText;
                invisiObj=secondText;
                switchTextView();
                flag=false;
            }else{
                visiObj=secondText;
                invisiObj=firstText;
                switchTextView();
                flag=true;
            }
        };
    };

    public AnimTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.amin_textview, this);
        firstText = (TextView) findViewById(R.id.first);
        secondText = (TextView) findViewById(R.id.second);
    }

    public AnimTextView(Context context) {
        super(context);
    }

    private void switchTextView() {
        ObjectAnimator visToInVisX = ObjectAnimator.ofFloat(visiObj, "translationX", 0, -this.getWidth());
        visToInVisX.setDuration(200);
        ObjectAnimator visToInVisAlpha = ObjectAnimator.ofFloat(visiObj, "alpha", 1f, 0.1f);
        visToInVisAlpha.setDuration(2000);

        ObjectAnimator inVisToVisX = ObjectAnimator.ofFloat(invisiObj, "translationX", this.getWidth(), 0);
        inVisToVisX.setDuration(500);
        ObjectAnimator inVisToVisAlpha = ObjectAnimator.ofFloat(invisiObj, "alpha", 0.1f, 1f);
        inVisToVisAlpha.setDuration(2000);

        

        AnimatorSet inVisToVisSet = new AnimatorSet();
        inVisToVisSet.play(inVisToVisX).with(inVisToVisAlpha);
        
        AnimatorSet visToInVisSet = new AnimatorSet();
        visToInVisSet.play(visToInVisX).with(visToInVisAlpha).with(inVisToVisSet);

        visToInVisSet.addListener(new AnimatorListenerAdapter() {
          
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                invisiObj.setVisibility(View.VISIBLE);
            }
            
        });
        visToInVisSet.start();
    }

    public void start() {
        ScheduledExecutorService  scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1, 5,
                TimeUnit.SECONDS);
       
    }
    
    public void setVisiText(String text){
        firstText.setText(text);
    }

    public void setInvisiText(String text){
        secondText.setText(text);
    }


}
