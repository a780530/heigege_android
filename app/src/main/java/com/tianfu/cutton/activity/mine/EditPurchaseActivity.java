package com.tianfu.cutton.activity.mine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.model.PurchaseOrderBySelfBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditPurchaseActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rb_Purchasing)
    RadioButton rbPurchasing;
    @BindView(R.id.tv_Purchasing)
    TextView tvPurchasing;
    @BindView(R.id.rb_Complete)
    RadioButton rbComplete;
    @BindView(R.id.tv_Complete)
    TextView tvComplete;
    @BindView(R.id.rb_Close)
    RadioButton rbClose;
    @BindView(R.id.tv_Close)
    TextView tvClose;
    @BindView(R.id.rb_Invalid)
    RadioButton rbInvalid;
    @BindView(R.id.tv_Invalid)
    TextView tvInvalid;
    @BindView(R.id.rg_purchaseStatus)
    RadioGroup rgPurchaseStatus;
    @BindView(R.id.ll_receiveDate)
    AutoLinearLayout llReceiveDate;
    @BindView(R.id.ll_deadline)
    AutoLinearLayout llDeadline;
    @BindView(R.id.tv_receiveDate)
    TextView tvReceiveDate;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tcv_address)
    TextView tcvAddress;
    @BindView(R.id.tv_batchCount)
    EditText tvBatchCount;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.etPrice_min)
    EditText etPriceMin;
    @BindView(R.id.etPrice_max)
    EditText etPriceMax;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_telephone)
    EditText tvTelephone;
    private String purchaseStatus;
    private String contacts;
    private String telephone;
    private String province;
    private String city;
    private String area;
    private String address;
    private String id;
    private String purchaseStatus1;
    private String remarkEdit;
    private String batchCountEdit;
    private int minPriceEdit;
    private int maxPriceEdit;
    private String receiveDateEdit;
    private String deadlineEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_purchase);
        ButterKnife.bind(this);
        tvTitle.setText("编辑基本信息");
        rgPurchaseStatus.setOnCheckedChangeListener(this);
        purchaseStatus = "Purchasing";
        Intent intent = getIntent();
        PurchaseOrderBySelfBean.ValueBean.RowsBean rowsBean = (PurchaseOrderBySelfBean.ValueBean.RowsBean) intent.getSerializableExtra("RowsBean");
        contacts = rowsBean.contacts;
        telephone = rowsBean.telephone;
        province = rowsBean.province;
        city = rowsBean.city;
        area = rowsBean.area;
        address = rowsBean.address;
        id = rowsBean.id;
        purchaseStatus1 = rowsBean.purchaseStatus;
        //备注
        remarkEdit = rowsBean.remark;
        //批次数量
        batchCountEdit = rowsBean.batchCount;
        minPriceEdit = rowsBean.minPrice;
        maxPriceEdit = rowsBean.maxPrice;
        //送达日期
        receiveDateEdit = rowsBean.receiveDate;
        //截至日期
        deadlineEdit = rowsBean.deadline;
        initData();
    }

    private void initData() {
        if (minPriceEdit != 0 && maxPriceEdit != 0) {
            etPriceMin.setText(minPriceEdit + "");
            etPriceMax.setText(maxPriceEdit + "");
        }
        if (remarkEdit != null && !"".equals(remarkEdit)) {
            etRemark.setText(remarkEdit);
        }
        if (receiveDateEdit != null && !"".equals(receiveDateEdit)) {
            tvReceiveDate.setText(receiveDateEdit);
        }
        if (deadlineEdit != null && !"".equals(deadlineEdit)) {
            tvDeadline.setText(deadlineEdit);
        }
        if (batchCountEdit != null && !"".equals(batchCountEdit)) {
            tvBatchCount.setText(batchCountEdit);
        }
        tvContacts.setText(contacts);
        tvTelephone.setText(telephone);
        if (address == null || "".equals(address)) {
            tcvAddress.setText("--");
        } else {
            tcvAddress.setText(address);
        }

        if (province == null) {
            tvProvince.setText("--");
        } else {
            tvProvince.setText(province + city + area);
        }
        if (purchaseStatus1.equals("Purchasing")) {
            rbPurchasing.setChecked(true);
        } else if (purchaseStatus1.equals("Close")) {
            rbClose.setChecked(true);
        } else if (purchaseStatus1.equals("Complete")) {
            rbComplete.setChecked(true);
        } else if (purchaseStatus1.equals("Invalid")) {
            rbInvalid.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rb_Purchasing:
                tvPurchasing.setTextColor(getResources().getColor(R.color.red_translucent));
                tvClose.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvComplete.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvInvalid.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                purchaseStatus = "Purchasing";
                break;
            case R.id.rb_Complete:
                tvPurchasing.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvClose.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvComplete.setTextColor(getResources().getColor(R.color.red_translucent));
                tvInvalid.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                purchaseStatus = "Complete";
                break;
            case R.id.rb_Close:
                tvPurchasing.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvClose.setTextColor(getResources().getColor(R.color.red_translucent));
                tvComplete.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvInvalid.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                purchaseStatus = "Close";
                break;
            case R.id.rb_Invalid:
                tvPurchasing.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvClose.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvComplete.setTextColor(getResources().getColor(R.color.tab_tv_normal));
                tvInvalid.setTextColor(getResources().getColor(R.color.red_translucent));
                purchaseStatus = "Invalid";
                break;
        }
    }

    @OnClick({R.id.ll_receiveDate, R.id.ll_deadline, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_receiveDate:
                showdate(tvReceiveDate);
                break;
            case R.id.ll_deadline:
                showdate(tvDeadline);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void showdate(final TextView tv) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(EditPurchaseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                tv.setText(DateFormat.format("yyy-MM-dd", c));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.bt_save)
    public void onViewClicked() {
        String minPrice = etPriceMin.getText().toString();
        String maxPrice = etPriceMax.getText().toString();
        String tv_batchCount = tvBatchCount.getText().toString();
        String tv_receiveDate = tvReceiveDate.getText().toString();
        String tv_deadline = tvDeadline.getText().toString();
        String et_remark = etRemark.getText().toString();
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);
        System.out.println("id:" + id);
        hashmap.put("receiveDate", tv_receiveDate);
        hashmap.put("minPrice", minPrice);
        hashmap.put("maxPrice", maxPrice);
        hashmap.put("deadline", tv_deadline);
        hashmap.put("batchCount", tv_batchCount);
        hashmap.put("remark", et_remark);
        hashmap.put("purchaseStatus", purchaseStatus);
        HttpManager.getServerApi().updatePurchase(hashmap).enqueue(new CallBack<PurchaseOrder>() {
            @Override
            public void success(PurchaseOrder data) {
                if (data.success) {
                    ToastUtil.show(BaseApplication.getContextObject(), "修改成功");
                    Intent intent = new Intent();
                    intent.putExtra("2", "success");
                    EditPurchaseActivity.this.setResult(0, intent);
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

}
