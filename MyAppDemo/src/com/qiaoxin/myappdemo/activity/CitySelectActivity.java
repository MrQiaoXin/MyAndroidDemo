package com.qiaoxin.myappdemo.activity;

import java.util.ArrayList;
import java.util.List;
import com.qiaoxin.myappdemo.bean.CityInfo;
import com.qiaoxin.myappdemo.bean.PListEntity;
import com.qiaoxin.myappdemo.utils.PListUtil;
import com.qiaoxin.myappdemo.widget.QuickSlideBar;
import com.qiaoxin.myappdemo.widget.QuickSlideBar.OnTouchLetterChangeListenner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CitySelectActivity extends Activity {
	
	private static final String LOCATING="正在定位...";

//	private TextView currentCity, locationCity;
//	private ImageView locationSelect;
//	private List<CityInfo> cityList;
//	private RelativeLayout locationCityLayout, currentCityLayout;
	private Intent intent;
	private String initCityName;

	private QuickSlideBar slideBar;
	private ExpandableListView mlistView;
	private ExpandableAdapter adapter;
	private List<PListEntity> plistEntity;
	private List<String> array;
	private PListUtil plistUtil;

	private static Handler handle = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_city);
		setPlistContent();
		initView();
	}

	private void initView() {
		
		// currentCity = (TextView) findViewById(R.id.tv_local_city_current);
		// currentCity.setText(initCityName);
		// currentCityLayout = (RelativeLayout)
		// findViewById(R.id.rl_local_city_current_text);
		// currentCityLayout.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (!initCityName.equals("")) {
		// returnToActivity(initCityName);
		// }
		// }
		// });
		// locationCity = (TextView) findViewById(R.id.tv_local_city_location);
		// locationCity.setText("正在定位...");
		// locationSelect = (ImageView) findViewById(R.id.in_location_select);
		// locationCityLayout = (RelativeLayout)
		// findViewById(R.id.rl_local_city_location_text);
		// locationCityLayout.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// String cityName = locationCity.getText().toString();
		// if (!cityName.equals("正在定位...")) {
		// initCityName = cityName;
		// locationSelect.setVisibility(View.VISIBLE);
		// // AppConfig.LOCAL_CITY = cityName;
		// // listAdapter.notifyDataSetChanged();
		// currentCity.setText(cityName);
		// returnToActivity(cityName);
		// }
		// }
		// });
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// CityInfo localCityInfo = (CityInfo) parent
		// .getItemAtPosition(position);
		// if (!initCityName.equals(localCityInfo.getCityName())) {
		// // AppConfig.LOCAL_CITY = localCityInfo.getCityName();
		// initCityName = localCityInfo.getCityName();
		// //listAdapter.notifyDataSetChanged();
		// currentCity.setText(localCityInfo.getCityName());
		// }
		// returnToActivity(localCityInfo.getCityName());
		// }
		// });
		plistEntity.get(2).setKey("热门城市");
		adapter = new ExpandableAdapter(getApplicationContext(), plistEntity);
		adapter.notifyDataSetChanged();
		
		Log.i("123", plistEntity.size() + "");
		slideBar = (QuickSlideBar) findViewById(R.id.quickSlideBar1);
		slideBar.setTextList(array);
		slideBar.setOnTouchLetterChangeListenner(new OnTouchLetterChangeListenner() {

			@Override
			public void onTouchLetterChange(MotionEvent event, String s) {
				int position = array.indexOf(s);// 这个array就是传给自定义Adapter的
				if (position != 0) {
					position += 2;
				}
				mlistView.setSelectedGroup(position);
			}
		});
		mlistView = (ExpandableListView) findViewById(R.id.myExpandableListView1);
		mlistView.setAdapter(adapter);
		// 遍历所有group,将所有项设置成默认展开
		int groupCount = mlistView.getCount();
		for (int i = 0; i < groupCount; i++) {
			mlistView.expandGroup(i);
		}
		// setExpandableListViewHeightBasedOnChildren(mlistView);
	}

	private void setPlistContent() {
		intent = this.getIntent();
		initCityName = intent.getStringExtra("initCityName");
		if (initCityName == null || initCityName.equals("")
				|| initCityName.equals("选择所在城市")) {
			initCityName = "";
		}
		
		PListEntity currentple = new PListEntity();
		currentple.setKey("当前选择城市");
		List<String> currentCity = new ArrayList<String>();
		currentCity.add(initCityName);
		currentple.setValue(currentCity);

		PListEntity localple = new PListEntity();
		localple.setKey("定位城市");
		List<String> localCity = new ArrayList<String>();
		localCity.add(LOCATING);
		localple.setValue(localCity);

		array = new ArrayList<String>();
		plistEntity = new ArrayList<PListEntity>();

		plistEntity.add(currentple);
		plistEntity.add(localple);

		plistUtil = new PListUtil(CitySelectActivity.this, "citydict.plist");
		plistEntity.addAll(plistUtil.plistParest());
		
		for (int i = 2; i < plistEntity.size(); i++) {
			array.add(plistEntity.get(i).getKey());
		}
		//
		// adapter = new ExpandableAdapter(getApplicationContext(),
		// plistEntity);
		// adapter.notifyDataSetChanged();
		// for (int i = 2; i < plistEntity.size(); i++) {
		// array.add(plistEntity.get(i).getKey());
		// }
		// Log.i("123", plistEntity.size() + "");
		// slideBar = (QuickSlideBar) findViewById(R.id.quickSlideBar1);
		// slideBar.setTextList(array);
		// slideBar.setOnTouchLetterChangeListenner(new
		// OnTouchLetterChangeListenner() {
		//
		// @Override
		// public void onTouchLetterChange(MotionEvent event, String s) {
		// int position = array.indexOf(s);// 这个array就是传给自定义Adapter的
		// if (position != 0) {
		// position += 2;
		// }
		// mlistView.setSelectedGroup(position);
		// }
		// });
		// mlistView = (ExpandableListView)
		// findViewById(R.id.myExpandableListView1);
		// mlistView.setAdapter(adapter);
		// // 遍历所有group,将所有项设置成默认展开
		// int groupCount = mlistView.getCount();
		// for (int i = 0; i < groupCount; i++) {
		// mlistView.expandGroup(i);
		// }
		// // setExpandableListViewHeightBasedOnChildren(mlistView);

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
			itemView.prefixListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int id, long arg3) {
							String result = parent.getAdapter().getItem(id)
									.toString();							
							if (result.equals("")||result.equals(LOCATING)) {
								
							} else {
							    returnToActivity(result);
								Intent it = new Intent();
								it.putExtra("result", result);
								CitySelectActivity.this.setResult(2, it);
								CitySelectActivity.this.finish();
							}
						}
					});
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
		public Object getItem(int id) {
			return listItems.get(id);
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

	// 调整ListView的Item高度
	public void setExpandableListViewHeightBasedOnChildren(
			ExpandableListView listView) {
		ExpandableListAdapter list = listView.getExpandableListAdapter();
		if (list == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < list.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
			// group高度
			View listItem = list.getGroupView(i, true, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			// child高度
			View ChildView = list.getChildView(i, 1, true, null, listView);
			ChildView.measure(0, 0);

			totalHeight += listItem.getMeasuredHeight()
					+ ChildView.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (list.getGroupCount() - 1));
		listView.setLayoutParams(params);
	}
	
	private void returnToActivity(String cityName) {
		Intent intent = new Intent();
		intent.putExtra("result", cityName);
		CitySelectActivity.this.setResult(0, intent);
		CitySelectActivity.this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			if (initCityName.equals("")) {
				Toast.makeText(CitySelectActivity.this, "请选择城市",
						Toast.LENGTH_SHORT).show();
			} else {
				returnToActivity(initCityName);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
