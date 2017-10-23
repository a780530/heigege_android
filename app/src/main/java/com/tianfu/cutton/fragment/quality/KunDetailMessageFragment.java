package com.tianfu.cutton.fragment.quality;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.model.QualityKunMessageBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
//捆信息的信息界面

/**
 * A simple {@link Fragment} subclass.
 */
public class KunDetailMessageFragment extends BaseFragment {
    @BindView(R.id.tv_kun_code)
    TextView tvKunCode;
    @BindView(R.id.bt_kun_key)
    Button btKunKey;
    @BindView(R.id.tv_kun_type)
    TextView tvKunType;
    @BindView(R.id.tv_kun_origin)
    TextView tvKunOrigin;
    @BindView(R.id.tv_kun_color)
    TextView tvKunColor;
    @BindView(R.id.tv_kun_KG)
    TextView tvKunKG;
    @BindView(R.id.tv_kun_horse)
    TextView tvKunHorse;
    @BindView(R.id.tv_kun_length)
    TextView tvKunLength;
    @BindView(R.id.tv_kun_lengthQ)
    TextView tvKunLengthQ;
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
    @BindView(R.id.ll_call)
    AutoLinearLayout llCall;
    private Map<String, String> map;
    private View mRootView;
    private TextView tv_kun_code;
    private Button bt_kun_key;
    private TextView tv_kun_type;
    private TextView tv_kun_origin;
    private TextView tv_kun_color;
    private TextView tv_kun_kg;
    private TextView tv_kun_horse;
    private TextView tv_kun_length;
    private TextView tv_kun_lengthQ;
    private TextView tv_kun_moisture;
    private TextView tv_kun_trash;
    private TextView tv_kun_breakLoadAverage;
    private TextView tv_kun_rdAverage;
    private TextView tv_kun_plusBAverage;
    private TextView tv_kun_createYear;
    private TextView tv_kun_checkStorage;
    private String kunCode;
    private QualityKunMessageBean.ValueBean value;

