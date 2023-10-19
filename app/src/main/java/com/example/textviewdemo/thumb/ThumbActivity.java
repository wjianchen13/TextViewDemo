package com.example.textviewdemo.thumb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

public class ThumbActivity extends AppCompatActivity {

    private ImageView imgvThumb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb);
        imgvThumb = findViewById(R.id.imgv_thumb);
    }

    /**
     * 测试图片
     * @param v
     */
    public void onTest1(View v) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test,options);
        imgvThumb.setImageBitmap(mBitmap);
    }

    /**
     * 测试裁剪图片
     * @param v
     */
    public void onTest2(View v) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test,options);
        Bitmap bitmap = Bitmap.createBitmap(mBitmap, 100, 100, 120, 120);
        imgvThumb.setImageBitmap(bitmap);
    }

    /**
     * 创建缩略图并显示
     * @param v
     */
    public void onTest3(View v) {
        Bitmap textBitmap = BitmapUtils.createEntryFontBitmap(this, "hello", "is coming",
                "#8c2e01", 0, 24, Gravity.START | Gravity.CENTER, false, false);
        imgvThumb.setImageBitmap(textBitmap);
    }

}