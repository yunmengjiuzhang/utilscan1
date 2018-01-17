package wangfei.scan1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.InactivityTimer;
import com.zbar.lib.decodef.CaptureActivityHandler;

import java.io.IOException;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class Scan1F extends SwipeBackFragment implements SurfaceHolder.Callback {
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;//剪切宽度
    private int cropHeight = 0;//剪切高度
    private View mCropLayout = null;
    private boolean isNeedCapture = false;
    private SurfaceView surfaceView;

    public boolean isNeedCapture() {
        return isNeedCapture;
    }

    public void setNeedCapture(boolean isNeedCapture) {
        this.isNeedCapture = isNeedCapture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return initView(inflater, container, savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();


        CameraManager.init(getContext());// 初始化CameraManager
        hasSurface = false;
        inactivityTimer = new InactivityTimer(getActivity());
        mCropLayout = getCaptureCropLayout();

        View mQrLineView = getline();
        TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mQrLineView.setAnimation(mAnimation);


        surfaceView = getSufaceView();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    public void handleDecode(String result) {
        inactivityTimer.onActivity();
        handleResult(result);
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);

            Point point = CameraManager.get().getCameraResolution();
            int width = point.y;
            int height = point.x;

            int x = mCropLayout.getLeft() * width / surfaceView.getWidth();
            int y = mCropLayout.getTop() * height / surfaceView.getHeight();

            int cropWidth = mCropLayout.getWidth() * width / surfaceView.getWidth();
            int cropHeight = mCropLayout.getHeight() * height / surfaceView.getHeight();

            setX(x);
            setY(y);
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
            setNeedCapture(true);


        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public Handler getHandler() {
        return handler;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract View getline();

    protected abstract View getCaptureCropLayout();

    protected abstract SurfaceView getSufaceView();

    public abstract void handleResult(String result);

    protected void openLight() {
        CameraManager.get().openLight(); //
    }

    protected void offLight() {
        CameraManager.get().offLight(); //
    }

    public static void openFileChooseProcess(Activity context) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        context.startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 0://选中
                        handleChooseFile(data.getData());
                        break;
                    default://选择其他
                        break;
                }
            } else if (resultCode == RESULT_CANCELED) {//取消

            }
        }
    }

    protected abstract void handleChooseFile(Uri data);


}