    public KunDetailMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_inquiry_detail_message, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            String code = getActivity().getIntent().getStringExtra("code");
            String batchType = getActivity().getIntent().getStringExtra("batchType");
            map = new HashMap<>();
            map.put("code", code);
            System.out.println("code:-----" + code);
            map.put("batchType", batchType);
            System.out.println("batchType--------:" + batchType);
            map.put("isProduct", "0");
            initView();
            return mRootView;
        }
    }

    private void initView() {
        tv_kun_code = (TextView) mRootView.findViewById(R.id.tv_kun_code);
        bt_kun_key = (Button) mRootView.findViewById(R.id.bt_kun_key);
        tv_kun_type = (TextView) mRootView.findViewById(R.id.tv_kun_type);
        tv_kun_origin = (TextView) mRootView.findViewById(R.id.tv_kun_origin);
        tv_kun_color = (TextView) mRootView.findViewById(R.id.tv_kun_color);
        tv_kun_kg = (TextView) mRootView.findViewById(R.id.tv_kun_KG);
        tv_kun_horse = (TextView) mRootView.findViewById(R.id.tv_kun_horse);
        tv_kun_length = (TextView) mRootView.findViewById(R.id.tv_kun_length);
        tv_kun_lengthQ = (TextView) mRootView.findViewById(R.id.tv_kun_lengthQ);
        tv_kun_moisture = (TextView) mRootView.findViewById(R.id.tv_kun_moisture);
        tv_kun_trash = (TextView) mRootView.findViewById(R.id.tv_kun_trash);
        tv_kun_breakLoadAverage = (TextView) mRootView.findViewById(R.id.tv_kun_breakLoadAverage);
        tv_kun_rdAverage = (TextView) mRootView.findViewById(R.id.tv_kun_rdAverage);
        tv_kun_plusBAverage = (TextView) mRootView.findViewById(R.id.tv_kun_plusBAverage);
        tv_kun_createYear = (TextView) mRootView.findViewById(R.id.tv_kun_createYear);
        tv_kun_checkStorage = (TextView) mRootView.findViewById(R.id.tv_kun_checkStorage);
    }

    protected boolean isVisible = false;//页面是否可见
    protected boolean isPrepared = false;//是否布局已创建

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        onVisible();
    }

    protected void onVisible() {
        if (!isVisible && !isPrepared) {
            return;
        }
        loadLazyData();
//        showData();
    }

    private void showData() {
        HttpManager.getServerApi().getQualityKunMessage(map).enqueue(new CallBack<QualityKunMessageBean>() {
            @Override
            public void success(QualityKunMessageBean data) {
                value = data.value;
                if (data.success) {
                    tv_kun_code.setText(value.code + "(" + value.bagCount + "/" + value.batchCount + ")");
                    bt_kun_key.setVisibility(View.GONE);
                    if (value.type != null && !"".equals(value.type)) {
                        tv_kun_type.setVisibility(View.VISIBLE);
                        tv_kun_type.setText(value.type);
                    } else {
                        tv_kun_type.setVisibility(View.GONE);
                    }
                    if (value.origin != null && !"".equals(value.origin)) {
                        tv_kun_origin.setText(value.origin);
                    }
                    if (value.colorGrade != null && !"".equals(value.colorGrade)) {
                        tv_kun_color.setText(value.colorGrade);
                    } else {
                        tv_kun_color.setText("--");
                    }
                    if (value.stdweight != null && !"".equals(value.stdweight)) {
                        tv_kun_kg.setText(value.stdweight + "t");
                    } else {
                        tv_kun_kg.setText("--");
                    }
                    if (value.micronGrade != null && !"".equals(value.micronGrade)) {
                        tv_kun_horse.setText(value.micronGrade);
                    } else {
                        tv_kun_horse.setText("--");
                    }
                    if (value.lengthAverage != null && !"".equals(value.lengthAverage)) {
                        tv_kun_length.setText(value.lengthAverage);
                        if (Double.parseDouble(value.lengthAverage) >= 28) {
                            tv_kun_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
                        } else {
                            tv_kun_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                    } else {
                        tv_kun_length.setText("--");
                    }
//                    tv_kun_lengthQ
                    if (value.uniformityIndexAverage != null && !"".equals(value.uniformityIndexAverage)) {
                        tv_kun_lengthQ.setText(value.uniformityIndexAverage);
                    } else {
                        tv_kun_lengthQ.setText("--");
                    }
//                    tv_kun_moisture
                    if (value.moisture != null && !"".equals(value.moisture)) {
                        tv_kun_moisture.setText(value.moisture + "%");
                    } else {
                        tv_kun_moisture.setText("--");
                    }
//                    tv_kun_trash
                    if (value.trash != null && !"".equals(value.trash)) {
                        tv_kun_trash.setText(value.trash + "%");
                    } else {
                        tv_kun_trash.setText("--");
                    }
                    if (value.breakLoadAverage != null && !"".equals(value.breakLoadAverage)) {
                        tv_kun_breakLoadAverage.setText(value.breakLoadAverage);
                        if (Double.parseDouble(value.breakLoadAverage) >= 28) {
                            tv_kun_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
                        } else {
                            tv_kun_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                    } else {
                        tv_kun_breakLoadAverage.setText("--");
                    }
                    if (value.rdAverage != null && !"".equals(value.rdAverage)) {
                        tv_kun_rdAverage.setText(value.rdAverage);
                    } else {
                        tv_kun_rdAverage.setText("--");
                    }
                    if (value.plusBAverage != null && !"".equals(value.plusBAverage)) {
                        tv_kun_plusBAverage.setText(value.plusBAverage);
                    } else {
                        tv_kun_plusBAverage.setText("--");
                    }
                    if (value.createYear != null && !"".equals(value.createYear)) {
                        tv_kun_createYear.setText(value.createYear);
                    } else {
                        tv_kun_createYear.setText("");
                    }
                    if (value.checkStorage != null && !"".equals(value.checkStorage)) {
                        tv_kun_checkStorage.setText(value.checkStorage);
                    } else {
                        tv_kun_checkStorage.setText("--");
                    }
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void loadLazyData() {
        showProgressBar("请求数据中.......", true);
        HttpManager.getServerApi().getQualityKunMessage(map).enqueue(new CallBack<QualityKunMessageBean>() {
            @Override
            public void success(QualityKunMessageBean data) {
                value = data.value;
                if (data.success) {
                    tv_kun_code.setText(value.code + "(" + value.bagCount + "/" + value.batchCount + ")");
                    bt_kun_key.setVisibility(View.GONE);
                    if (value.type != null && !"".equals(value.type)) {
                        tv_kun_type.setVisibility(View.VISIBLE);
                        tv_kun_type.setText(value.type);
                    } else {
                        tv_kun_type.setVisibility(View.GONE);
                    }
                    if (value.origin != null && !"".equals(value.origin)) {
                        tv_kun_origin.setText(value.origin);
                    }
                    if (value.colorGrade != null && !"".equals(value.colorGrade)) {
                        tv_kun_color.setText(value.colorGrade);
                    } else {
                        tv_kun_color.setText("--");
                    }
                    if (value.stdweight != null && !"".equals(value.stdweight)) {
                        tv_kun_kg.setText(value.stdweight + "t");
                    } else {
                        tv_kun_kg.setText("--");
                    }
                    if (value.micronGrade != null && !"".equals(value.micronGrade)) {
                        tv_kun_horse.setText(value.micronGrade);
                    } else {
                        tv_kun_horse.setText("--");
                    }
                    if (value.lengthAverage != null && !"".equals(value.lengthAverage)) {
                        tv_kun_length.setText(value.lengthAverage);
                        if (Double.parseDouble(value.lengthAverage) >= 28) {
                            tv_kun_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
                        } else {
                            tv_kun_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                    } else {
                        tv_kun_length.setText("--");
                    }
//                    tv_kun_lengthQ
                    if (value.uniformityIndexAverage != null && !"".equals(value.uniformityIndexAverage)) {
                        tv_kun_lengthQ.setText(value.uniformityIndexAverage);
                    } else {
                        tv_kun_lengthQ.setText("--");
                    }
//                    tv_kun_moisture
                    if (value.moisture != null && !"".equals(value.moisture)) {
                        tv_kun_moisture.setText(value.moisture + "%");
                    } else {
                        tv_kun_moisture.setText("--");
                    }
//                    tv_kun_trash
                    if (value.trash != null && !"".equals(value.trash)) {
                        tv_kun_trash.setText(value.trash + "%");
                    } else {
                        tv_kun_trash.setText("--");
                    }
                    if (value.breakLoadAverage != null && !"".equals(value.breakLoadAverage)) {
                        tv_kun_breakLoadAverage.setText(value.breakLoadAverage);
                        if (Double.parseDouble(value.breakLoadAverage) >= 28) {
                            tv_kun_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
                        } else {
                            tv_kun_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
                        }
                    } else {
                        tv_kun_breakLoadAverage.setText("--");
                    }
                    if (value.rdAverage != null && !"".equals(value.rdAverage)) {
                        tv_kun_rdAverage.setText(value.rdAverage);
                    } else {
                        tv_kun_rdAverage.setText("--");
                    }
                    if (value.plusBAverage != null && !"".equals(value.plusBAverage)) {
                        tv_kun_plusBAverage.setText(value.plusBAverage);
                    } else {
                        tv_kun_plusBAverage.setText("--");
                    }
                    if (value.createYear != null && !"".equals(value.createYear)) {
                        tv_kun_createYear.setText(value.createYear);
                    } else {
                        tv_kun_createYear.setText("");
                    }
                    if (value.checkStorage != null && !"".equals(value.checkStorage)) {
                        tv_kun_checkStorage.setText(value.checkStorage);
                    } else {
                        tv_kun_checkStorage.setText("--");
                    }
                    dismissProgressBar();
                } else {
                    getActivity().finish();
                    ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
            }
        });
    }

    public QualityKunMessageBean.ValueBean getData() {
        return value;
    }
}
