package com.tianfu.cutton.fragment.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tianfu.cutton.R.id.tv_kun_contact;
import static com.tianfu.cutton.R.id.tv_kun_distance;
import static com.tianfu.cutton.R.id.tv_kun_length;
import static com.tianfu.cutton.R.id.tv_kun_mobile;
import static com.tianfu.cutton.R.id.tv_kun_releaseDate;
import static com.tianfu.cutton.R.id.tv_kun_stdweight;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreKunMessageFragment extends Fragment {
    @BindView(R.id.tv_kun_batch)
    TextView tvKunBatch;
    @BindView(R.id.bt_kun_key)
    Button btKunKey;
    @BindView(R.id.tv_kun_origin)
    TextView tvKunOrigin;
    @BindView(R.id.tv_kun_color)
    TextView tvKunColor;
    @BindView(tv_kun_stdweight)
    TextView tvKunStdweight;
    @BindView(R.id.tv_kun_horse)
    TextView tvKunHorse;
    @BindView(tv_kun_length)
    TextView tvKunLength;
    @BindView(R.id.tv_kun_uniformityIndexAverage)
    TextView tvKunUniformityIndexAverage;
    @BindView(R.id.tv_kun_moisture)
    TextView tvKunMoisture;
    @BindView(R.id.tv_kun_trash)
    TextView tvKunTrash;
    @BindView(R.id.tv_kun_breakLoadAverage)
    TextView tvKunBreakLoadAverage;
    @BindView(R.id.tv_kun_rdAverage)
    TextView tvKunRdAverage;
    @BindView(R.id.tv_kun_plusBAverage)
    TextView tvKunPlusBAverage;
    @BindView(R.id.tv_kun_createYear)
    TextView tvKunCreateYear;
    @BindView(R.id.tv_kun_checkStorage)
    TextView tvKunCheckStorage;
    @BindView(R.id.tv_kun_storage)
    TextView tvKunStorage;
    @BindView(tv_kun_distance)
    TextView tvKunDistance;
    @BindView(tv_kun_contact)
    TextView tvKunContact;
    @BindView(tv_kun_mobile)
    TextView tvKunMobile;
    @BindView(tv_kun_releaseDate)
    TextView tvKunReleaseDate;
    @BindView(R.id.tv_kun_commany)
    TextView tvKunCommany;
    @BindView(R.id.tv_gongjian)
    TextView tvGongjian;
    private View mRootView;
    private Map<String, String> map;
    private TextView tvStoreKunMoney1;
    private LinearLayout nodata;
    private String productId;
    private LinearLayout sl_haveData;
    private StoreKunMessage.ValueBean item;

    public StoreKunMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_store_detail_message, container, false);
            initView();
            ButterKnife.bind(this, mRootView);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            productId = getActivity().getIntent().getStringExtra("productId");
            initIsOn();
            map = new HashMap<>();
            map.put("productIds", productId);
            initData();
            return mRootView;
        }
    }

    private void initIsOn() {
        Map mapIsOn = new HashMap();
        mapIsOn.put("productIds", productId);
        HttpManager.getServerApi().getStoreKunMessage(mapIsOn).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    sl_haveData.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.GONE);
                } else {
                    sl_haveData.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initView() {
        tvStoreKunMoney1 = (TextView) mRootView.findViewById(R.id.tv_store_kun_money);
        nodata = (LinearLayout) mRootView.findViewById(R.id.nodata);
        sl_haveData = (LinearLayout) mRootView.findViewById(R.id.sl_haveData);
    }

    private void initData() {
        HttpManager.getServerApi().getStoreKunMessage(map).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
//                    item.code + "(" + item.bagCount + "/" + item.batchCount + ")"
                    item = data.value.get(0);
                    tvKunBatch.setText(item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
                        btKunKey.setVisibility(View.GONE);
                    if (item.checkDate != null&&!"".equals(item.checkDate)) {
                        tvGongjian.setText(item.checkDate);
                    }
                    if (!item.price.equals("0")&&!TextUtils.isEmpty(item.price)) {
                        tvStoreKunMoney1.setText(item.price + "");
                        tvKunCommany.setVisibility(View.VISIBLE);
                    } else {
                        tvStoreKunMoney1.setText("价格面议");
                        tvKunCommany.setVisibility(View.GONE);
                    }
                    if (item.origin != null&&!"".equals(item.origin)) {
                        tvKunOrigin.setText(item.origin);
                    } else {
                        tvKunOrigin.setText("--");
                    }
                    if (item.colorGrade != null&&!"".equals(item.colorGrade)) {
                        tvKunColor.setText(item.colorGrade);
                    } else {
                        tvKunColor.setText("--");
                    }
                    if (item.stdweight != null&&!"".equals(item.stdweight)) {
                        tvKunStdweight.setText(item.stdweight + "t");
                    } else {
                        tvKunStdweight.setText("--");
                    }
                    if (item.micronGrade != null&&!"".equals(item.micronGrade)) {
                        tvKunHorse.setText(item.micronGrade);
                    } else {
                        tvKunHorse.setText("--");
                    }
                    if (item.lengthAverage != null&&!"".equals(item.lengthAverage)) {
                        tvKunLength.setText(item.lengthAverage);
                        if (Double.parseDouble(item.lengthAverage)>=28){
                            tvKunLength.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.homeTextLongColor));
                        }else{
                            tvKunLength.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                    } else {
                        tvKunLength.setText("--");
                    }
                    if (item.uniformityIndexAverage != null&&!"".equals(item.uniformityIndexAverage)) {
                        tvKunUniformityIndexAverage.setText(item.uniformityIndexAverage);
                    } else {
                        tvKunUniformityIndexAverage.setText("--");
                    }
                    if (item.moisture != null&&!"".equals(item.moisture)) {
                        tvKunMoisture.setText(item.moisture + "%");
                    } else {
                        tvKunMoisture.setText("--");
                    }
                    if (item.trash != null&&!"".equals(item.trash)) {
                        tvKunTrash.setText(item.trash + "%");
                    } else {
                        tvKunTrash.setText("--");
                    }
                    if (item.breakLoadAverage != null&&!"".equals(item.breakLoadAverage)) {
                        tvKunBreakLoadAverage.setText(item.breakLoadAverage);
                    } else {
                        if (Double.parseDouble(item.lengthAverage)>=28){
                            tvKunBreakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.homeTextLongColor));
                        }else{
                            tvKunBreakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                        tvKunBreakLoadAverage.setText("--");
                    }
                    if (item.createYear != null&&!"".equals(item.createYear)) {
                        tvKunCreateYear.setText(item.createYear);
                    } else {
                        tvKunCreateYear.setText("--");
                    }
                    if (item.checkStorage != null&&!"".equals(item.checkStorage)) {
                        tvKunCheckStorage.setText(item.checkStorage);
                    } else {
                        tvKunCheckStorage.setText("--");
                    }
//                    tv_kun_storage
                    if (item.storage != null&&!"".equals(item.storage)) {
                        tvKunStorage.setText(item.storage);
                    } else {
                        tvKunStorage.setText("--");
                    }
//                    tv_kun_distance
                    if (item.distance != null&&!"".equals(item.distance)) {
                        tvKunDistance.setText(item.distance + "km");
                    } else {
                        tvKunDistance.setText("--");
                    }
//                    tv_kun_contact
                    if (item.contact != null&&!"".equals(item.contact)) {
                        tvKunContact.setText(item.contact);
                    } else {
                        tvKunContact.setText("--");
                    }
//                    tv_kun_mobile
                    if (item.mobile != null&&!"".equals(item.mobile)) {
                        tvKunMobile.setText(item.mobile);
                    } else {
                        tvKunMobile.setText("--");
                    }
//                    tv_kun_releaseDate
                    if (item.releaseDate != null&&!"".equals(item.releaseDate)) {
                        tvKunReleaseDate.setText(item.releaseDate);
                    } else {
                        tvKunReleaseDate.setText("--");
                    }
                } else {

                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public StoreKunMessage.ValueBean getData() {
        return item;
    }
}
