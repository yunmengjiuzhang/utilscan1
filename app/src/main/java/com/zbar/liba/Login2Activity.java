package com.zbar.liba;

import android.widget.Toast;

import wangfei.scan1.Scan1Activity;

public class Login2Activity extends Scan1Activity {

    @Override
    public void handleResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }
}

