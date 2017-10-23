package com.tianfu.cutton.fragment.quality;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.QualityKunMessageBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 质量批次详情
 * A simple {@link Fragment} subclass.
 */
public class QualityBatchDetalisMessageFragment extends Fragment {

    @BindView(R.id.tv_batch_code)
    TextView tvBatchCode;
    @BindView(R.id.bt_batch_key)
    Button btBatchKey;
    @BindView(R.id.tv_batch_type)
    TextView tvBatchType;
    @BindView(R.id.tv_batch_factoryCode)
    TextView tvBatchFactoryCode;
    @BindView(R.id.tv_batch_factory)
    TextView tvBatchFactory;
    @BindView(R.id.tv_batch_factoryAddress)
    TextView tvBatchFactoryAddress;
    @BindView(R.id.tv_batch_checkStorage)
    TextView tvBatchCheckStorage;
    @BindView(R.id.tv_batch_checkDate)
    TextView tvBatchCheckDate;
    @BindView(R.id.tv_yxxw)
    TextView tvYxxw;
    @BindView(R.id.cottonYear)
    TextView cottonYear;
    private View mRootview;
    private Map<String, String> hashMap;
    private String id;
    private String batchType;
    private String kunCodeIntent;
    private String code;
    private QualityKunMessageBean.ValueBean value;
    private String productId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootview != null) {
            return mRootview;
        } else {
            mRootview = inflater.inflate(R.layout.fragment_quality_batch_detalis, container, false);
            ViewGroup parent = (ViewGroup) mRootview.getParent();
            if (parent != null) {
                parent.removeView(mRootview);
            }
            ButterKnife.bind(this, mRootview);
            code = getActivity().getIntent().getStringExtra("code");
            String batchType = getActivity().getIntent().getStringExtra("batchType");
            productId = getActivity().getIntent().getStringExtra("productId");
            hashMap = new HashMap<>();
            hashMap.put("code", code);
            hashMap.put("batchType", batchType);
            hashMap.put("isProduct", "0");
            return mRootview;
        }
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
        if (!isVisible || !isPrepared) {
            return;
        }
        loadLazyData();
    }

    private void loadLazyData() {
        HttpManager.getServerApi().getQualityKunMessage(hashMap).enqueue(new CallBack<QualityKunMessageBean>() {

            @Override
            public void success(QualityKunMessageBean data) {
                if (data.success) {
                    value = data.value;
                    if (value == null) {
                        return;
                    }
                    if (data.success) {
                        kunCodeIntent = value.kunCode;
                        batchType = value.batchType;
                        id = value.id;

                        if (value.code != null) {
                            tvBatchCode.setText(value.code + "(" + value.bagCount + "/" + value.batchCount + ")");
                        }
                        String property = value.property;
                        if (property != null && !"".equals(property)) {
                            btBatchKey.setVisibility(View.VISIBLE);
                            btBatchKey.setText(property);
                        } else {
                            btBatchKey.setVisibility(View.GONE);
                        }
                        if (value.type != null && !"".equals(value.type)) {
                            tvBatchType.setText(value.type);
                            tvBatchType.setVisibility(View.VISIBLE);
                        } else {
                            tvBatchType.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(value.createYear)) {
                            cottonYear.setText("-");
                        }else{
                            cottonYear.setText(value.createYear);
                        }

                        if (value.factoryCode != null && !"".equals(value.factoryCode)) {
                            tvBatchFactoryCode.setText(value.factoryCode);
                        } else {
                            tvBatchFactoryCode.setText("--");
                        }
//                    tv_batch_factory
                        if (value.factory != null && !"".equals(value.factory)) {
                            tvBatchFactory.setText(value.factory);
                        } else {
                            tvBatchFactory.setText("--");
                        }
                        if (value.factoryAddress != null && !"".equals(value.factoryAddress)) {
                            tvBatchFactoryAddress.setText(value.factoryAddress);
                        } else {
                            tvBatchFactoryAddress.setText("--");
                        }
                        if (value.checkStorage != null && !"".equals(value.checkStorage)) {
                            tvBatchCheckStorage.setText(value.checkStorage);
                        } else {
                            tvBatchCheckStorage.setText("--");
                        }
                        if (value.checkDate != null && !"".equals(value.checkDate)) {
                            tvBatchCheckDate.setText(value.checkDate);
                        } else {
                            tvBatchCheckDate.setText("--");
                        }
                        if (TextUtils.isEmpty(value.yxxwCount) || "0".equals(value.yxxwCount)) {
                            tvYxxw.setText("0包");
                        } else {
                            tvYxxw.setText(value.yxxwCount + "包");
                        }
                    } else {
                        ToastUtil.show(BaseApplication.getContextObject(), "获取失败");
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
        isPrepared = false;
        isVisible = false;
    }


    public QualityKunMessageBean.ValueBean getData() {
        return value;
    }
}
