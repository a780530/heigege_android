package com.tianfu.cutton.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianfu.cutton.R;

/**
 * Created by xiaohei on 2016/10/24.
 */

public class ToastUtil {
    private static Toast toast;

    public static void show(Context context, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showImageNew(Context context, String message) {
        toast = Toast.makeText(context,
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(R.mipmap.ic_commo_success);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void dissMiss() {
        if (toast != null) {
            toast.cancel();
        }
    }
    public static void showImage(Context context,String message){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_view, null);
        TextView t = (TextView) view.findViewById(R.id.toast_text);
        t.setText(message);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
