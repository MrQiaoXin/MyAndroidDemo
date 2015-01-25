package com.qiaoxin.myappdemo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.qiaoxin.myappdemo.bean.PListEntity;
import com.qiaoxin.myappdemo.common.plist.PListXMLHandler;
import com.qiaoxin.myappdemo.common.plist.PListXMLParser;
import com.qiaoxin.myappdemo.common.plist.domain.Array;
import com.qiaoxin.myappdemo.common.plist.domain.Dict;
import com.qiaoxin.myappdemo.common.plist.domain.PList;
import com.qiaoxin.myappdemo.common.plist.domain.PListObject;

/**
 * 
 * @author qiaoxin
 * 
 */
public class PListUtil {
    private static final String TAG = "PListUtil";

    private PListXMLParser parser = new PListXMLParser(); // 基于SAX的实现
    private PListXMLHandler handler = new PListXMLHandler();
    private Activity activity;
    private PList plist;
    private String fileName;

    public PListUtil(Activity activity, String fileName) {
        this.activity = activity;
        this.fileName = fileName;
    }

    public List<PListEntity> plistParest() {

        List<PListEntity> list = new ArrayList<PListEntity>();

        parser.setHandler(handler);
        try {
            parser.parse(activity.getAssets().open(fileName)); // area.plist是你要解析的文件，该文件需放在assets文件夹下
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PList actualPList = ((PListXMLHandler) parser.getHandler()).getPlist();
        Dict root = (Dict) actualPList.getRootElement();

        Map<String, PListObject> group = root.getConfigMap();

        // Log.i(TAG, "start parest");
        // Log.i(TAG, group.keySet().toString());
        // Log.i(TAG, group.keySet().size() + "");
        Iterator it = group.keySet().iterator();

        for (int i = 0; i < group.keySet().size(); i++) {

            PListEntity pe = new PListEntity();

            String keyName = (String) it.next();
            Array arrayList = root.getConfigurationArray(keyName);
            // Log.i(TAG, "**" + keyName);
            pe.setKey(keyName);
            pe.setValue(new ArrayList<String>());
            // Log.i(TAG, "**" + arrayList);
            // Log.i(TAG, "**start");

            for (int j = 0; j < arrayList.size(); j++) {
                com.qiaoxin.myappdemo.common.plist.domain.String valueName = (com.qiaoxin.myappdemo.common.plist.domain.String) arrayList
                        .get(j);
                // Log.i(TAG + "cities", valueName.getValue());
                pe.getValue().add(valueName.getValue());
            }
            list.add(pe);
        }

        return list;
    }
}
