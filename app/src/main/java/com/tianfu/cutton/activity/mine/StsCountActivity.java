package com.tianfu.cutton.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CalculateOnceBean;
import com.tianfu.cutton.model.SerializableMap;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StsCountActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    AutoRelativeLayout llTitle;
    @BindView(R.id.bt_countSts)
    Button btCountSts;
    @BindView(R.id.et_batch)
    EditText etBatch;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_quotedPrice)
    EditText etQuotedPrice;
    String[] singleList = {"公重", "毛重"};
    @BindView(R.id.tv_account)
    TextView tvAccount;
    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sts_count);
        ButterKnife.bind(this);
        tvTitle.setText("升贴水计算");
        etBatch.addTextChangedListener(this);
        etPrice.addTextChangedListener(this);
        etQuotedPrice.addTextChangedListener(this);
    }

    @OnClick({R.id.iv_back, R.id.bt_countSts,R.id.tv_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.bt_countSts:
                String[] split = tvAccount.getText().toString().trim().split(" ");
                String trimBatch = etBatch.getText().toString().trim();
                String trimPrice= etPrice.getText().toString().trim();
                String trimQuotedPrice = etQuotedPrice.getText().toString().trim();
                if (trimBatch.length()<11) {
                    ToastUtil.show(BaseApplication.getContextObject(),"请输入11位正确的批次号");
                    return;
                }
                hashMap = new HashMap<>();
                hashMap.put("settlementChoice","1");
                if (split[0].equals("毛重")){
                    hashMap.put("settlementChoice","2");
                }
                hashMap.put("code",trimBatch);
                hashMap.put("spotPrice",trimQuotedPrice);
                hashMap.put("basePrice",trimPrice);
                hashMap.put("deviceNo", Common.deviceNo);
                HttpManager.getServerApi().calculateOnce(hashMap).enqueue(new CallBack<CalculateOnceBean>() {
                    @Override
                    public void success(CalculateOnceBean data) {
                        if (data.success){
                            Intent intent = new Intent(StsCountActivity.this, StsResultActivity.class);
                            final SerializableMap myMap = new SerializableMap();
                            myMap.setMap(hashMap);//将map数据添加到封装的myMap中
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mapSts", myMap);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            ToastUtil.show(BaseApplication.getContextObject(),data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {

                    }
                });
                break;
            case R.id.tv_account:
                showSingleChoiceDialog();
                break;
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String trim = etBatch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            btCountSts.setBackgroundResource(R.mipmap.ic_mine_stsbackblack);
            btCountSts.setEnabled(false);
        } else {
            btCountSts.setBackgroundResource(R.mipmap.ic_mine_stsback);
            btCountSts.setEnabled(true);
        }
        String trim1 = etPrice.getText().toString().trim();
        if (trim1.startsWith("0")){
            etPrice.getText().clear();
        }
        String trim2 = etQuotedPrice.getText().toString().trim();
        if (trim2.startsWith("0")) {
            etQuotedPrice.getText().clear();
        }
    }

}
