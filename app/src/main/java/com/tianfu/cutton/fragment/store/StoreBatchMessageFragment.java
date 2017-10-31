package com.tianfu.cutton.fragment.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.quality.QualityDetailBagActivity;
import com.tianfu.cutton.activity.store.StsDetadilsActivity;
import com.tianfu.cutton.model.PremiumJsonBean;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.StringJudgeUtils;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.bt_count_bag;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreBatchMessageFragment extends Fragment {


    @BindView(R.id.tv_batch_batch)
    TextView tvBatchBatch;
    @BindView(R.id.bt_batch_key)
    Button btBatchKey;
    @BindView(R.id.tv_store_kun_money)
    TextView tvStoreKunMoney;
    @BindView(R.id.tv_kun_commany)
    TextView tvKunCommany;
    @BindView(R.id.tv_batch_origin)
    TextView tvBatchOrigin;
    @BindView(R.id.tv_batch_factoryCode)
    TextView tvBatchFactoryCode;
    @BindView(R.id.tv_batch_factory)
    TextView tvBatchFactory;
    @BindView(R.id.tv_batch_factoryAddress)
    TextView tvBatchFactoryAddress;
    @BindView(R.id.tv_batch_checkStorage)
    TextView tvBatchCheckStorage;
    @BindView(R.id.tv_batch_storage)
    TextView tvBatchStorage;
    @BindView(R.id.tv_batch_contact)
    TextView tvBatchContact;
    @BindView(R.id.tv_batch_mobile)
    TextView tvBatchMobile;
    @BindView(R.id.tv_batch_checkDate)
    TextView tvBatchCheckDate;
    @BindView(R.id.tv_batch_releaseDate)
    TextView tvBatchReleaseDate;
    @BindView(R.id.receviType)
    TextView receviType;
    @BindView(R.id.nodata)
    AutoLinearLayout nodata;
    @BindView(R.id.sl_haveData)
    ScrollView slHaveData;
    @BindView(R.id.tv_yxxy)
    TextView tvYxxy;
    @BindView(R.id.sts_Num)
    TextView stsNum;
    @BindView(R.id.go_toSts)
    AutoLinearLayout goToSts;
    @BindView(bt_count_bag)
    Button btCountBag;
    @BindView(R.id.ivIsCheap)
    ImageView ivIsCheap;
    private View mRootView;
    private String productId;
    private Map<String, String> map;
    private String batchTypeStore;
    private String mobileBaojia;
    private StoreKunMessage.ValueBean item;
    private String kunCodeStore;
    private PremiumJsonBean premiumJsonBean;
    private String beasePrice;
    private String premiumJsonString;
    private String code;

    public StoreBatchMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_store_batch_message, container, false);
        ButterKnife.bind(this, mRootView);
        productId = getActivity().getIntent().getStringExtra("productId");
        code = getActivity().getIntent().getStringExtra("code");
        mobileBaojia = getActivity().getIntent().getStringExtra("mobileBaojia");
        map = new HashMap<>();
        map.put("productIds", productId);//516
        initData();
        return mRootView;
    }

    private void initData() {
        HttpManager.getServerApi().getStoreKunMessage(map).enqueue(new CallBack<StoreKunMessage>() {

            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    item = data.value.get(0);
                    batchTypeStore = item.batchType;
                    kunCodeStore = item.kunCode;
                    if (TextUtils.isEmpty(item.yxxwCount) || "0".equals(item.yxxwCount)) {
                        tvYxxy.setText("0包");
                    } else {
                        tvYxxy.setText(item.yxxwCount + "包");
                    }

                    tvBatchBatch.setText(item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
                    if (item.property != null && !"".equals(item.property)) {
                        btBatchKey.setText(item.property);
                        btBatchKey.setVisibility(View.VISIBLE);
                    } else {
                        btBatchKey.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(item.price) && !item.price.equals("0")) {
                        tvStoreKunMoney.setText(item.price + "");
                        tvKunCommany.setVisibility(View.VISIBLE);
                    } else {
                        tvStoreKunMoney.setText("价格面议");
                        tvKunCommany.setVisibility(View.GONE);
                    }
//                    tv_batch_origin
                    if (item.type != null && !"".equals(item.type)) {
                        tvBatchOrigin.setText(item.type);
                    } else {
                        tvBatchOrigin.setText("--");
                    }

//                    tv_batch_factoryCode
                    if (item.factoryCode != null && !"".equals(item.factoryCode)) {
                        tvBatchFactoryCode.setText(item.factoryCode);
                    } else {
                        tvBatchFactoryCode.setText("--");
                    }
//                    tv_batch_factory
                    if (item.factory != null && !"".equals(item.factory)) {
                        tvBatchFactory.setText(item.factory);
                    } else {
                        tvBatchFactory.setText("--");
                    }
//                    tv_batch_factoryAddress
                    if (item.factoryAddress != null && !"".equals(item.factoryAddress)) {
                        tvBatchFactoryAddress.setText(item.factoryAddress);
                    } else {
                        tvBatchFactoryAddress.setText("--");
                    }
//                    tv_batch_checkStorage
                    if (item.checkStorage != null && !"".equals(item.checkStorage)) {
                        tvBatchCheckStorage.setText(item.checkStorage);
                    } else {
                        tvBatchCheckStorage.setText("--");
                    }
//                    tv_batch_storage
                    if (item.storage != null && !"".equals(item.storage)) {
                        tvBatchStorage.setText(item.storage);
                    } else {
                        tvBatchStorage.setText("--");
                    }
//                    tv_batch_distance

//                    tv_batch_contact
                    if (item.contact != null && !"".equals(item.contact)) {
                        tvBatchContact.setText(item.contact);
                    } else {
                        tvBatchContact.setText("--");
                    }
//                }tv_batch_mobile
                    if (item.mobile != null && !"".equals(item.mobile)) {
                        tvBatchMobile.setText(item.mobile);
                    } else {
                        tvBatchMobile.setText("--");
                    }
//                    tv_batch_checkDate
                    if (item.checkDate != null && !"".equals(item.checkDate)) {
                        tvBatchCheckDate.setText(item.checkDate);
                    } else {
                        tvBatchCheckDate.setText("--");
                    }
//                    tv_batch_releaseDate
                    if (item.releaseDate != null && !"".equals(item.releaseDate)) {
                        tvBatchReleaseDate.setText(item.releaseDate);
                    } else {
                        tvBatchReleaseDate.setText("--");
                    }
                    beasePrice = item.referencBeasePrice;
                    premiumJsonString = item.premiumJson;
                    Gson gson = new Gson();
                    premiumJsonBean = gson.fromJson(premiumJsonString, PremiumJsonBean.class);


                    StringJudgeUtils.judgeStringSts(item.sts, stsNum);
                    String takeType = item.takeType;
                    String settlementMethod = item.settlementMethod;
                    if (takeType != null && settlementMethod != null) {
                        if (takeType.equals("1") && settlementMethod.equals("1")) {
                            receviType.setText("自提/公重");
                        } else if (takeType.equals("1") && settlementMethod.equals("2")) {
                            receviType.setText("自提/毛重");
                        } else if (takeType.equals("2") && settlementMethod.equals("1")) {
                            receviType.setText("送到/公重");
                        } else if (takeType.equals("2") && settlementMethod.equals("2")) {
                            receviType.setText("送到/毛重");
                        }
                    }
                    if (TextUtils.isEmpty(item.isCheap)){
                        ivIsCheap.setVisibility(View.GONE);
                    }else{
                        if (item.isCheap.equals("0")){
                            ivIsCheap.setVisibility(View.GONE);
                        }else{
                            ivIsCheap.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    nodata.setVisibility(View.VISIBLE);
                    slHaveData.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                System.out.println("失败");
            }
        });
    }


    public StoreKunMessage.ValueBean getData() {
        return item;
    }


    @OnClick({R.id.go_toSts, R.id.bt_count_bag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_toSts:
                if (TextUtils.isEmpty(premiumJsonString)) {
                    ToastUtil.show(BaseApplication.getContextObject(), "暂无升贴水数据");
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("premiumJsonBean", premiumJsonBean);
                    intent.putExtra("beasePrice", beasePrice);
                    intent.setClass(getActivity(), StsDetadilsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bt_count_bag:
                Intent intent = new Intent(getActivity(), QualityDetailBagActivity.class);
                intent.putExtra("code", code);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
