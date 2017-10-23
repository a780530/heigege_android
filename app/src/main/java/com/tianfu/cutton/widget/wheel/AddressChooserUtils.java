package com.tianfu.cutton.widget.wheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tianfu.cutton.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/2.
 */
public class AddressChooserUtils {


    private WheelView sProvince;
    private WheelView sCity;
    private WheelView sDistrict;
    private ArrayList<String> sProvinces;
    private Map<String, ArrayList<String>> sCitis;
    private Map<String, ArrayList<String>> sDistric;
    private TextView sChooseFinish;
    private Context mContext;
    private String mCurrentProviceName;
    private String mCurrentCityName;
    private String mCurrentDistrictName;
    private Dialog sDialog;
    private static AddressChooserUtils mAddressChooserUtils;

    public AddressChooserUtils(Context context) {
        this.mContext = context;
    }

    public static AddressChooserUtils getInstance(Context context) {
        if (mAddressChooserUtils == null) {
            mAddressChooserUtils = new AddressChooserUtils(context);
        }
        return mAddressChooserUtils;
    }

    public void setData(ArrayList<String> provinces, Map<String, ArrayList<String>> citis, Map<String, ArrayList<String>> distric) {
        this.sProvinces = provinces;
        this.sCitis = citis;
        this.sDistric = distric;
    }

    public void showAddressChooserDialog(Context context) {
        mContext = context;

        sDialog = new Dialog(context, R.style.load_dialog);
        View dialogView = View.inflate(context, R.layout.dialog_chose_address, null);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setContentView(dialogView);

        Window dialogWindow = sDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
        attributes.width = CommonUtils.getScreenWidth(context);
        dialogWindow.setAttributes(attributes);

        initWheelView(dialogView);
        sDialog.show();

        CommonUtils.setBackground((Activity) mContext, 0.7f);

        sDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                CommonUtils.setBackground((Activity) mContext, 1.0f);
            }
        });
    }

    private void initWheelView(View dialogView) {
        sProvince = (WheelView) dialogView.findViewById(R.id.wheelview_province);
        sCity = (WheelView) dialogView.findViewById(R.id.wheelview_city);
        sDistrict = (WheelView) dialogView.findViewById(R.id.wheelview_district);
        sChooseFinish = ((TextView) dialogView.findViewById(R.id.tv_choose_address_finish));

        setListener();

        sProvince.setViewAdapter(new ArrayWheelAdapterTest<>(mContext, sProvinces));
        sProvince.setCurrentItem(0);
        updateCities();

        sProvince.setVisibleItems(8);
        sCity.setVisibleItems(8);
        sDistrict.setVisibleItems(8);

    }


    //根据当前的省，更新市WheelView的信息

    private void updateCities() {

        int pCurrent = sProvince.getCurrentItem();
        mCurrentProviceName = sProvinces.get(pCurrent);
        Log.i("AddressUtils", mCurrentProviceName + "");

        ArrayList<String> cities = sCitis.get(mCurrentProviceName);
        if (cities == null) {
            cities = new ArrayList<>();
            return;
        }

        if (cities.size() > 0) {
            mCurrentCityName = cities.get(0);
        }
        sCity.setViewAdapter(new ArrayWheelAdapterTest<String>(mContext, cities));
        sCity.setCurrentItem(0);
        updateAreas();

    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = sCity.getCurrentItem();
        if (sCitis != null && sCitis.get(mCurrentProviceName) != null && sCitis.get(mCurrentProviceName).size() > 0) {
            mCurrentCityName = sCitis.get(mCurrentProviceName).get(pCurrent);
        }

        ArrayList<String> areas = sDistric.get(mCurrentCityName);


        if (areas == null) {
            areas = new ArrayList<>();
            return;
        }

        if (areas.size() > 0) {
            mCurrentDistrictName = areas.get(0);
        }
        Log.i("AddressUtils", mCurrentDistrictName + "");
        sDistrict.setViewAdapter(new ArrayWheelAdapterTest<String>(mContext, areas));
        sDistrict.setCurrentItem(0);
    }


    private OnWheelChangedListener onWheelChangedListener = new OnWheelChangedListener() {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            //sProvince.setCurrentItem(0);
            if (wheel == sProvince) {
                updateCities();

            } else if (wheel == sCity) {

                updateAreas();

            } else if (wheel == sDistrict) {
                mCurrentDistrictName = sDistric.get(mCurrentCityName).get(newValue);
            }
        }
    };
    private OnAddressDialogDismissListener sOnAddressDialogDismissListener;

    public void setOnAddressDialogDismissListener(OnAddressDialogDismissListener onAddressDialogDismissListener) {
        this.sOnAddressDialogDismissListener = onAddressDialogDismissListener;
    }

    public interface OnAddressDialogDismissListener {
        void onDissmiss(String province, String city, String district);
    }

    private void setListener() {
        sProvince.addChangingListener(onWheelChangedListener);
        sCity.addChangingListener(onWheelChangedListener);
        sDistrict.addChangingListener(onWheelChangedListener);
        sChooseFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOnAddressDialogDismissListener.onDissmiss(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
                sDialog.dismiss();
                Log.i("AddressChooseUtils", mCurrentProviceName + "=" + mCurrentCityName + "=" + mCurrentDistrictName);
            }
        });
    }
}
