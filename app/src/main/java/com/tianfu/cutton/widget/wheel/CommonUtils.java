package com.tianfu.cutton.widget.wheel;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaohei on 2016-12-3.
 */
public class CommonUtils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    //获取主线程中的Handler
    public static Handler getHandler() {
        return mHandler;
    }

    //在主线程中执行任务
    public static void runOnUiThread(Runnable task) {
        mHandler.post(task);
    }

    //优化的Toast，可以在子线程中显示
    private static Toast mToast = null;

    public static void showToast(final Context context, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
                mToast.setText(text);
                mToast.show();
            }
        });
    }


    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //dp转px
    public static float getPxForDp(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    //获取当前时间
    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static void setBackground(Activity activity, float alpha) {
        Window window = activity.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


}

