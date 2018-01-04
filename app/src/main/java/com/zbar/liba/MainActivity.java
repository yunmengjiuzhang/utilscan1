package com.zbar.liba;

import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import wangfei.scan1.Scan1Activity;

public class MainActivity extends Scan1Activity {

    @Override
    protected ImageView getLinene() {
        return (ImageView) findViewById(R.id.capture_scan_line);
    }

    @Override
    protected RelativeLayout getLienContatiner() {
        return (RelativeLayout) findViewById(R.id.capture_crop_layout);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
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
}
