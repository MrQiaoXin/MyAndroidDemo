/*
 * PlateSelectActivity.java create on 2014-12-26
 * Copyright(c) 2014-12-26 by Duoda
 */
package com.qiaoxin.myappdemo.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qiaoxin.myappdemo.bean.PListEntity;
import com.qiaoxin.myappdemo.bean.PlateType;
import com.qiaoxin.myappdemo.common.plist.domain.PListObject;
import com.qiaoxin.myappdemo.utils.PListUtil;
import com.qiaoxin.myappdemo.widget.QuickSlideBar;
import com.qiaoxin.myappdemo.widget.QuickSlideBar.OnTouchLetterChangeListenner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author qiaoxin
 * 
 */
public class PListTestActivity extends Activity {

    private QuickSlideBar slideBar;
    private ExpandableListView mlistView;
    private ExpandableAdapter adapter;
    private List<PListEntity> plistEntity;
    private List<String> array;
    private PListUtil plistUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_select);
        setPlateType();
        initView();
    }

    private void initView() {
        adapter = new ExpandableAdapter(getApplicationContext(), plistEntity);

        mlistView = (ExpandableListView) findViewById(R.id.list);
        mlistView.setAdapter(adapter);
        // 遍历所有group,将所有项设置成默认展开
        int groupCount = mlistView.getCount();
        for (int i = 0; i < groupCount; i++) {
            mlistView.expandGroup(i);
        }

        slideBar = (QuickSlideBar) findViewById(R.id.slideBar);
        slideBar.setTextList(array);
        slideBar.setOnTouchLetterChangeListenner(new OnTouchLetterChangeListenner() {

            @Override
            public void onTouchLetterChange(MotionEvent event, String s) {
                int position = array.indexOf(s);// 这个array就是传给自定义Adapter的
                mlistView.setSelectedGroup(position);
            }
        });

    }

    private void setPlateType() {

        array = new ArrayList<String>();
        plistEntity = new ArrayList<PListEntity>();

        plistUtil = new PListUtil(PListTestActivity.this,
                "carPlatePrefix.plist");
        plistEntity = plistUtil.plistParest();

        for (int i = 0; i < plistEntity.size(); i++) {
            array.add(plistEntity.get(i).getKey());
        }

    }

    public class ExpandableAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater listContainer;
        private List<PListEntity> listItems;
        private ListViewAdapter prefixListAdapter;

        public ExpandableAdapter(Context context, List<PListEntity> listItems) {
            this.context = context;
            this.listItems = listItems;
            this.listContainer = LayoutInflater.from(context);
        }

        public final class GroupListItemView { // 自定义组控件集合
            public TextView province;
        }

        public final class ChildListItemView { // 自定义子控件集合
            public ListView prefixListView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            GroupListItemView itemView = null;
            if (convertView == null) {
                itemView = new GroupListItemView();
                convertView = listContainer.inflate(
                        R.layout.plate_select_group, null);
                itemView.province = (TextView) convertView
                        .findViewById(R.id.province);
                convertView.setClickable(true);
                convertView.setTag(itemView);
            } else {
                itemView = (GroupListItemView) convertView.getTag();
            }
            itemView.province.setText(listItems.get(groupPosition).getKey());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            ChildListItemView itemView = null;
            if (convertView == null) {
                itemView = new ChildListItemView();
                convertView = listContainer.inflate(
                        R.layout.plate_select_child, null);
                itemView.prefixListView = (ListView) convertView
                        .findViewById(R.id.prefix_list);
                convertView.setTag(itemView);
            } else {
                itemView = (ChildListItemView) convertView.getTag();
            }
            prefixListAdapter = new ListViewAdapter(this.context, listItems
                    .get(groupPosition).getValue());
            itemView.prefixListView.setAdapter(prefixListAdapter);
            setListViewHeightBasedOnChildren(itemView.prefixListView);
            return convertView;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listItems.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return listItems.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }

    public class ListViewAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater listContainer;
        private List<String> listItems;

        public ListViewAdapter(Context context, List<String> listItems) {
            this.context = context;
            this.listItems = listItems;
            this.listContainer = LayoutInflater.from(context);
        }

        public final class ListItemView { // 自定义控件集合
            public TextView platePrefix;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int arg0) {
            return listItems;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView listItemView = null;
            if (convertView == null) {
                listItemView = new ListItemView();
                convertView = listContainer.inflate(
                        R.layout.plate_select_child_item, null);
                listItemView.platePrefix = (TextView) convertView
                        .findViewById(R.id.plate_prefix);
                convertView.setTag(listItemView);
            } else {
                listItemView = (ListItemView) convertView.getTag();
            }
            listItemView.platePrefix.setText(listItems.get(position));
            return convertView;
        }
    }

    // 调整ListView的Item高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter list = listView.getAdapter();
        if (list == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0, len = list.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = list.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (list.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
