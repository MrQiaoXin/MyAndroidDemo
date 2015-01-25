package com.qiaoxin.myappdemo.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiaoxin.myappdemo.utils.DisplayUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MyCameraActivity extends Activity {
    private static final int REQUESTCODE_CAMERA = 4;
    private static final int REQUESTCODE_PIC = 5;
    private static final int REQUESTCODE_HAND = 6;

    private ImageView imageView;
    private Button button;
    private Button button_openImages;
    private final String TAG = "MyCameraActivity";

    private Uri imageFilePath;
    private static final Uri STORAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = (ImageView) findViewById(R.id.imageview);
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                imageFilePath = getOutput();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFilePath);
                startActivityForResult(intent, REQUESTCODE_CAMERA);

            }
        });
        button_openImages = (Button) findViewById(R.id.button2);
        button_openImages.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent picture = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUESTCODE_PIC);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_CAMERA && resultCode == Activity.RESULT_OK) {// 相机

            imageView.setImageBitmap(getBitmap(imageFilePath.getPath()));// 将图片显示在ImageView里

            galleryAddPic();

            return;
        }
        if (requestCode == REQUESTCODE_PIC && resultCode == Activity.RESULT_OK) {// 相册
            //获取到相片的uir，从数据库中找出图片路径
            Uri selectedImage = data.getData();
            String[] filePathColumns = { MediaStore.Images.Media.DATA };
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null,
                    null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            
            ((ImageView) findViewById(R.id.imageview)).setImageBitmap(getBitmap(picturePath));// 将图片显示在ImageView里

            Log.e(TAG, "selectedImage:-------" + selectedImage.toString());
            Log.e(TAG, "picturePath:-------" + picturePath);

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getBitmap(String pathName) {
        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        // BitmapFactory.Options factoryOptions = new
        // BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;

        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

        // View
        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;
        factoryOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathName, factoryOptions);
        return bitmap;

    }

    @SuppressWarnings("static-access")
    private Uri getOutput() {
        File picDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "duoda");// 图片存放的标准目录。
        if (!picDir.exists()) {
            picDir.mkdirs();
        }
        imageName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        File picFile = new File(picDir.getPath() + File.separator + imageName);
        Uri picUri = Uri.fromFile(picFile);
        return picUri;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // File f = new File(imageFilePath.getPath());
        // Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(imageFilePath);
        this.sendBroadcast(mediaScanIntent);
    }

}
