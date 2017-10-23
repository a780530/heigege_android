package com.tianfu.cutton.activity.purchase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.adapter.PurchaseOrderdetailsRecylerAdapter;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.ListPurchaseOrder;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurchaseDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_supplier)
    LinearLayout llSupplier;
    @BindView(R.id.purchaseKeyword)
    RecyclerView purchaseKeyword;
    @BindView(R.id.moneyDtails)
    TextView moneyDtails;
    @BindView(R.id.companyDetails)
    TextView companyDetails;
    @BindView(R.id.rl_money)
    AutoRelativeLayout rlMoney;
    @BindView(R.id.origin)
    TextView origin;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.horse)
    TextView horse;
    @BindView(R.id.breakLoadAverage)
    TextView breakLoadAverage;
    @BindView(R.id.length)
    TextView length;
    @BindView(R.id.contacts)
    TextView contacts;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.address2)
    TextView address2;
    @BindView(R.id.deadTime)
    TextView deadTime;
    @BindView(R.id.receiveDate)
    TextView receiveDate;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.tv_purchase_count)
    TextView tvPurchaseCount;

    private ListPurchaseOrder.ValueBean.RowsBean rowsBean;
    private int id;
    private Boolean isLogin;
    private String mobile1;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);
        ButterKnife.bind(this);
        tvTitle.setText("采购单详情");
        Intent intent = getIntent();
        rowsBean = (ListPurchaseOrder.ValueBean.RowsBean) intent.getSerializableExtra("RowsBeanPurchase");
        initData(rowsBean);
//        rlMoney
    }

    private void initData(ListPurchaseOrder.ValueBean.RowsBean rowsBean) {
        List<String> origin2 = rowsBean.origin2;
        List<String> colorGrade2 = rowsBean.colorGrade2;
        List<String> type2 = rowsBean.type2;
        List<String> keyword = rowsBean.keyword;

        id = rowsBean.id;
        ListPurchaseOrder.ValueBean.RowsBean.BreakLoadAverageBean breakLoadAverage1 = rowsBean.breakLoadAverage;
        ListPurchaseOrder.ValueBean.RowsBean.LengthAverageBean lengthAverage = rowsBean.lengthAverage;
        ListPurchaseOrder.ValueBean.RowsBean.MicronAverageBean micronAverage = rowsBean.micronAverage;
        String contacts1 = rowsBean.contacts;
        String telephone = rowsBean.telephone;
        String deadline1 = rowsBean.deadline;
        String receiveDate1 = rowsBean.receiveDate;
        String remark1 = rowsBean.remark;
        String province = rowsBean.province;
        String address1 = rowsBean.address;
        String address = rowsBean.address2;
        String batchCount = rowsBean.batchCount;
        int minPrice = rowsBean.minPrice;
        int maxPrice = rowsBean.maxPrice;
        if (batchCount != null && !"".equals(batchCount)) {
            tvPurchaseCount.setText(batchCount);
        }
        if (minPrice == 0 && maxPrice == 0) {
            moneyDtails.setText("价格面议");
            companyDetails.setVisibility(View.GONE);
        } else if (minPrice == maxPrice) {
            moneyDtails.setText(minPrice + "");
            companyDetails.setVisibility(View.VISIBLE);
        } else {
            moneyDtails.setText(minPrice + "-" + maxPrice);
            companyDetails.setVisibility(View.VISIBLE);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.getContextObject(), 4);
        purchaseKeyword.setLayoutManager(gridLayoutManager);
        PurchaseOrderdetailsRecylerAdapter adapter = new PurchaseOrderdetailsRecylerAdapter(BaseApplication.getContextObject(), keyword);
        purchaseKeyword.setAdapter(adapter);
        if (colorGrade2 == null) {
            color.setText("---");
        } else {
            String str = "";
            int count = 0;
            for (int i = 0; i < colorGrade2.size(); i++) {
                if (count % 3 == 0 && count != 0) {
                    str += "\n" + colorGrade2.get(i) + "   ";
                } else {
                    str += colorGrade2.get(i) + "   ";
                }
                count++;
            }
            color.setText(str);
        }
        if (origin2 == null) {
            origin.setText("---");
        } else {
            String str = "";
            int count = 0;
            for (int i = 0; i < origin2.size(); i++) {
                if (count % 3 == 0 && count != 0) {
                    str += "\n" + origin2.get(i) + "   ";
                } else {
                    str += origin2.get(i) + "   ";
                }
                count++;
            }
            origin.setText(str);
        }
        if (type2 == null) {
            type.setText("---");
        } else {
            String str = "";
            int count = 0;
            for (int i = 0; i < type2.size(); i++) {
                if (count % 3 == 0 && count != 0) {
                    str += "\n" + type2.get(i) + "   ";
                } else {
                    str += type2.get(i) + "   ";
                }
                count++;
            }
            type.setText(str);
        }
        mobile.setText(telephone);
        contacts.setText(contacts1);
        if ((address1 == null) || province == null) {
            address2.setText("--");
        } else {
            address2.setText(address);
        }
        if (remark1 != null && !"".equals(remark1)) {
            remark.setText(remark1);
        } else {
            remark.setText("--");
        }
        if (deadline1 != null) {
            deadTime.setText(deadline1);
        } else {
            deadTime.setText("--");
        }
        if (receiveDate1 != null) {
            receiveDate.setText(receiveDate1);
        } else {
            receiveDate.setText("--");
        }
        if (lengthAverage != null && !"".equals(lengthAverage)) {
            length.setText(lengthAverage.min + "-" + lengthAverage.max);
            if (lengthAverage.min.equals(lengthAverage.max)) {
                length.setText(lengthAverage.min);
                if (lengthAverage.min.equals("25")){
                    length.setText("25及以下");
                }else if (lengthAverage.max.equals("32")){
                    length.setText("32及以上");
                }
            }

        } else {
            length.setText("25-32");
        }
        if (micronAverage != null && !"".equals(micronAverage)) {
            horse.setText(micronAverage.min + "-" + micronAverage.max);
            if (micronAverage.min.equals(micronAverage.max)) {
                horse.setText(micronAverage.min);
                if (micronAverage.min.equals("3.4")){
                    horse.setText("3.4及以下");
                }else if (micronAverage.max.equals("5.0")){
                    horse.setText("5.0及以上");
                }
            }

        } else {
            horse.setText("3.4-5.0");
        }
        if (breakLoadAverage1 != null && !"".equals(breakLoadAverage1)) {
            breakLoadAverage.setText(breakLoadAverage1.min + "-" + breakLoadAverage1.max);
            if (breakLoadAverage1.min.equals(breakLoadAverage1.max)) {
                breakLoadAverage.setText(breakLoadAverage1.min);
                if (breakLoadAverage1.min.equals("24")){
                    breakLoadAverage.setText("24及以下");
                }else if (breakLoadAverage1.max.equals("31")){
                    breakLoadAverage.setText("31及以上");
                }
            }

        } else {
            breakLoadAverage.setText("24-31");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        mobile1 = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
        userName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "userName");
    }

    @OnClick({R.id.iv_back, R.id.ll_supplier})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_supplier:
                if (!isLogin) {
                    startActivity(new Intent(BaseApplication.getContextObject(), LoginActivity.class));
                    return;
                }
                //弹框
                showPop();
                break;
        }
    }

    private void showPop() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_supplier, null);
