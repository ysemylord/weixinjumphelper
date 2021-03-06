package com.plugin.xuyabo.weixingameplugin;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;


/**
 * Created by xuyabo on 2018/1/5.
 */

public class HelperWindow {
    private static final String LOG_TAG = "HelperWindow";
    private View mHelperView;
    private Context mContext;
    private WindowManager mWindowManager = null;
    private int mNeedTime;

    public HelperWindow(Context context) {
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mHelperView = setUpView(mContext);
    }

    public void showPopupWindow(int width, int height) {
        if (mHelperView != null && mHelperView.getParent() != null) {
            mWindowManager.removeView(mHelperView);
        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = width;
        params.height = height;
        params.gravity = Gravity.TOP;
        mWindowManager.addView(mHelperView, params);
        Log.i(LOG_TAG, "add view");
    }

    private View setUpView(final Context context) {
        Log.i(LOG_TAG, "setUp helperView");
        PlugView helperView = (PlugView) LayoutInflater.from(context).inflate(R.layout.popup_window, null);
        final TextView lastPointTextView = helperView.findViewById(R.id.last_point_text);
        final TextView nowPointTextView = helperView.findViewById(R.id.now_point_text);
        final TextView delatPointTextView = helperView.findViewById(R.id.delate);
        Button autoPressBtn = helperView.findViewById(R.id.auto_press_btn);

        helperView.setInterface(new PlugView.TouchInterface() {
            @Override
            public void onClickPoint(float x1, float y1, float x2, float y2) {
                float delatX = x2 - x1;
                float delatY = y2 - y1;
                double lineDistance = Math.sqrt(delatX * delatX + delatY * delatY);//两点的直线距离
                String delatXStr = "delatX:" + delatX + "";
                String delatYStr = "delatY:" + delatY;
                mNeedTime = DistanceToTime.calculateTime((int) lineDistance);
                String needTime = "需要" + mNeedTime + "ms";
                lastPointTextView.setText(x1 + "," + y1);
                nowPointTextView.setText(x2 + "," + y2);
                delatPointTextView.setText(delatXStr + "\n" + delatYStr + "\n" + "直线距离:" + lineDistance + "\n"
                        + needTime);

            }

            @Override
            public void onBack() {
                hidePopupWindow();
            }
        });

        autoPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimulateInput.doCmds("input swipe 900 900 900 900 " + mNeedTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "执行 adb shell 异常");
                }
            }
        });

        Button positiveBtn =  helperView.findViewById(R.id.positiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxPopupWindow();
            }
        });
        Button negativeBtn =  helperView.findViewById(R.id.negativeBtn);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopupWindow();
            }
        });
        Button minBtn = helperView.findViewById(R.id.min_btn);
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minPopupWindow();
            }
        });

        return helperView;
    }

    private void hidePopupWindow() {
        if (null != mHelperView) {
            Log.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mHelperView);
        }
    }

    private void minPopupWindow() {
         //使用updateViewLayout改变视图
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) mHelperView.getLayoutParams();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mHelperView.setLayoutParams(params);
        mWindowManager.updateViewLayout(mHelperView, params);

    }

    private void maxPopupWindow(){
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) mHelperView.getLayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        mHelperView.setLayoutParams(params);
        mWindowManager.updateViewLayout(mHelperView, params);
    }


}
