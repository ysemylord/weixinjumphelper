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


/**
 * 弹窗辅助类
 *
 * @ClassName WindowUtils
 */
public class WindowUtils {
    private static final String LOG_TAG = "WindowUtils";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;

    /**
     * 显示弹出框
     *
     * @param context
     * @param
     */
    public static void showPopupWindow(final Context context,int width,int height) {
        if (mView != null&&mView.getParent()!=null) {
            mWindowManager.removeView(mView);
        }

        Log.i(LOG_TAG, "showPopupWindow");
        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型TYPE_APPLICATION_OVERLAY
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = //WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_TOUCH_MODAL
         WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = width;//WindowManager.LayoutParams.MATCH_PARENT;
        params.height =height;// WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.TOP;
        mWindowManager.addView(mView, params);
        Log.i(LOG_TAG, "add view");
    }


    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if ( null != mView) {
            Log.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
        }
    }

    /**
     * 隐藏弹出框
     */
    public static void minPopupWindow() {
        PlugView plugView = (PlugView) mView;
        hidePopupWindow();
        showPopupWindow(mContext,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);

    }

    private  static int mNeedTime=0;
    private static View setUpView(final Context context) {
        Log.i(LOG_TAG, "setUp view");
        PlugView view = (PlugView) LayoutInflater.from(context).inflate(R.layout.popup_window,
                null);
        final TextView lastPointTextView=view.findViewById(R.id.last_point_text);
        final TextView nowPointTextView=view.findViewById(R.id.now_point_text);
        final TextView delatPointTextView=view.findViewById(R.id.delate);
        view.setInterface(new PlugView.TouchInterface() {
            @Override
            public void onClickPoint(float x1, float y1, float x2, float y2) {
                float delatX=x2-x1;
                float delatY=y2-y1;
                double lineDistance=Math.sqrt(delatX*delatX+delatY*delatY);//两点的直线距离
                String delatXStr="delatX:"+delatX+"";
                String delatYStr="delatY:"+delatY;
                mNeedTime = DistanceToTime.calculateTime((int) lineDistance);
                String needTime="需要"+ mNeedTime +"ms";
                lastPointTextView.setText(x1+","+y1);
                nowPointTextView.setText(x2+","+y2);
                delatPointTextView.setText(delatXStr+"\n"+delatYStr+"\n"+"直线距离:"+lineDistance+"\n"
                +needTime);

            }
        });

        Button autoPressBtn=view.findViewById(R.id.auto_press_btn);
        autoPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimulateInput.doCmds("input swipe 900 900 900 900 "+mNeedTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "执行 adb shell 异常");
                }
            }
        });

        Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "ok on click");
                // 隐藏弹窗
                showPopupWindow(mContext, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);            }
        });
        Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "cancel on click");
                WindowUtils.hidePopupWindow();
            }
        });
        Button minBtn = (Button) view.findViewById(R.id.min_btn);
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "cancel on click");
                WindowUtils.minPopupWindow();
            }
        });

        // 点击back键可消除
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtils.hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }
}
