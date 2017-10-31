package com.tianfu.cutton.activity.mine;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.adapter.PurchaseOrderdetailsRecylerAdapter;
import com.tianfu.cutton.adapter.SupplierRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.ListSupplierByPurchaseIdBean;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.model.PurchaseorderbySelfDetailsBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.ll_timeTitle;


public class PurchaseOrderDetailsActivity extends BaseActivity {

    /*
    * 采购单详情
    * */
    @BindView(R.id.supplierRecycler)
    RecyclerView supplierRecycler;
    @BindView(R.id.tv_staus)
    TextView tvStaus;
    @BindView(R.id.stausImage)
    ImageView stausImage;
    @BindView(R.id.moneyDtails)
    TextView moneyDtails;
    @BindView(R.id.companyDetails)
    TextView companyDetails;
    @BindView(R.id.originDetails)
    TextView originDetails;
    @BindView(R.id.typeDetails)
    TextView typeDetails;
    @BindView(R.id.colorDetails)
    TextView colorDetails;
    @BindView(R.id.micronAverageDetails)
    TextView micronAverageDetails;
    @BindView(R.id.breakLoadAverageDetails)
    TextView breakLoadAverageDetails;
    @BindView(R.id.lengthDetails)
    TextView lengthDetails;
    @BindView(R.id.personDetails)
    TextView personDetails;
    @BindView(R.id.phoneDetails)
    TextView phoneDetails;
    @BindView(R.id.addressDetails)
    TextView addressDetails;
    @BindView(R.id.lastDate)
    TextView lastDate;
    @BindView(R.id.toDate)
    TextView toDate;
    @BindView(R.id.numberDetails)
    TextView numberDetails;
    @BindView(R.id.moreDetails)
    TextView moreDetails;
    @BindView(R.id.crateDate)
    TextView crateDate;
    @BindView(R.id.closeDetails)
    Button closeDetails;
    @BindView(R.id.finishDetails)
    Button finishDetails;
    @BindView(R.id.ll_ButtonDetails)
    AutoLinearLayout llButtonDetails;
    @BindView(R.id.timeTitle)
    TextView timeTitle;
    @BindView(R.id.shixiaoDate)
    TextView shixiaoDate;
    @BindView(ll_timeTitle)
    AutoLinearLayout llTimeTitle;
    @BindView(R.id.purchaseOrder_recycler)
    RecyclerView purchaseOrder_recycler;
    @BindView(R.id.rl_money)
    AutoRelativeLayout rlMoney;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    AutoRelativeLayout llTitle;
    @BindView(R.id.trash)
    TextView trash;
    @BindView(R.id.mMoisture)
    TextView mMoisture;
    @BindView(R.id.mSettlementMethod)
    TextView mSettlementMethod;
    @BindView(R.id.tvColor)
    TextView tvColor;
    private Intent intent;
    private String purchaseSn;
    private SupplierRecylerAdapter adapterSupplier;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_details);
        ButterKnife.bind(this);
        black();//状态栏字体
        llTitle.setBackgroundResource(R.color.white);
        iv_back.setImageResource(R.mipmap.ic_mine_backgreen);
        tvTitle.setText("采购单详情");
        tvTitle.setTextColor(getResources().getColor(R.color.tab_tv_selected));
        intent = getIntent();
        purchaseSn = intent.getStringExtra("purchaseSn");
        System.out.println("purchaseSn:" + purchaseSn);
        initData();
    }

    private void initData() {
        showProgressBar("", true);
        Map<String, String> stringMap = new HashMap();
        stringMap.put("purchaseSn", purchaseSn);
        stringMap.put("deviceNo", Common.deviceNo);
        HttpManager.getServerApi().getPurchaseOrderDetails(stringMap).enqueue(new CallBack<PurchaseorderbySelfDetailsBean>() {
            @Override
            public void success(PurchaseorderbySelfDetailsBean data) {
                if (data.success) {
                    id = data.value.id;
                    List<String> keyWord = data.value.keyword;
                    if (keyWord != null) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(PurchaseOrderDetailsActivity.this, 4);
                        purchaseOrder_recycler.setLayoutManager(gridLayoutManager);
                        PurchaseOrderdetailsRecylerAdapter adapter = new PurchaseOrderdetailsRecylerAdapter(BaseApplication.getContextObject(), keyWord);
                        purchaseOrder_recycler.setAdapter(adapter);
                    }
                    String purchaseId = data.value.id;
                    getSupplire(purchaseId);//获取供货人列表
                    tvStaus.setText(data.value.purchaseStatusName);
                    String purchaseStatus = data.value.purchaseStatus;
                    if (purchaseStatus.equals("Purchasing")) {
                        llTimeTitle.setVisibility(View.GONE);
                        stausImage.setImageResource(R.mipmap.ic_common_ing);
                        llButtonDetails.setVisibility(View.VISIBLE);
                    } else if (purchaseStatus.equals("Close")) {
                        timeTitle.setText("关闭时间");
                        llTimeTitle.setVisibility(View.VISIBLE);
                        llButtonDetails.setVisibility(View.GONE);
                        stausImage.setImageResource(R.mipmap.ic_common_closed);
                    } else if (purchaseStatus.equals("Invalid")) {
                        timeTitle.setText("失效时间");
                        llTimeTitle.setVisibility(View.VISIBLE);
                        llButtonDetails.setVisibility(View.GONE);
                        stausImage.setImageResource(R.mipmap.ic_common_shixiao);
                    } else if (purchaseStatus.equals("Complete")) {
                        timeTitle.setText("完成时间");
                        llTimeTitle.setVisibility(View.VISIBLE);
                        llButtonDetails.setVisibility(View.GONE);
                        stausImage.setImageResource(R.mipmap.ic_mine_over);
                    }
                    //产地
                    if (data.value.origin2 == null) {
                        originDetails.setText("--");
                    } else {
                        List<String> list = data.value.origin2;
                        String str = "";
                        for (int i = 0; i < list.size(); i++) {
                            str += list.get(i) + "   ";
                        }
                        originDetails.setText(str);
                    }
                    //棉花年度
                    if (data.value.createYear != null && data.value.createYear.size() != 0) {
                        String s = "";
                        for (int i = 0; i < data.value.createYear.size(); i++) {
                            if (i != data.value.createYear.size() - 1) {
                                s = s + data.value.createYear.get(i) + "/";
                            } else {
                                s = s + data.value.createYear.get(i);
                            }
                        }
                        typeDetails.setText(s);
                    } else {
                        typeDetails.setText("--");
                    }
                /*    if (data.value.createYear == null) {
                        typeDetails.setText("--");
                    } else {
                        List<String> list = data.value.createYear;
                        String str = "";
                        int count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (count % 3 == 0 && count != 0) {
                                str += "\n" + list.get(i) + "   ";
                            } else {
                                str += list.get(i) + "   ";
                            }
                            count++;
                        }
                        typeDetails.setText(str);
                    }*/
                    //颜色及
                    if (data.value.colorGrade2 == null) {
                        colorDetails.setText("--");
                    } else {
                        List<String> list = data.value.colorGrade2;
                        String str = "";
                        int count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (count % 3 == 0 && count != 0) {
                                str += "\n" + list.get(i) + "   ";
                            } else {
                                str += list.get(i) + "   ";
                            }
                            count++;
                        }
                        colorDetails.setText(str);
                    }
                    if (data.value.micronAverage == null) {
                        micronAverageDetails.setText("3.4-5.0");
                    } else {
                        micronAverageDetails.setText(data.value.micronAverage.min + "-" + data.value.micronAverage.max);
                        if ((data.value.micronAverage.min + "").equals("" + data.value.micronAverage.max)) {
                            micronAverageDetails.setText(data.value.micronAverage.min + "");
                            if ((data.value.micronAverage.min + "").equals("3.4")) {
                                micronAverageDetails.setText("3.4及以下");
                            } else if ((data.value.micronAverage.min + "").equals("5.0")) {
                                micronAverageDetails.setText("5.0及以上");
                            }
                        }
                    }
                    //强力
                    if (data.value.breakLoadAverage == null) {
                        breakLoadAverageDetails.setText("25.6-27.8");
                    } else {
                        breakLoadAverageDetails.setText(data.value.breakLoadAverage.min + "-" + data.value.breakLoadAverage.max);
                        if ((data.value.breakLoadAverage.min + "").equals(data.value.breakLoadAverage.max + "")) {
                            breakLoadAverageDetails.setText(data.value.breakLoadAverage.min + "");
                            if ((data.value.breakLoadAverage.min + "").equals("24")) {
                                breakLoadAverageDetails.setText("24及以下");
                            } else if ((data.value.breakLoadAverage.min + "").equals("31")) {
                                breakLoadAverageDetails.setText("31及以上");
                            }
                        }
                    }
                    if (data.value.lengthAverage == null) {
                        lengthDetails.setText("27-29");
                    } else {
                        lengthDetails.setText(data.value.lengthAverage.min + "-" + data.value.lengthAverage.max);
                        if ((data.value.lengthAverage.min + "").equals(data.value.lengthAverage.max + "")) {
                            lengthDetails.setText(data.value.lengthAverage.min + "");
                            if ((data.value.lengthAverage.min + "").equals("25")) {
                                lengthDetails.setText("25及以下");
                            } else if ((data.value.lengthAverage.min + "").equals("32")) {
                                lengthDetails.setText("32及以上");
                            }
                        }
                    }
                    //联系人
                    personDetails.setText(data.value.contacts);
                    //电话
                    phoneDetails.setText(data.value.telephone);
                    //address
                    if (data.value.address.equals("") || data.value.province == null) {
                        addressDetails.setText("--");
                    } else if (data.value.province == null) {
                        addressDetails.setText(data.value.address);
                    } else {
                        addressDetails.setText(data.value.address2);
                    }
                    //截止日期
                    if (data.value.deadline == null) {
                        lastDate.setText("--");
                    } else {
                        lastDate.setText(data.value.deadline);
                    }
                    //送达日期
                    if (data.value.receiveDate == null) {
                        toDate.setText("--");
                    } else {
                        toDate.setText(data.value.receiveDate);
                    }
//                    批次数量
                    if (data.value.batchCount == null) {
                        numberDetails.setText("--");
                    } else {
                        numberDetails.setText(data.value.batchCount);
                    }
                    //备注
                    if (data.value.remark == null) {
                        moreDetails.setText("--------------");
                    } else {
                        moreDetails.setText(data.value.remark);
                    }
                    crateDate.setText(data.value.createTime);
                    shixiaoDate.setText(data.value.stateUpdateTime);
                    //价格角
                    if (data.value.minPrice == 0 && data.value.maxPrice == 0) {
                        companyDetails.setVisibility(View.GONE);
                        moneyDtails.setText("价格面议");
                    } else {
                        companyDetails.setVisibility(View.VISIBLE);
                        if (data.value.minPrice == data.value.maxPrice) {
                            moneyDtails.setText(data.value.minPrice + "");
                        } else {
                            moneyDtails.setText(data.value.minPrice + "-" + data.value.maxPrice);
                        }

                    }
                    PurchaseorderbySelfDetailsBean.ValueBean.Trash trashBean = data.value.trash;
                    PurchaseorderbySelfDetailsBean.ValueBean.Moisture moistureBean = data.value.moisture;
                    if (trashBean != null) {
                        trash.setText(trashBean.min + "-" + trashBean.max);
                        if (trashBean.min.equals(trashBean.max)) {
                            trash.setText(trashBean.min);
                            if (trashBean.min.equals("0")) {
                                trash.setText("0及以下");
                            } else if (trashBean.min.equals("5")) {
                                trash.setText("5及以上");
                            }
                        }
                    }
                    if (moistureBean != null) {
                        mMoisture.setText(moistureBean.min + "-" + moistureBean.max);
                        if (moistureBean.min.equals(moistureBean.max)) {
                            mMoisture.setText(moistureBean.min);
                            if (moistureBean.min.equals("0")) {
                                mMoisture.setText("0及以下");
                            } else if (moistureBean.min.equals("10")) {
                                mMoisture.setText("10及以上");
                            }
                        }
                    }
                    String settlementMethod = data.value.settlementMethod;
                    if (TextUtils.isEmpty(settlementMethod)) {
                        mSettlementMethod.setText("--");
                    } else {
                        mSettlementMethod.setText(settlementMethod);
                    }
                    boolean baleCotton = data.value.baleCotton;
                    if (baleCotton) {
                        tvColor.setText("品级");
                    }
                    dismissProgressBar();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                dismissProgressBar();
            }
        });


    }

    private static final int TOTAL_COUNTER = 8;
    private int delayMillis = 1000;
    private static final int PAGE_SIZE = 6;

    private int mCurrentCounter = 0;

    private void getSupplire(String purchaseId) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //自动测量
        linearLayoutManager.setAutoMeasureEnabled(true);
        //禁用滑动
        supplierRecycler.setNestedScrollingEnabled(false);
        supplierRecycler.setLayoutManager(linearLayoutManager);
        Map<String, String> map = new HashMap<>();
        map.put("purchaseId", purchaseId);
        map.put("pageNum", "1");
        HttpManager.getServerApi().getListSupplierByPurchaseId(map).enqueue(new CallBack<ListSupplierByPurchaseIdBean>() {
            @Override
            public void success(final ListSupplierByPurchaseIdBean data) {
                if (data.success) {
                    adapterSupplier = new SupplierRecylerAdapter(R.layout.activity_purchase_order_item, data.value.rows);
                    mCurrentCounter = adapterSupplier.getItemCount();
                    adapterSupplier.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                                    + data.value.rows.get(position).mobile));//电话号码
                            if (ActivityCompat.checkSelfPermission(PurchaseOrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);
                        }
                    });
                    adapterSupplier.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            //加载更多供货人
                            if (mCurrentCounter >= TOTAL_COUNTER) {
                                supplierRecycler.post(new Runnable() {
                                    @Override
                                    public void run() {
//                                        adapterSupplier.isNextLoad(false);
                                        adapterSupplier.loadMoreEnd();
                                    }
                                });

                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCurrentCounter = adapterSupplier.getItemCount();
                                        adapterSupplier.loadMoreEnd();

                                    }
                                }, delayMillis);
                            }
                        }
                    });
                    supplierRecycler.setAdapter(adapterSupplier);

                } else {
                    ToastUtil.show(PurchaseOrderDetailsActivity.this, data.msg.toString());
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void black() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    @OnClick({R.id.closeDetails, R.id.finishDetails, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closeDetails:
                AlertDialog dialog = new AlertDialog.Builder(PurchaseOrderDetailsActivity.this)
                        .setMessage("是否关闭采购单？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                close();
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.finishDetails:
                AlertDialog dialog1 = new AlertDialog.Builder(PurchaseOrderDetailsActivity.this)
                        .setMessage("是否完成采购单？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                finishDetails();
                            }
                        })
                        .create();
                dialog1.show();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void finishDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpManager.getServerApi().completePurchaseOrder(map).enqueue(new CallBack<PurchaseOrder>() {
            @Override
            public void success(PurchaseOrder data) {
                if (data.success) {
                    initData();
                    Intent intent = new Intent();
                    intent.putExtra("1", "finish");
                    PurchaseOrderDetailsActivity.this.setResult(0, intent);
                    onBackPressed();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void close() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpManager.getServerApi().closePurchaseOrder(map).enqueue(new CallBack<PurchaseOrder>() {
            @Override
            public void success(PurchaseOrder data) {
                if (data.success) {
                    initData();
                    Intent intent = new Intent();
                    intent.putExtra("1", "close");
                    PurchaseOrderDetailsActivity.this.setResult(0, intent);
                    onBackPressed();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

}
