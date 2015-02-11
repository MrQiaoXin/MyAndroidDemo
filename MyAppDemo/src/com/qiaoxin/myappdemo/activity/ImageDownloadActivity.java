package com.qiaoxin.myappdemo.activity;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImageDownloadActivity extends Activity {

    private ImageView image;
    private Button downBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);
        initImageLoader();
        initView();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.imageView1);

        downBtn = (Button) findViewById(R.id.button1);
        downBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // 图片Uri
                String uri = "http://oss.aliyuncs.com/rijiche/1422860201576/5.jpg";
                String uri2 = "http://d.hiphotos.baidu.com/image/w%3D310/sign=bfcdf60d2d2eb938ec6d7cf3e56385fe/6d81800a19d8bc3e5a1ff326808ba61ea9d345c3.jpg";
                // 设置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.ic_shophoto)
                        .showImageForEmptyUri(R.drawable.ic_shophoto)
                        .showImageOnLoading(R.drawable.ic_shophoto).cacheInMemory(true)
                        .cacheOnDisk(true).build();
                // 下载
                ImageLoader.getInstance().displayImage(uri2, image, options);

            }
        });
    }

    private void initImageLoader() {

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPoolSize(3)
                .diskCache(new UnlimitedDiscCache(cacheDir)).build();
        ImageLoader.getInstance().init(config);
    }
}
