package com.qiaoxin.myappdemo.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuickSlideBar extends View {

    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // 是否画出背景
    private boolean showBg = false;
    // 选中的项
    private int choose = -1;
    // 准备好的A~Z的字母数组
    // public static String[] letters = { "#", "川", "鄂", "豫", "苏", "皖", "琼",
    // "甘", "浙", "赣", "津", "辽", "青", "藏", "闽", "鲁", "渝", "冀", "京", "云", "泸",
    // "粤", "新", "蒙", "黑", "湘", "贵", "陕", "宁", "贵", "晋", "吉" };
    public static List<String> letters = new ArrayList<String>();

    public QuickSlideBar(Context context) {
        super(context);
    }

    public QuickSlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽和高
        int width = getWidth();
        int height = getHeight();
        // letters.add("a");
        // 每个字母的高度
        int singleHeight = height / letters.size();
        if (showBg) {
            // 画出背景
            canvas.drawColor(Color.parseColor("#55000000"));
        }
        // 画字母
        for (int i = 0; i < letters.size(); i++) {
            paint.setColor(Color.parseColor("#CA614B"));
            // 设置字体格式
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20f);
            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.setColor(Color.parseColor("#F88701"));
                paint.setFakeBoldText(true);
            }
            // 要画的字母的x,y坐标
            float posX = width / 2 - paint.measureText(letters.get(i)) / 2;
            float posY = i * singleHeight + singleHeight;
            // 画出字母
            canvas.drawText(letters.get(i), posX, posY, paint);
            // 重新设置画笔
            paint.reset();
        }
    }

    /**
     * 处理SlideBar的状态
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        final int index = (int) (y / getHeight() * letters.size());
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            showBg = true;
            if (oldChoose != index && listenner != null && index >= 0
                    && index < letters.size()) {
                choose = index;
                listenner.onTouchLetterChange(event, letters.get(index));
                invalidate();
            }
            break;

        case MotionEvent.ACTION_MOVE:
            if (oldChoose != index && listenner != null && index >= 0
                    && index < letters.size()) {
                choose = index;
                listenner.onTouchLetterChange(event, letters.get(index));
                invalidate();
            }
            break;
        case MotionEvent.ACTION_UP:
        default:
            showBg = false;
            choose = -1;
            if (listenner != null && index >= 0 && index < letters.size())
                listenner.onTouchLetterChange(event, letters.get(index));
            invalidate();
            break;
        }
        return true;
    }

    /**
     * 回调方法，注册监听器
     * 
     * @param listenner
     */
    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }

    /**
     * SlideBar 的监听器接口
     * 
     */
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(MotionEvent event, String s);
    }

    /**
     * 设置内容
     */
    public void setTextList(List<String> letters) {
        this.letters.clear();
        this.letters.addAll(letters);
    }
}
