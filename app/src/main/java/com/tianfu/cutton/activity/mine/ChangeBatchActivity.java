package com.tianfu.cutton.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.EntrepotBean;
import com.tianfu.cutton.model.ProvinceBean;
import com.tianfu.cutton.model.StoreKunMessage;
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
import cn.qqtheme.framework.picker.OptionPicker;

public class ChangeBatchActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.rl_location)
    AutoRelativeLayout rlLocation;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_name)
    AutoRelativeLayout rlName;
    @BindView(R.id.et_Upprice)
    EditText etUpprice;
    @BindView(R.id.tv_Uptime)
    TextView tvUptime;
    @BindView(R.id.rl_time)
    AutoRelativeLayout rlTime;
    @BindView(R.id.iv_account1)
    ImageView ivAccount1;
    @BindView(R.id.tv_account1)
    TextView tvAccount1;
    @BindView(R.id.account1)
    AutoLinearLayout account1;
    @BindView(R.id.iv_account2)
    ImageView ivAccount2;
    @BindView(R.id.tv_account2)
    TextView tvAccount2;
    @BindView(R.id.account2)
    AutoLinearLayout account2;
    @BindView(R.id.iv_pick1)
    ImageView ivPick1;
    @BindView(R.id.tv_pick1)
    TextView tvPick1;
    @BindView(R.id.pick1)
    AutoLinearLayout pick1;
    @BindView(R.id.iv_pick2)
    ImageView ivPick2;
    @BindView(R.id.tv_pick2)
    TextView tvPick2;
    @BindView(R.id.pick2)
    AutoLinearLayout pick2;
    @BindView(R.id.iv_up)
    ImageView ivUp;
    @BindView(R.id.tv_up)
    TextView tvUp;
    @BindView(R.id.up)
    AutoLinearLayout up;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.down)
    AutoLinearLayout down;
    @BindView(R.id.iv_out)
    ImageView ivOut;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.out)
    AutoLinearLayout out;
    @BindView(R.id.iv_off)
    ImageView ivOff;
    @BindView(R.id.tv_off)
    TextView tvOff;
    @BindView(R.id.off)
    AutoLinearLayout off;
    @BindView(R.id.bt_Up)
    Button btUp;
    private boolean locationSelect = false;
    private boolean nameSelect = false;
    private boolean timeSelect = false;
    private String[] tvNames;
    private String[] tvlocation;
    private int pickNum = 1;
    private int accountNum = 1;
    private String state;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_batch);
        ButterKnife.bind(this);
        etUpprice.addTextChangedListener(this);
        initProvinceData();
        productId = getIntent().getStringExtra("productId");
        System.out.println("productId:-------------" + productId);
        initData(productId);
        String state5 = getIntent().getStringExtra("state");
