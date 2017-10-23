package com.tianfu.cutton.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianfu.cutton.R;

/**
 * Created by liwuya on 2017/7/22.
 */

public class LoadingDialog extends Dialog {


    private static LoadingDialog dialog;
    private Context mContent;

    public LoadingDialog(Context context) {
        super(context);
        mContent = context;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContent = context;
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContent = context;
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.img);
        // 获取ImageView上的动画背景
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                mContent, R.anim.loading_animation);
        // 开始动画
        imageView.startAnimation(hyperspaceJumpAnimation);
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @return
     */
    public Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static LoadingDialog show(Context context, boolean cancelable, OnCancelListener cancelListener) {
        dialog = new LoadingDialog(context, R.style.loading_dialog);
        dialog.setTitle("");
        dialog.setContentView(R.layout.loading_dialog);
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }

    public void dismisss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
