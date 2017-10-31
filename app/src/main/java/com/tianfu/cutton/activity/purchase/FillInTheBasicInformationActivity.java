package com.tianfu.cutton.activity.purchase;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.model.SerializableMap;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.wheel.AddressChooserUtils;
import com.tianfu.cutton.widget.wheel.JsonUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 填写基本信息
* */
public class FillInTheBasicInformationActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_contacts)
    EditText tvContacts;
    @BindView(R.id.tv_telephone)
    EditText tvTelephone;
    @BindView(R.id.et_minPrice)
    EditText etMinPrice;
    @BindView(R.id.et_maxPrice)
    EditText etMaxPrice;
    @BindView(R.id.tv_batchCount)
    EditText tvBatchCount;
    @BindView(R.id.tv_receiveDate)
    TextView tvReceiveDate;
    @BindView(R.id.ll_receiveDate)
    AutoLinearLayout llReceiveDate;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.ll_deadline)
    AutoLinearLayout llDeadline;
    @BindView(R.id.ll_province)
    AutoLinearLayout llProvince;
    @BindView(R.id.tcv_address)
    EditText etAddress;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    String[] singleList = {"公重", "毛重"};
    @BindView(R.id.tv_account)
    TextView tvAccount;
    private Map<String, String> map;
    //省份集合
    ArrayList<String> provinces = new ArrayList<String>();
    //城市集合
    Map<String, ArrayList<String>> citis = new HashMap<String, ArrayList<String>>();
    //区域集合
    Map<String, ArrayList<String>> districts = new HashMap<String, ArrayList<String>>();
    //JSON文件
    private static final String FILE_PATH = "addr.json";
    private List address111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_basic_information);
        ButterKnife.bind(this);
        load(FillInTheBasicInformationActivity.this);
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        map = serializableMap.getMap();
        String mobile = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
        String userName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "userName");
        String companyName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "companyName");
        System.out.println("userName:" + userName);
        tvTelephone.setText(mobile);
        if (userName != null) {
            tvContacts.setText(userName);
            if (!TextUtils.isEmpty(companyName)) {
                tvContacts.setText(userName + "(" + companyName + ")");
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_receiveDate, R.id.ll_deadline, R.id.ll_province, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_receiveDate:
                showdate(tvReceiveDate);
                break;
            case R.id.ll_deadline:
                showdate(tvDeadline);
                break;
            case R.id.ll_province:
                bindShopper(address111);
                break;
            case R.id.bt_save:
                btSave.setEnabled(false);
                String contacts = tvContacts.getText().toString().trim();
                System.out.println("contacts:" + contacts);
                String telephone = tvTelephone.getText().toString().trim();
                String minPrice = etMinPrice.getText().toString().trim();
                String maxPrice = etMaxPrice.getText().toString().trim();
                String batchCount = tvBatchCount.getText().toString().trim();
                String receiveDate = tvReceiveDate.getText().toString().trim();
                String deadline = tvDeadline.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();
                String tvAccountTrim = tvAccount.getText().toString().trim();
                map.put("settlementMethod",tvAccountTrim);
                map.put("contacts", contacts);
                map.put("telephone", telephone);
                map.put("receiveDate", receiveDate);
                map.put("minPrice", minPrice);
                map.put("maxPrice", maxPrice);
                map.put("deadline", deadline);
                map.put("batchCount", batchCount);
                map.put("address", address);
                map.put("remark", remark);
                map.put("deviceNo", Common.deviceNo);
                HttpManager.getServerApi().createPurchaseOrder(map).enqueue(new CallBack<PurchaseOrder>() {
                    @Override
                    public void success(PurchaseOrder data) {
                        if (data.success) {
                            Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            ToastUtil.show(BaseApplication.getContextObject(), "发布成功");
                            intent.putExtra("fragmentid", "2");
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                            btSave.setEnabled(true);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
                break;
        }
    }

    private void load(Context context) {
        if (address111 == null) {
            address111 = JsonUtil.loadFromJson(context, FILE_PATH, List.class);
        }
    }

    private void bindShopper(final List<Map<String, Object>> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> m = list.get(i);
                //省名称
                String province = (String) m.get("name");
                ArrayList<Map<String, Object>> cities = (ArrayList) m.get("sub");
                provinces.add(province);

                ArrayList<String> oneProvinceCities = new ArrayList<>();

                if (cities != null) {
                    for (int j = 0; j < cities.size(); j++) {
                        ArrayList<String> oneProvinceCounties = new ArrayList<>();
                        Map<String, Object> city = cities.get(j);
                        //市
                        List<Map<String, Object>> counties = (List) city.get("sub");
                        if (counties != null) {
                            for (int z = 0; z < counties.size(); z++) {

                                Map<String, Object> county = counties.get(z);
                                //区名称
                                String countyName = (String) county.get("name");
                                oneProvinceCounties.add(countyName);
                            }
                        }
                        //市名称
                        String cityName = (String) city.get("name");
                        districts.put(cityName, oneProvinceCounties);
                        //一个省所有市
                        oneProvinceCities.add(cityName);
                    }
                }
                citis.put(province, oneProvinceCities);
            }
        }
        AddressChooserUtils instance = AddressChooserUtils.getInstance(FillInTheBasicInformationActivity.this);
        instance.setData(provinces, citis, districts);
        instance.showAddressChooserDialog(FillInTheBasicInformationActivity.this);
        instance.setOnAddressDialogDismissListener(new AddressChooserUtils.OnAddressDialogDismissListener() {
            @Override
            public void onDissmiss(String province, String city, String district) {
                tvProvince.setText(province + "" + city + district);
                map.put("province", province);
                map.put("city", city);
                map.put("area", district);
            }
        });
    }

    private void showdate(final TextView tv) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(FillInTheBasicInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                tv.setText(DateFormat.format("yyy-MM-dd", c));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.tv_account)
    public void onViewClicked() {
        showSingleChoiceDialog();
    }

    private void showSingleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择结算类型");
        String[] split = tvAccount.getText().toString().trim().split(" ");
        int count = 0;
        if (split[0].equals("毛重")) {
            count=1;
        }
        builder.setSingleChoiceItems(singleList, count, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = singleList[which];
                tvAccount.setText(str);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
