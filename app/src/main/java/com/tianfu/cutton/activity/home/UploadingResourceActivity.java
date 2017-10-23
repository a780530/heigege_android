package com.tianfu.cutton.activity.home;

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

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.EntrepotBean;
import com.tianfu.cutton.model.ProvinceBean;
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

import static com.tianfu.cutton.R.id.et_Upprice;
import static com.tianfu.cutton.R.id.tv_Uptime;


public class UploadingResourceActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    AutoRelativeLayout llTitle;
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
    @BindView(R.id.et_batch)
    EditText etBatch;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(et_Upprice)
    EditText etUpprice;
    @BindView(tv_Uptime)
    TextView tvUptime;
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
    @BindView(R.id.iv_show1)
    ImageView ivShow1;
    @BindView(R.id.tv_show1)
    TextView tvShow1;
    @BindView(R.id.show1)
    AutoLinearLayout show1;
    @BindView(R.id.iv_show2)
    ImageView ivShow2;
    @BindView(R.id.tv_show2)
    TextView tvShow2;
    @BindView(R.id.show2)
    AutoLinearLayout show2;
    @BindView(R.id.rl_location)
    AutoRelativeLayout rlLocation;
    @BindView(R.id.rl_name)
    AutoRelativeLayout rlName;
    @BindView(R.id.rl_time)
    AutoRelativeLayout rlTime;
    @BindView(R.id.bt_Up)
    Button btUp;
    @BindView(R.id.rl_upprice)
    AutoRelativeLayout rlUpprice;
    @BindView(R.id.unShow)
    AutoRelativeLayout unShow;

    private int pickNum = 1;
    private int accountNum = 1;
    private String showNum = "ON";
    private boolean locationSelect = false;
    private boolean nameSelect = false;
    private boolean timeSelect = false;
    private Map map;
    private String[] tvlocation;
    private String[] tvNames;
    private String codes;
    private Map mapUp;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_resource);
        ButterKnife.bind(this);
        map = new HashMap();
        etBatch.addTextChangedListener(this);
        etUpprice.addTextChangedListener(this);
        initProvinceData();
        tvUptime.setText("3天");
        timeSelect=true;
        Intent intent = getIntent();
        tvTitle.setText("上传资源");
        codes = intent.getStringExtra("codes");
        code = intent.getStringExtra("code");
        if (!TextUtils.isEmpty(code)) {
            etBatch.setText(code);
        }
        if (!TextUtils.isEmpty(codes)) {
            etBatch.setText(codes);
            unShow.setVisibility(View.GONE);
        }else{
            unShow.setVisibility(View.VISIBLE);
        }
    }

    private void initProvinceData() {
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

    @OnClick({R.id.rl_location, R.id.rl_name, R.id.rl_time, R.id.pick1, R.id.pick2, R.id.show1, R.id.show2, R.id.iv_back, R.id.account1, R.id.account2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pick1:
                pickNum = 1;
                tvPick1.setTextColor(getResources().getColor(R.color.red_translucent));
                tvPick2.setTextColor(getResources().getColor(R.color.tabColor));
                ivPick1.setImageResource(R.drawable.ic_mine_sec);
                ivPick2.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case R.id.pick2:
                pickNum = 2;
                tvPick2.setTextColor(getResources().getColor(R.color.red_translucent));
                tvPick1.setTextColor(getResources().getColor(R.color.tabColor));
                ivPick2.setImageResource(R.drawable.ic_mine_sec);
                ivPick1.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case R.id.show1:
                showNum = "ON";
                tvShow1.setTextColor(getResources().getColor(R.color.red_translucent));
                tvShow2.setTextColor(getResources().getColor(R.color.tabColor));
                ivShow1.setImageResource(R.drawable.ic_mine_sec);
                ivShow2.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case R.id.show2:
                showNum = "OFF";
                tvShow2.setTextColor(getResources().getColor(R.color.red_translucent));
                tvShow1.setTextColor(getResources().getColor(R.color.tabColor));
                ivShow2.setImageResource(R.drawable.ic_mine_sec);
                ivShow1.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.account1:
                accountNum = 1;
                tvAccount1.setTextColor(getResources().getColor(R.color.red_translucent));
                tvAccount2.setTextColor(getResources().getColor(R.color.tabColor));
                ivAccount1.setImageResource(R.drawable.ic_mine_sec);
                ivAccount2.setImageResource(R.drawable.ic_mine_nosec);
                break;
            case R.id.account2:
                accountNum = 2;
                tvAccount2.setTextColor(getResources().getColor(R.color.red_translucent));
                tvAccount1.setTextColor(getResources().getColor(R.color.tabColor));
                ivAccount2.setImageResource(R.drawable.ic_mine_sec);
                ivAccount1.setImageResource(R.drawable.ic_mine_nosec);
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
                //选择有效时间
                String sMarrige[] = {"3天", "2天", "1天"};
                selector(sMarrige, tvUptime, 3);
                break;
        }
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
                String trimBatch = etBatch.getText().toString().trim();
                String trimPrice = etUpprice.getText().toString().trim();
                if (select == 1) {
                    locationSelect = true;
                    getStoragesByProvince();
                } else if (select == 2) {
                    nameSelect = true;
                } else if (select == 3) {
                    timeSelect = true;
                }
                if (!TextUtils.isEmpty(trimBatch) && !TextUtils.isEmpty(trimPrice) && locationSelect && nameSelect && timeSelect) {
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

    @OnClick({R.id.bt_Up})
    public void onViewClicked() {
        String trimBatch = etBatch.getText().toString().trim();
        String trimLocation = tvLocation.getText().toString().trim();
        String trimName = tvName.getText().toString().trim();
        String trimUpprice = etUpprice.getText().toString().trim();
        if (etUpprice.length()!=5){
            ToastUtil.show(BaseApplication.getContextObject(),"请输入5位数非0价格");
            return;
        }
        String trimUptime = tvUptime.getText().toString().trim();
        if (trimUptime.contains("天")) {
            trimUptime = trimUptime.replace("天", "");
        }
        mapUp = new HashMap();
        mapUp.put("code", trimBatch);
        mapUp.put("deviceNo", Common.deviceNo);
        mapUp.put("province", trimLocation);
        mapUp.put("storageNameTemp", trimName);
        mapUp.put("price", trimUpprice);
        mapUp.put("settlementMethod", accountNum + "");
        mapUp.put("takeType", pickNum + "");
        mapUp.put("state", showNum);
        mapUp.put("validDate", trimUptime);
        if (!TextUtils.isEmpty(codes)) {
            mapUp.remove("code");
            mapUp.put("codes", codes);
            upAll(mapUp);
            return;
        }
        HttpManager.getServerApi().addProductOne(mapUp).enqueue(new CallBack<CodeValidate>() {
            @Override
            public void success(CodeValidate data) {
                if (data.success) {
                    Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("fragmentid", "1");
                    startActivity(intent);
                    ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                } else {
                    ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "网络错误");
            }
        });


    }

    private void upAll(Map mapUp) {
        showProgressBar("",false);
        HttpManager.getServerApi().addProductBatch(mapUp).enqueue(new CallBack<CodeValidate>() {
            @Override
            public void success(CodeValidate data) {
                if (data.success) {
                    dismissProgressBar();
                    Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("fragmentid", "1");
                    startActivity(intent);
                    ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                } else {
                    dismissProgressBar();
                    ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "网络错误");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String trimBatch = etBatch.getText().toString().trim();
        String trimPrice = etUpprice.getText().toString().trim();
        if (trimPrice.startsWith("0")) {
            etUpprice.getText().clear();
        }
        if (!TextUtils.isEmpty(trimPrice) && !TextUtils.isEmpty(trimBatch)&& locationSelect && nameSelect && timeSelect) {
            btUp.setEnabled(true);
            btUp.setBackgroundResource(R.mipmap.ic_back_long);
        } else {
            btUp.setEnabled(false);
            btUp.setBackgroundColor(getResources().getColor(R.color.tab_div));
        }
    }


}
