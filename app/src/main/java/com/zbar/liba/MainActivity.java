package com.zbar.liba;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.zbar.lib.ZbarManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import wangfei.scan1.Scan1Activity;
import wangfei.scan1.UriUtils;

public class MainActivity extends Scan1Activity {

    @Override
    protected View getline() {
        return findViewById(R.id.capture_scan_line);
    }

    @Override
    protected View getCaptureCropLayout() {
        return findViewById(R.id.capture_crop_layout);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        CheckBox viewById = (CheckBox) findViewById(R.id.cb_light);
        viewById.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openLight();
                } else {
                    offLight();
                }
            }
        });

        findViewById(R.id.openFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooseProcess(MainActivity.this);
            }
        });
    }

    @Override
    protected SurfaceView getSufaceView() {
        return (SurfaceView) findViewById(R.id.capture_preview);
    }

    @Override
    public void handleResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void handleChooseFile(Uri data) {
        Bitmap bitmap = null;
        try {
            bitmap = UriUtils.getBitmapFormUri(this, data);
//            ImageView view = (ImageView) findViewById(R.id.iv_test);
//            view.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();
        String s = decodeRresult(datas, bitmap.getWidth(), bitmap.getHeight());

        Log.d("解码尝试", String.valueOf(s));
    }

    public String decodeRresult(byte[] data, int width, int height) {


//        byte[] rotatedData = new byte[data.length];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++)
//                rotatedData[x * height + height - y - 1] = data[x + y * width];
//        }
//        int tmp = width;// Here we are swapping, that's the difference to #11
//        width = height;
//        height = tmp;

        ZbarManager manager = new ZbarManager();//解码工具
        //解码结果
        String result = manager.decode(data, height, width, false, 0, 0, 1080, 1920);
        return result;
    }
}