//        ON("上架"), OFF("下架"), SOLD_OUT("已售"), DEPLETE("已消耗");
        if (state5.equals("ON")) {
            state = "ON";
            showSeletc(state);
        } else if (state5.equals("OFF")) {
            state = "OFF";
            showSeletc(state);
        } else if (state5.equals("SOLD_OUT")) {
            state = "SOLD_OUT";
            showSeletc(state);
        } else {
            state = "DEPLETE";
            showSeletc(state);
        }
    }

    private void initData(String productId) {
        Map map = new HashMap();
        map.put("productIds", productId);
        HttpManager.getServerApi().getStoreKunMessage(map).enqueue(new CallBack<StoreKunMessage>() {
            @Override
            public void success(StoreKunMessage data) {
                if (data.success) {
                    if (data.value != null) {
                        StoreKunMessage.ValueBean valueBean = data.value.get(0);
                        tvTitle.setText(data.value.get(0).code + "--修改批次");
                        if (!TextUtils.isEmpty(data.value.get(0).storage)) {
                            tvName.setText(data.value.get(0).storage);
                            nameSelect = true;
                        }
                        if (!TextUtils.isEmpty(data.value.get(0).province)) {
                            tvLocation.setText(data.value.get(0).province);
                            locationSelect = true;
                        }
                        if (!TextUtils.isEmpty(data.value.get(0).price)) {
                            etUpprice.setText(data.value.get(0).price);
                        }
                        if (!TextUtils.isEmpty(data.value.get(0).validDate)) {
                            tvUptime.setText(data.value.get(0).validDate + "天");
                            timeSelect = true;
                        }
                        if (!TextUtils.isEmpty(valueBean.takeType)) {
                            if ("1".equals(valueBean.takeType)) {
                                pickNum = 1;
                                showPickSelect(pickNum);
                            } else {
                                pickNum = 2;
                                showPickSelect(pickNum);
                            }
                        }
                        if (!TextUtils.isEmpty(valueBean.settlementMethod)) {
                            if ("1".equals(valueBean.settlementMethod)) {
                                accountNum = 1;
                                showAccountSelect(accountNum);
                            } else {
                                accountNum = 2;
                                showAccountSelect(accountNum);
                            }
                        }
                        String trimPrice = etUpprice.getText().toString().trim();
                        if (!TextUtils.isEmpty(trimPrice) && locationSelect && nameSelect && timeSelect) {
                            btUp.setEnabled(true);
                            btUp.setBackgroundResource(R.mipmap.ic_back_long);
                        } else {
                            btUp.setEnabled(false);
                            btUp.setBackgroundColor(getResources().getColor(R.color.tab_div));
                        }
                    }
                }
                String trim = tvLocation.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){

                }else{
                    getStoragesByProvince();
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void initProvinceData() {
        Map map = new HashMap();
        HttpManager.getServerApi().getProvince(map).enqueue(new CallBack<ProvinceBean>() {
            @Override
            public void success(ProvinceBean data) {
                if (data.success) {
                    List<String> value = data.value;
                    tvlocation = value.toArray(new String[value.size()]);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
            }
        });
    }

    private void getStoragesByProvince() {
        String trim = tvLocation.getText().toString().trim();
        Map<String, String> mapStorages = new HashMap<>();
        mapStorages.put("province", trim);
        HttpManager.getServerApi().getStoragesByProvince(mapStorages).enqueue(new CallBack<EntrepotBean>() {
            @Override
            public void success(EntrepotBean data) {
                if (data.success) {
                    List<String> value = data.value;
                    tvNames = value.toArray(new String[value.size()]);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void selector(String[] sMarrige, final TextView tvUptime, final int select) {
        final OptionPicker picker = new OptionPicker(this, sMarrige);
        picker.setOffset(2);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTopLineVisible(false);
        picker.setLineColor(getResources().getColor(R.color.red_translucent));
        picker.setTextColor(getResources().getColor(R.color.red_translucent));
        picker.setTopBackgroundColor(getResources().getColor(R.color.bt_back));//设置顶部标题栏背景颜色
        picker.setCancelTextColor(getResources().getColor(R.color.drop_color));
        picker.setSubmitTextColor(getResources().getColor(R.color.drop_color));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tvUptime.setText(option);
                String trimPrice = etUpprice.getText().toString().trim();
                if (select == 1) {
                    locationSelect = true;
                    getStoragesByProvince();
                } else if (select == 2) {
                    nameSelect = true;
                } else if (select == 3) {
                    timeSelect = true;
                }
                if (!TextUtils.isEmpty(trimPrice) && locationSelect && nameSelect && timeSelect) {
                    btUp.setEnabled(true);
                    btUp.setBackgroundResource(R.mipmap.ic_back_long);
                } else {
                    btUp.setEnabled(false);
                    btUp.setBackgroundColor(getResources().getColor(R.color.tab_div));
                }
            }
        });
        picker.show();
    }

    @OnClick({R.id.iv_back, R.id.rl_location, R.id.rl_name, R.id.rl_time, R.id.account1, R.id.account2, R.id.pick1, R.id.pick2, R.id.up, R.id.down, R.id.out, R.id.off, R.id.bt_Up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_location:
                selector(tvlocation, tvLocation, 1);
                break;
            case R.id.rl_name:
                String trim = tvLocation.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.show(BaseApplication.getContextObject(), "请选择仓库所在地");
                    return;
                }
                selector(tvNames, tvName, 2);
                break;
            case R.id.rl_time:
                String sMarrige[] = {"1天", "2天", "3天"};
                selector(sMarrige, tvUptime, 3);
                break;
            case R.id.account1:
                accountNum = 1;
                showAccountSelect(accountNum);
                break;
            case R.id.account2:
                accountNum = 2;
                showAccountSelect(accountNum);
                break;
            case R.id.pick1:
                pickNum = 1;
                showPickSelect(pickNum);
                break;
            case R.id.pick2:
                pickNum = 2;
                showPickSelect(pickNum);
                break;
            case R.id.up:
//                ON("上架"), OFF("下架"), SOLD_OUT("已售"), DEPLETE("已消耗");
                state = "ON";
                showSeletc(state);
                break;
            case R.id.down:
                state = "OFF";
                showSeletc(state);
                break;
            case R.id.out:
                state = "SOLD_OUT";
                showSeletc(state);
                break;
            case R.id.off:
                state = "DEPLETE";
                showSeletc(state);
                break;
            case R.id.bt_Up:
                String trimLocation = tvLocation.getText().toString().trim();
                String trimName = tvName.getText().toString().trim();
                String trimUpprice = etUpprice.getText().toString().trim();
                if (trimUpprice.length()!=5){
                    ToastUtil.show(BaseApplication.getContextObject(),"请输入5位数非0价格");
                    return;
                }
                String trimUptime = tvUptime.getText().toString().trim();
                if (trimUptime.contains("天")) {
                    trimUptime = trimUptime.replace("天", "");
                }
                Map<String, String> map = new HashMap<>();
                map.put("id", productId);
                map.put("storageNameTemp", trimName);
                map.put("province", trimLocation);
                map.put("price", trimUpprice);
                map.put("settlementMethod", accountNum + "");
                map.put("state", state);
                map.put("takeType", pickNum + "");
                map.put("validDate", trimUptime);
                HttpManager.getServerApi().changeBatch(map).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            Intent intent = new Intent();
                            intent.putExtra("1", "close");
                            ChangeBatchActivity.this.setResult(0,intent);
                            ToastUtil.show(BaseApplication.getContextObject(),data.msg);
                            onBackPressed();
                        }else{
                            ToastUtil.show(BaseApplication.getContextObject(),data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {

                    }
                });
                break;
        }
    }

    private void showPickSelect(int pickNum) {
        switch (pickNum) {
            case 1:
                tvPick1.setTextColor(getResources().getColor(R.color.red_translucent));
                tvPick2.setTextColor(getResources().getColor(R.color.tabColor));
                ivPick1.setImageResource(R.drawable.ic_mine_sec);
                ivPick2.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case 2:
                tvPick2.setTextColor(getResources().getColor(R.color.red_translucent));
                tvPick1.setTextColor(getResources().getColor(R.color.tabColor));
                ivPick2.setImageResource(R.drawable.ic_mine_sec);
                ivPick1.setImageResource(R.drawable.ic_mine_nosec);
                break;
        }
    }

    private void showAccountSelect(int accountNum) {
        switch (accountNum) {
            case 1:
                tvAccount1.setTextColor(getResources().getColor(R.color.red_translucent));
                tvAccount2.setTextColor(getResources().getColor(R.color.tabColor));
                ivAccount1.setImageResource(R.drawable.ic_mine_sec);
                ivAccount2.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case 2:
                tvAccount2.setTextColor(getResources().getColor(R.color.red_translucent));
                tvAccount1.setTextColor(getResources().getColor(R.color.tabColor));
                ivAccount2.setImageResource(R.drawable.ic_mine_sec);
                ivAccount1.setImageResource(R.drawable.ic_mine_nosec);
                break;
        }
    }

    private void showSeletc(String state) {
        switch (state) {
//            ON("上架"), OFF("下架"), SOLD_OUT("已售"), DEPLETE("已消耗");
            case "ON":
                ivUp.setImageResource(R.drawable.ic_mine_sec);
                ivDown.setImageResource(R.drawable.ic_mine_nosec);
                ivOut.setImageResource(R.drawable.ic_mine_nosec);
                ivOff.setImageResource(R.drawable.ic_mine_nosec);
                tvUp.setTextColor(getResources().getColor(R.color.red_translucent));
                tvDown.setTextColor(getResources().getColor(R.color.tabColor));
                tvOut.setTextColor(getResources().getColor(R.color.tabColor));
                tvOff.setTextColor(getResources().getColor(R.color.tabColor));
                break;
            case "OFF":
                ivUp.setImageResource(R.drawable.ic_mine_nosec);
                ivDown.setImageResource(R.drawable.ic_mine_sec);
                ivOut.setImageResource(R.drawable.ic_mine_nosec);
                ivOff.setImageResource(R.drawable.ic_mine_nosec);
                tvUp.setTextColor(getResources().getColor(R.color.tabColor));
                tvDown.setTextColor(getResources().getColor(R.color.red_translucent));
                tvOut.setTextColor(getResources().getColor(R.color.tabColor));
                tvOff.setTextColor(getResources().getColor(R.color.tabColor));
                break;
            case "SOLD_OUT":
                ivUp.setImageResource(R.drawable.ic_mine_nosec);
                ivDown.setImageResource(R.drawable.ic_mine_nosec);
                ivOut.setImageResource(R.drawable.ic_mine_sec);
                ivOff.setImageResource(R.drawable.ic_mine_nosec);
                tvUp.setTextColor(getResources().getColor(R.color.tabColor));
                tvDown.setTextColor(getResources().getColor(R.color.tabColor));
                tvOut.setTextColor(getResources().getColor(R.color.red_translucent));
                tvOff.setTextColor(getResources().getColor(R.color.tabColor));
                break;
            case "DEPLETE":
                ivUp.setImageResource(R.drawable.ic_mine_nosec);
                ivDown.setImageResource(R.drawable.ic_mine_nosec);
                ivOut.setImageResource(R.drawable.ic_mine_nosec);
                ivOff.setImageResource(R.drawable.ic_mine_sec);
                tvUp.setTextColor(getResources().getColor(R.color.tabColor));
                tvDown.setTextColor(getResources().getColor(R.color.tabColor));
                tvOut.setTextColor(getResources().getColor(R.color.tabColor));
                tvOff.setTextColor(getResources().getColor(R.color.red_translucent));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String trimPrice = etUpprice.getText().toString().trim();
        if (trimPrice.startsWith("0")) {
            etUpprice.getText().clear();
        }
        if (!TextUtils.isEmpty(trimPrice) && locationSelect && nameSelect && timeSelect) {
            btUp.setEnabled(true);
            btUp.setBackgroundResource(R.mipmap.ic_back_long);
        } else {
            btUp.setEnabled(false);
            btUp.setBackgroundColor(getResources().getColor(R.color.tab_div));
        }
    }
}
