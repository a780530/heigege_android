package com.tianfu.cutton.activity.mine;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.adapter.PurchaseOrderdetailsRecylerAdapter;
import com.tianfu.cutton.model.ListSupplyOrderBySelfBean;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.iv_back;

public class SupplyOrderDetailsActivity extends BaseActivity {

    @BindView(R.id.supplyOrder_recycler)
    RecyclerView supplyOrderRecycler;
    @BindView(R.id.supplyDetails_staus)
    TextView supplyDetailsStaus;
    @BindView(R.id.iv_supplyDetails_staus)
    ImageView ivSupplyDetailsStaus;
    @BindView(R.id.supply_Details_origin)
    TextView supplyDetailsOrigin;
    @BindView(R.id.supply_Details_type)
    TextView supplyDetailsType;
    @BindView(R.id.supply_details_colorGrade)
    TextView supplyDetailsColorGrade;
    @BindView(R.id.supply_Details_micron)
    TextView supplyDetailsMicron;
    @BindView(R.id.supply_details_breakLoad)
    TextView supplyDetailsBreakLoad;
    @BindView(R.id.supply_Details_length)
    TextView supplyDetailsLength;
    @BindView(R.id.supply_Details_contacts)
    TextView supplyDetailsContacts;
    @BindView(R.id.supply_Details_mobile)
    TextView supplyDetailsMobile;
    @BindView(R.id.supply_Details_address)
    TextView supplyDetailsAddress;
    @BindView(R.id.supply_Details_deadtime)
    TextView supplyDetailsDeadtime;
    @BindView(R.id.supply_Details_recevitime)
    TextView supplyDetailsRecevitime;
    @BindView(R.id.supply_Details_remark)
    TextView supplyDetailsRemark;
    @BindView(R.id.supply_Details_createTime)
    TextView supplyDetailsCreateTime;
    @BindView(R.id.supply_Details_supplyTime)
    TextView supplyDetailsSupplyTime;
    @BindView(R.id.ll_supply_Details_supplyTime)
    AutoLinearLayout ll_supply_Details_supplyTime;
    @BindView(R.id.supply_Details_outTime)
    TextView supplyDetailsOutTime;
    @BindView(R.id.ll_supply_Details_outTime)
    AutoLinearLayout llSupplyDetailsOutTime;
    @BindView(R.id.ss)
    NestedScrollView ss;
    @BindView(R.id.iv_outTime)
    ImageView ivOutTime;
    @BindView(R.id.moneySupplyDetails)
    TextView moneySupplyDetails;
    @BindView(R.id.companySupplyDetails)
    TextView companySupplyDetails;
    @BindView(R.id.rl_money_supply)
    AutoRelativeLayout rlMoneySupply;
    @BindView(R.id.call_purchase)
    TextView callPurchase;
    @BindView(R.id.ll_button_supply)
    AutoLinearLayout llButtonSupply;
    @BindView(R.id.no_supply)
    AutoLinearLayout noSupply;
    @BindView(R.id.call_kefu)
    TextView callKefu;
    @BindView(R.id.call_kefu_noSupply)
    TextView callKefuNoSupply;
    @BindView(R.id.call_purchaseNosupply)
    TextView callPurchaseNosupply;
    @BindView(R.id.supply_sure)
    TextView supplySure;
    @BindView(iv_back)
    ImageView ivBack;
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
    @BindView(R.id.batchCount)
    TextView batchCount;
    private ListSupplyOrderBySelfBean.ValueBean.RowsBean rowsBean;
    private String telephone;
    private String ids;
    private String eMUserName;
    private String eMPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_order_details);
        ButterKnife.bind(this);
        llTitle.setBackgroundResource(R.color.white);
        ivBack.setImageResource(R.mipmap.ic_mine_backgreen);
        tvTitle.setText("供货单详情");
        tvTitle.setTextColor(getResources().getColor(R.color.tab_tv_selected));
        black();//状态栏字体
        initdata();
    }

    private void initdata() {
        Intent intent = getIntent();
        rowsBean = (ListSupplyOrderBySelfBean.ValueBean.RowsBean) intent.getSerializableExtra("RowsBeanSupply");
        if (TextUtils.isEmpty(rowsBean.batchCount)) {
            batchCount.setText("--");
        }else {
            batchCount.setText(rowsBean.batchCount);
        }
        ids = rowsBean.supplyId;
        List<String> keyword = rowsBean.keyword;
        if (keyword != null && keyword.size() != 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.getContextObject(), 4);
            PurchaseOrderdetailsRecylerAdapter adapter = new PurchaseOrderdetailsRecylerAdapter(BaseApplication.getContextObject(), keyword);
            supplyOrderRecycler.setLayoutManager(gridLayoutManager);
            supplyOrderRecycler.setAdapter(adapter);
        }
        String supplyStatusName = rowsBean.supplyStatusName;//供货状态
        Boolean invalidStatus = rowsBean.invalidStatus;
        if (invalidStatus == false) {
            ivOutTime.setVisibility(View.GONE);
        } else {
            ivOutTime.setVisibility(View.GONE);
        }
        supplyDetailsStaus.setText(supplyStatusName);
        if (supplyStatusName.equals("已供货")) {
            llButtonSupply.setVisibility(View.VISIBLE);
            noSupply.setVisibility(View.GONE);
            ivSupplyDetailsStaus.setImageResource(R.mipmap.ic_mine_supply);
        } else if (supplyStatusName.equals("未供货")) {
            ivSupplyDetailsStaus.setImageResource(R.mipmap.ic_mine_notsupply);
        } else {
            llButtonSupply.setVisibility(View.GONE);
            noSupply.setVisibility(View.VISIBLE);
            ivSupplyDetailsStaus.setImageResource(R.mipmap.ic_mine_outtime);
        }

        List<String> originList = rowsBean.origin2;//产地
        if (originList == null) {
            supplyDetailsOrigin.setText("--");
        } else {
            String str = "";
            for (int i = 0; i < originList.size(); i++) {
                str += originList.get(i) + "   ";
            }
            supplyDetailsOrigin.setText(str);
        }

        List<String> listColor = rowsBean.colorGrade2;//颜色及
        if (listColor == null || listColor.size() < 1) {
            supplyDetailsColorGrade.setText("--");
        } else {
            String str = "";
            int count = 0;
            for (int i = 0; i < listColor.size(); i++) {
                if (count % 3 == 0 && count != 0) {
                    str += "\n" + listColor.get(i) + "   ";
                } else {
                    str += listColor.get(i) + "   ";
                }
                count++;
            }
            supplyDetailsColorGrade.setText(str);
        }

        ListSupplyOrderBySelfBean.ValueBean.RowsBean.BreakLoadAverageBean breakLoadAverage = rowsBean.breakLoadAverage;//强力
        if (breakLoadAverage == null) {
            supplyDetailsBreakLoad.setText("--");
        } else {
            supplyDetailsBreakLoad.setText(breakLoadAverage.min + "-" + breakLoadAverage.max);
            if (breakLoadAverage.min.equals(breakLoadAverage.max)) {
                supplyDetailsBreakLoad.setText(breakLoadAverage.min + "");
                if (breakLoadAverage.min.equals("24")) {
                    supplyDetailsBreakLoad.setText("24及以下");
                } else if (breakLoadAverage.min.equals("31")) {
                    supplyDetailsBreakLoad.setText("31及以上");
                }
            }
        }
        ListSupplyOrderBySelfBean.ValueBean.RowsBean.LengthAverageBean lengthAverage = rowsBean.lengthAverage;//长度
        if (lengthAverage == null) {
            supplyDetailsLength.setText("--");
        } else {
            supplyDetailsLength.setText(lengthAverage.min + "-" + lengthAverage.max);
            if (lengthAverage.min.equals(lengthAverage.max)) {
                supplyDetailsLength.setText(lengthAverage.min + "");
                if (lengthAverage.min.equals("25")) {
                    supplyDetailsLength.setText("25及以下");
                } else if (lengthAverage.min.equals("32")) {
                    supplyDetailsLength.setText("32及以上");
                }
            }
        }
        ListSupplyOrderBySelfBean.ValueBean.RowsBean.MicronAverageBean micronAverage = rowsBean.micronAverage;//马值
        if (micronAverage == null) {
            supplyDetailsMicron.setText("--");
        } else {
            supplyDetailsMicron.setText(micronAverage.min + "-" + micronAverage.max);
            if (micronAverage.min.equals(micronAverage.max)) {
                supplyDetailsMicron.setText(micronAverage.min + "");
                if (micronAverage.min.equals("3.4")) {
                    supplyDetailsMicron.setText("3.4及以下");
                } else if (micronAverage.min.equals("5.0")) {
                    supplyDetailsMicron.setText("5.0及以上");
                }
            }
        }
  /*      List<String> type2 = rowsBean.type2;//棉花年度
        if (type2 == null) {
            supplyDetailsType.setText("--");
        } else {
            String str = "";
            for (int i = 0; i < type2.size(); i++) {
                str += type2.get(i) + "   ";
            }
            supplyDetailsType.setText(str);
        }*/
        String settlementMethod = rowsBean.settlementMethod;
        if (TextUtils.isEmpty(settlementMethod)) {
            mSettlementMethod.setText("--");
        } else {
            mSettlementMethod.setText(settlementMethod);
        }
        ListSupplyOrderBySelfBean.ValueBean.RowsBean.TrashBean trashBean = rowsBean.trash;
        ListSupplyOrderBySelfBean.ValueBean.RowsBean.MoistureBean moistureBean = rowsBean.moisture;
        if (trashBean != null) {
            this.trash.setText(trashBean.min + "-" + trashBean.max);
            if (trashBean.min.equals(trashBean.max)) {
                this.trash.setText(trashBean.min);
                if (trashBean.min.equals("0")) {
                    this.trash.setText("0及以下");
                } else if (trashBean.min.equals("5")) {
                    this.trash.setText("5及以上");
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
        if (rowsBean.createYear != null && rowsBean.createYear.size() != 0) {
            String s = "";
            for (int i = 0; i < rowsBean.createYear.size(); i++) {
                if (i != rowsBean.createYear.size() - 1) {
                    s = s + rowsBean.createYear.get(i) + "/";
                } else {
                    s = s + rowsBean.createYear.get(i);
                }
            }
            supplyDetailsType.setText(s);
        } else {
            supplyDetailsType.setText("--");
        }

        String contacts = rowsBean.contacts;//联系人
        supplyDetailsContacts.setText(contacts);
        //电话
        telephone = rowsBean.telephone;
        supplyDetailsMobile.setText(telephone);
        String address = rowsBean.address;//地址
        String province = rowsBean.province;//省
        String city = rowsBean.city;
        String area = rowsBean.area;
   /*     if (address.equals("") && province == null) {
            supplyDetailsAddress.setText("--");
        } else if (province == null) {
            supplyDetailsAddress.setText(address);
        } else {
            supplyDetailsAddress.setText(province + city + area + address);
        }*/
        if (TextUtils.isEmpty(rowsBean.address2)) {
            supplyDetailsAddress.setText("--");
        } else {
            supplyDetailsAddress.setText(rowsBean.address2);
        }
        String deadline = rowsBean.deadline;//截至日期
        if (deadline == null) {
            supplyDetailsDeadtime.setText("--");
        } else {
            supplyDetailsDeadtime.setText(deadline);
        }

        boolean baleCotton = rowsBean.baleCotton;
        if (baleCotton) {
            tvColor.setText("品级");
        }
        String receiveDate = rowsBean.receiveDate;//送达日期
        if (receiveDate == null) {
            supplyDetailsRecevitime.setText("--");
        } else {
            supplyDetailsRecevitime.setText(receiveDate);
        }
        String remark = rowsBean.remark;//需求说明
        if (remark == null) {
            supplyDetailsRemark.setText("--");
        } else {
            supplyDetailsRemark.setText(remark);
        }
        String createTime = rowsBean.createTime;//创建时间
        supplyDetailsCreateTime.setText(createTime);
        String supplyTime = rowsBean.supplyTime;//供货时间
        if (supplyTime == null) {
            ll_supply_Details_supplyTime.setVisibility(View.GONE);
        } else {
            ll_supply_Details_supplyTime.setVisibility(View.VISIBLE);
            supplyDetailsSupplyTime.setText(supplyTime);
        }
        String stateUpdateTime = rowsBean.stateUpdateTime;//过期时间
        if (stateUpdateTime == null) {
            llSupplyDetailsOutTime.setVisibility(View.GONE);
        } else {
            llSupplyDetailsOutTime.setVisibility(View.VISIBLE);
            supplyDetailsOutTime.setText(stateUpdateTime);
        }
        int minPrice = rowsBean.minPrice;
        int maxPrice = rowsBean.maxPrice;
        if (minPrice == 0 && maxPrice == 0) {
            companySupplyDetails.setVisibility(View.GONE);
            moneySupplyDetails.setText("价格面议");
        } else {
            if (minPrice == maxPrice) {
                companySupplyDetails.setVisibility(View.VISIBLE);
                moneySupplyDetails.setText(minPrice + "");
            } else {
                companySupplyDetails.setVisibility(View.VISIBLE);
                moneySupplyDetails.setText(minPrice + "-" + maxPrice);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
    }

    private void black() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @OnClick({R.id.call_kefu, R.id.call_kefu_noSupply, R.id.call_purchaseNosupply, R.id.supply_sure, R.id.call_purchase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_purchase:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + telephone));//电话号码
                if (ActivityCompat.checkSelfPermission(SupplyOrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                break;
            case R.id.call_kefu:
                CallKefu.callOnLine(SupplyOrderDetailsActivity.this, eMUserName, eMPassword);
                break;
            case R.id.call_kefu_noSupply:
                CallKefu.callOnLine(SupplyOrderDetailsActivity.this, eMUserName, eMUserName);
                break;
            case R.id.call_purchaseNosupply:
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + telephone));//电话号码
                if (ActivityCompat.checkSelfPermission(SupplyOrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent1);
                break;
            case R.id.supply_sure:
                AlertDialog dialog = new AlertDialog.Builder(SupplyOrderDetailsActivity.this)
                        .setMessage("确定供货吗")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("supplyId", ids);
                                HttpManager.getServerApi().sureSupply(map).enqueue(new CallBack<PurchaseOrder>() {
                                    @Override
                                    public void success(PurchaseOrder data) {
                                        if (data.success) {
                                            dialog.dismiss();
                                            ToastUtil.show(BaseApplication.getContextObject(), "供货成功");
                                            finish();
                                        } else {
                                            ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                                        }
                                    }

                                    @Override
                                    public void failure(ErrorType type, int httpCode) {
                                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                                    }
                                });
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }

    @OnClick(iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
