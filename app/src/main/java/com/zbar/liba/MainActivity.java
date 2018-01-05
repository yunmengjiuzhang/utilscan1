package com.zbar.liba;

import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import wangfei.scan1.Scan1Activity;

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
