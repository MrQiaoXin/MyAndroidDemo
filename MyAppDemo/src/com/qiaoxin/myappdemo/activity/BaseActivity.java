package com.qiaoxin.myappdemo.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class BaseActivity extends Activity {

    private GestureDetector gestureDetector = null;

    public void initGesture(int layoutId) {
        ViewGroup view = (ViewGroup) findViewById(layoutId);
        view.setClickable(true);
        gestureDetector = new GestureDetector(this, new OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();

                if (e1 != null && e2 != null && distanceX > 150 && Math.abs(velocityX) > 10) {
                    finishActivity();
                    Log.e("", "滑动" + (e2.getX() - e1.getX()));
                    Runnable ru = new Runnable() {

                        @Override
                        public void run() {
                            // Toast.makeText(this, e2.getX() - e1.getX(),
                            // Toast.LENGTH_SHORT).show();
                        }
                    };

                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

        });

        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("", "setOnTouchListener");
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public void initDoubletClick(int layoutId) {
        ViewGroup view = (ViewGroup) findViewById(layoutId);
        view.setClickable(true);
        new GestureDetector.OnDoubleTapListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

        };
    }

    public void switchActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void switchActivity(Class cls, Map<String, String> m) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        for (String k : m.keySet()) {
            intent.putExtra(k, m.get(k));
        }
        startActivity(intent);
    }

    public void finishActivity() {
        finish();
        // overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