//        final PopWindow popWindow = new PopWindow(this, conentView);
        final PopupWindow popWindow = new PopupWindow();
        popWindow.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        popWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setFocusable(true);//响应弹框以外的按钮的点击事件
        popWindow.setOutsideTouchable(true);
        //防止虚拟软键盘被遮住
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 刷新状态
        popWindow.update();
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        final EditText et_contacts = (EditText) conentView.findViewById(R.id.et_contacts);
        final EditText etMobile = (EditText) conentView.findViewById(R.id.et_mobile);
        final EditText etRemark = (EditText) conentView.findViewById(R.id.et_remark);
        etMobile.setText(mobile1);
        if (userName != null) {
            et_contacts.setText(userName);
            if(!TextUtils.isEmpty(SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(),"companyName"))){
                et_contacts.setText(userName+"("+SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(),"companyName")+")");
            }
        }
        Button viewById = (Button) conentView.findViewById(R.id.sure_supply);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etMobile.getText().toString())) {
                    ToastUtil.show(BaseApplication.getContextObject(), "手机号码不能为空");
                    return;
                } else if (!CharacterFormatUtil.isPhoneNumberValid(etMobile.getText().toString())) {
                    ToastUtil.show(BaseApplication.getContextObject(), "手机号码格式不正确");
                    return;
                } else if (TextUtils.isEmpty(et_contacts.getText().toString())) {
                    ToastUtil.show(BaseApplication.getContextObject(), "联系人不能为空");
                    return;
                }
                String trimContacts = et_contacts.getText().toString().trim();
                String trimMobile = etMobile.getText().toString().trim();
                String trimRemark = etRemark.getText().toString().trim();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("contacts", trimContacts);
                hashMap.put("mobile", trimMobile);
                hashMap.put("remark", trimRemark);
                hashMap.put("purchaseId", id + "");
                hashMap.put("deviceNo", Common.deviceNo);
                HttpManager.getServerApi().createSupplyOrder(hashMap).enqueue(new CallBack<PurchaseOrder>() {
                    @Override
                    public void success(PurchaseOrder data) {
                        if (data.success) {
                            popWindow.dismiss();
                            ToastUtil.show(BaseApplication.getContextObject(), "供货成功");
                            onBackPressed();
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
        });
        backgroundAlpha(0.5f);//llSupplier
        popWindow.showAtLocation(llSupplier, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}