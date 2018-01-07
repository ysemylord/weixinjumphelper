package com.plugin.xuyabo.weixingameplugin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by xuyabo on 2017/12/31.
 * 辅助记录前后两次点击的坐标点
 */

public class PlugView extends LinearLayout{
    private static final String TAG = "PlugView";
    private float mLastX;
    private float mLastY;
    private TouchInterface mInterface;

    public PlugView(Context context) {
        super(context);
    }

    public PlugView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        event.getAction();

        int action= MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float nowX=event.getX();
                float nowY=event.getY();
                float delatX=nowX-mLastX;//当前手势的触摸点与上次手势的触摸点的差值
                float delatY=nowY-mLastY;
                if(mInterface!=null){
                    mInterface.onClickPoint(mLastX,mLastY,nowX,nowY);
                }
                Log.i(TAG, "onTouchEvent: last"+mLastX+","+mLastY);
                Log.i(TAG, "onTouchEvent: now"+nowX+","+nowX);
                mLastX=nowX;
                mLastY=nowY;
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setInterface(TouchInterface anInterface) {
        mInterface = anInterface;
    }

    public  interface  TouchInterface{
        void onClickPoint(float x1,float y1,float x2,float y2);
        void onBack();
    }

    //// TODO: 2018/1/7 为甚使用这样方法才监听到back键
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mInterface != null) {
                    mInterface.onBack();
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                // 处理自己的逻辑break;
            default:
                break;
        }
        return super.dispatchKeyEvent(event);
    }
}
