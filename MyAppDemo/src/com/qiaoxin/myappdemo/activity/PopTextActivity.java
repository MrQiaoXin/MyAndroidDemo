package com.qiaoxin.myappdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PopTextActivity extends Activity {
    private ListView listview;
    private MyAdapter myAdapter;

    // private ImageButton arrow;
    // private TextView con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poptext);

        listview = (ListView) findViewById(R.id.lv_listview);
        myAdapter = new MyAdapter(PopTextActivity.this);
        listview.setAdapter(myAdapter);

        // con = (TextView) findViewById(R.id.tv_main);

        // ViewTreeObserver observer = con.getViewTreeObserver();
        // observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
        //
        // @Override
        // public void onGlobalLayout() {
        // Toast.makeText(PopTextActivity.this, "TextView渲染后的高度" +
        // con.getHeight(),
        // Toast.LENGTH_LONG).show();
        // }
        // });

        // arrow = (ImageButton) findViewById(R.id.ib_arrow);
        // arrow.setOnClickListener(new OnClickListener() {
        // Boolean flag = true;
        //
        // @Override
        // public void onClick(View arg0) {
        // if (flag) {
        // flag = false;
        // con.setEllipsize(null); // 展开
        // con.setSingleLine(flag);
        // } else {
        // flag = true;
        // con.setEllipsize(TextUtils.TruncateAt.END); // 收缩
        // con.setLines(2);
        // }
        // }
        // });

    }

    private class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private ViewItem viewItem;
        private Boolean flag = true;
        private List<String> list;

        public MyAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            list = new ArrayList<String>();
            list.add("1");
            list.add("2");
            list.add("3");
        }

        public final class ViewItem {
            private TextView con;
            private ImageButton btn;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                viewItem = new ViewItem();
                view = layoutInflater.inflate(R.layout.poptext_item, null);
                viewItem.btn = (ImageButton) view.findViewById(R.id.ib_arrow);
                viewItem.con = (TextView) view.findViewById(R.id.tv_main);


                this.viewItem.btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag) {
                            flag = false;
                            viewItem.con.setEllipsize(null); // 展开
                            viewItem.con.setSingleLine(flag);
                        } else {
                            flag = true;
                            viewItem.con.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                            viewItem.con.setLines(2);
                        }
                    }
                });
                
                view.setTag(viewItem);
            } else {
                viewItem = (ViewItem) view.getTag();
            }


            // ViewTreeObserver observer = viewItem.con.getViewTreeObserver();
            // observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            //
            // @Override
            // public void onGlobalLayout() {
            // // Toast.makeText(PopTextActivity.this,
            // // "TextView渲染后的高度" + viewItem.con.getHeight(),
            // // Toast.LENGTH_LONG).show();
            // LayoutParams params=(LayoutParams) view.getLayoutParams();
            // params.height= view.getHeight()+viewItem.con.getHeight();
            // view.setLayoutParams(params) ;
            // }
            // });
            
            return view;
        }

    }

}
